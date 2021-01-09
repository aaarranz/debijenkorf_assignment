package com.debijenkorf.assignment.model;

public class PredefinedImageType {

    public enum ScaleType {
        CROP,
        FILL,
        SKEW
    }

    public enum ImageType {
        JPG,
        PNG
    }

    int height, width, quality, fillColor;
    String sourceName;

    ScaleType scaleType;
    ImageType imageType;

    public PredefinedImageType(int height, int width, int quality, int fillColor, ScaleType scaleType, ImageType imageType) {
        this.height = height;
        this.width = width;
        this.quality = quality;
        // TODO: Convert from String HEX definition to integer
        this.fillColor = fillColor;
        this.scaleType = scaleType;
        this.imageType = imageType;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }
}
