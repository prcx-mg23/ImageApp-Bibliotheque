package com.imglibrary.imageapp;

import java.util.ArrayList;
import java.util.List;

public class ImageMetaData {
    private String imagePath;
    private List<String> tags;
    private List<TransformationInfo> transformations;

    public ImageMetaData(String imagePath) {
        this.imagePath = imagePath;
        this.tags = new ArrayList<>();
        this.transformations = new ArrayList<>();
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public List<TransformationInfo> getTransformations() {
        return transformations;
    }

    public void addTransformation(TransformationInfo transformation) {
        transformations.add(transformation);
    }
}

