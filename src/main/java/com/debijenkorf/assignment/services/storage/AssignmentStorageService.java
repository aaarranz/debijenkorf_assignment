package com.debijenkorf.assignment.services.storage;

import com.debijenkorf.assignment.exceptions.GenericException;
import com.debijenkorf.assignment.exceptions.UploadToStorageException;

import java.util.Optional;
import java.util.Set;

public interface AssignmentStorageService {

    /**
     * Check for optimized or original image, and return if exist
     *
     * @return optimized image in byte array or null if not exist
     * @throws GenericException is some error
     */
    Optional<byte[]> obtainStoragedImage(String predefinedImageType, String filename) throws GenericException;

    /**
     * Store the optimized or original image
     *
     * @param predefinedImageType
     * @param filename
     * @throws GenericException is some error
     */
    void storeImage(String predefinedImageType, String filename, byte[] image) throws UploadToStorageException;

    /**
     *  Delete optimized image
     * @param predefinedImageType
     * @param filename
     * @throws GenericException
     */
    void deleteStoredImage(String predefinedImageType, String filename) throws GenericException;

    /**
     * Delete all optimized images (case "original" in flush request)
     * @param predImageTypes
     * @param filename
     * @throws GenericException
     */
    void deleteAllStoredImages(Set<String> predImageTypes, String filename) throws GenericException;
}
