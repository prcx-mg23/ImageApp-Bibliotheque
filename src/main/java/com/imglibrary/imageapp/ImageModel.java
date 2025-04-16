package com.imglibrary.imageapp;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class ImageModel {
        private String filePath;
        private List<Tag> tags = new ArrayList<>();
        private List<ImageTransformation> transformations = new ArrayList<>();


        public ImageModel(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public List<Tag> getTags() {
            return tags;
        }

        public void setTags(List<Tag> tags) {
            this.tags = tags;
        }

        public void addTag(Tag tag) {
            this.tags.add(tag);
        }

        public void removeTag(Tag tag) {
            this.tags.remove(tag);
        }

        public List<ImageTransformation> getTransformations() {
            return transformations;
        }

        public void setTransformations(List<ImageTransformation> transformations) {
            this.transformations = transformations;
        }

        public void addTransformation(ImageTransformation transformation) {
            this.transformations.add(transformation);
        }

    public Image applyTransformations(Image originalImage) {
        Image transformedImage = originalImage;
        for (ImageTransformation transformation : transformations) {
            transformedImage = transformation.transform(transformedImage);
        }
        return transformedImage;
    }
    }
