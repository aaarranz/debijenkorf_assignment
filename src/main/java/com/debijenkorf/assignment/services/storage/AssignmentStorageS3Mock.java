package com.debijenkorf.assignment.services.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.debijenkorf.assignment.config.AssignmentEnvConfig;
import com.debijenkorf.assignment.exceptions.GenericException;
import com.debijenkorf.assignment.exceptions.UploadToStorageException;
import com.debijenkorf.assignment.log.AssignmentLogger;
import io.findify.s3mock.S3Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AssignmentStorageS3Mock implements AssignmentStorageService {

    private AssignmentLogger logger;
    private AssignmentEnvConfig envs;

    private S3Mock s3MockServer;
    private AmazonS3Client s3client;

    @Autowired
    public AssignmentStorageS3Mock(AssignmentLogger logger, AssignmentEnvConfig envs) {

        this.logger = logger;
        this.envs = envs;

        // TODO: Improve client initialization
        s3client = new AmazonS3Client();
    }

    private void startMockServer() {
        s3MockServer = S3Mock.create(8001, "/tmp/s3");
        s3MockServer.start();

        // TODO: Mock call to server
    }

    /**
     *  AWS S3 directory strategy
     *
     * @param predImageType
     * @param filename
     * @return
     */
    private String getS3path(String predImageType, String filename) {

        if(predImageType.length() <= 4) {
            return "/" + predImageType +
                    // unique-original-image-filename
                    "/" + filename.replace("/","_");
        }

        if(predImageType.length() <= 8) {
            return "/" + predImageType + "/" +
                    // first-4-chars
                    filename.substring(0, 4).replace("/","_") +
                    // unique-original-image-filename
                    "/" + filename.replace("/","_");
        }

        return "/" + predImageType + "/" +
                // first-4-chars
                filename.substring(0, 4).replace("/","_") +
                // second-4-chars
                "/" + filename.substring(filename.length()-4, filename.length()).replace("/","_") +
                // unique-original-image-filename
                "/" + filename.replace("/","_");
    }

    @Override
    public Optional<byte[]> obtainStoragedImage(String predefinedImageType, String filename) throws GenericException {

        String filePath = getS3path(predefinedImageType, filename);

        logger.log(AssignmentStorageS3Mock.class, LogLevel.INFO, "obtain S3 invoked " + envs.getAws_s3_endpoint()
                + " for image: " +  filePath);

        try {
            // TODO: Do s3 client stuff to get image
            //s3client.getObject(... filePath)
        } catch (Exception e) {
            String errorMessage = "Exception with S3 Storeage: " + e.getMessage();
            logger.log(AssignmentStorageS3Mock.class, LogLevel.ERROR, errorMessage);
            throw new GenericException(errorMessage);
        }

        // Until S3 is fully mock a simple "mock" implementation
        byte[] mockImage = "MockImage".getBytes();

        switch (filename) {
            case "validimage":
                return Optional.of(mockImage);
            case "errorimage":
                logger.log(AssignmentStorageS3Mock.class, LogLevel.ERROR, "Exception with S3 Storeage: error image in action");
                throw new GenericException("Exception with S3 Storeage: error image in action");
            default:
                return Optional.empty();
        }
    }

    @Override
    public void storeImage(String predefinedImageType, String filename, byte[] image) throws UploadToStorageException {

        String filePath = getS3path(predefinedImageType, filename);

        logger.log(AssignmentStorageS3Mock.class, LogLevel.INFO, "store S3 invoked " + envs.getAws_s3_endpoint()
                + " for image: " +  filePath);

        int numRetries = 1;
        String errorMessage = "";

        while(numRetries>=0) {

            try {
                // TODO: Do s3 client stuff to store the image
                // s3client.createBucket(...)
                // ....
            } catch (Exception e) {
                // Log the exception on WARNING level.
                //Retry writing once after 200ms, if still not
                //successful, log another exception on
                //ERROR level

                numRetries--;

                errorMessage = "Exception with S3 Storage: " + e.getMessage();

                if (numRetries<0)  {
                    logger.log(AssignmentStorageS3Mock.class, LogLevel.ERROR, errorMessage);
                } else {
                    logger.log(AssignmentStorageS3Mock.class, LogLevel.WARN, errorMessage);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

        if(numRetries<0) {
            throw new UploadToStorageException(errorMessage);
        }
    }

    @Override
    public void deleteStoredImage(String predefinedImageType, String filename) throws GenericException {
        try {

            String filePath = getS3path(predefinedImageType, filename);
            // TODO: Do S3 stuff for delete de filepath

        } catch (Exception e) {
            String errorMessage = "Exception S3 Storage: " + e.getMessage();
            logger.log(AssignmentStorageS3Mock.class, LogLevel.ERROR, errorMessage);
            throw new GenericException(errorMessage);
        }
    }

    @Override
    public void deleteAllStoredImages(Set<String> predImageTypes, String filename) throws GenericException {

        try {

            predImageTypes.forEach(predImage -> {
                String filePath = getS3path(predImage, filename);
                // TODO: Do S3 stuff for delete de filepath
            });

        } catch (Exception e) {
            String errorMessage = "Exception S3 Storage: " + e.getMessage();
            logger.log(AssignmentStorageS3Mock.class, LogLevel.ERROR, errorMessage);
            throw new GenericException(errorMessage);
        }
    }
}
