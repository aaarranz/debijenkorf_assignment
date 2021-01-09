package com.debijenkorf.assignment.services;

import com.debijenkorf.assignment.config.AssignmentEnvConfig;
import com.debijenkorf.assignment.exceptions.*;
import com.debijenkorf.assignment.log.AssignmentLogger;
import com.debijenkorf.assignment.model.PredefinedImageType;
import com.debijenkorf.assignment.services.storage.AssignmentStorageService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Assignment Service class
 */

@Service
public class AssignmentMainService {

    private AssignmentLogger logger;
    private AssignmentStorageService storageService;
    private AssignmentEnvConfig envs;

    private Map<String, PredefinedImageType> predefinedImageMap;

    @Autowired
    public AssignmentMainService(AssignmentLogger logger, AssignmentStorageService storageService, AssignmentEnvConfig envs) {
        this.logger = logger;
        this.storageService = storageService;
        this.envs = envs;

        predefinedImageMap = loadPredefinedImageTypes();
    }

    /**
     * Load predefined image types from resources resources/prefefined_images_types/*.json
     * @return a map with all predefined image types allowed by the assignment
     */
    private Map<String, PredefinedImageType> loadPredefinedImageTypes() {

        //TODO: Load Predefined Image Types from resources

        // Temporal "mock"
        return Map.of(
                "thumbnail", new PredefinedImageType(100,200, 50, 400, PredefinedImageType.ScaleType.FILL, PredefinedImageType.ImageType.JPG),
                "smallscale", new PredefinedImageType(10,20, 50, 400, PredefinedImageType.ScaleType.CROP, PredefinedImageType.ImageType.PNG)
        );
    }

    /**
     * Get the predefined image type object from key name
     *
     * @param name
     * @return PredefinedImageType object or empty if original
     * @throws ImageTypeNotExistException
     */
    private PredefinedImageType getPredefinedImage(String name) throws ImageTypeNotExistException {

        if(predefinedImageMap.containsKey(name)) {
            return predefinedImageMap.get(name);
        } else if (!name.equals("original")) {
            String errorMessage = "Image type " + name + " does not exist";
            logger.log(AssignmentMainService.class, LogLevel.INFO, errorMessage);
            throw new ImageTypeNotExistException(errorMessage);
        } else {
            // If original the PredefinedImageType is not going to be needed
            return null;
        }
    }

    /**
     * Download original image from base
     *
     * @return
     */
    private byte[] getOriginalImage(String filename) throws ImageSourceNotExistException, SourceUrlDownException {

        String url = envs.getSource_root_url() + filename;

        try {

            // TODO: Don't now if HttpClient is the best way, but we want to get the response code in order to launch an exception or not
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == HttpStatus.SC_NOT_FOUND) {
                String errorMessage = "Image " + url + " does not exist";
                logger.log(AssignmentMainService.class, LogLevel.INFO, errorMessage);
                throw new ImageSourceNotExistException(errorMessage);
            }

            // TODO: Transform response image to byte[]
            return new byte[0];

        } catch (Exception e) {
            String errorMessage = "Error getting image " + url + " : " + e.getMessage();
            logger.log(AssignmentMainService.class, LogLevel.ERROR, errorMessage);
            throw new SourceUrlDownException(errorMessage);
        }
    }

    /**
     * Resize the image
     *
     * @param image
     * @param predefinedImageType
     * @return
     */
    private byte[] resizeOriginalImage(byte[] image, PredefinedImageType predefinedImageType) throws GenericException {

        try {
            // TODO: Do the resize stuff
            return new byte[0];

        } catch (Exception e) {
            String errorMessage = "Error resizing image: " + e.getMessage();
            logger.log(AssignmentMainService.class, LogLevel.ERROR, errorMessage);
            throw new SourceUrlDownException(errorMessage);
        }
    }

    /**
     * GetImage logic
     *
     * @return image
     */
    public byte[] getImage(String predefinedImageType, String filename)
            throws GenericException , ImageTypeNotExistException, SourceUrlDownException, ImageSourceNotExistException, UploadToStorageException {

        logger.log(AssignmentMainService.class, LogLevel.INFO, "Service getImage invoked");

        /*
           Check if predefined image type exist, will return an exception if predefinedImageType is not defined. And assign
           PredefinedImageType if needed in future resize step
         */
        PredefinedImageType predImageTypeResize = getPredefinedImage(predefinedImageType);

        // First check if optimized image is stored
        Optional<byte[]> optimizedImage = storageService.obtainStoragedImage(predefinedImageType, filename);

        // If optimized image does not exist
        if(optimizedImage.isEmpty()) {

            // Check for original image
            Optional<byte[]> originalImage = storageService.obtainStoragedImage("original", filename);

            // If not exits
            if(originalImage.isEmpty()) {

                // Download original image
                byte[] originalImageDownaload = getOriginalImage(filename);

                // Upload to storage server
                storageService.storeImage("original", filename, originalImageDownaload);

                originalImage = Optional.of(originalImageDownaload);
            }

            // Resize the image if not the original and upload
            if(!predefinedImageType.equalsIgnoreCase("original")) {

                byte[] resizedImage = resizeOriginalImage(originalImage.get(), predImageTypeResize);

                // Upload to storage server
                storageService.storeImage(predefinedImageType, filename, resizedImage);

                return resizedImage;

            } else {
                return originalImage.get();
            }

        } else {
            return optimizedImage.get();
        }
    }

    /**
     *  Flush images logic
     *
     * @param predefinedImageType
     * @param filename
     * @throws GenericException
     */
    public void deleteOneOrAllImages(String predefinedImageType, String filename) throws GenericException {

        // If original delete all optimized images for this filename
        if(predefinedImageType.equalsIgnoreCase("original")) {

            Set<String> predImageKeys = predefinedImageMap.keySet();
            predImageKeys.add("original");

            storageService.deleteAllStoredImages(predImageKeys, filename);

        } else {
            storageService.deleteStoredImage(predefinedImageType, filename);
        }
    }
}
