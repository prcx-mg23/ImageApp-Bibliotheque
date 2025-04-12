package com.imglibrary.imageapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
public class ImageController {
    @FXML
    private ImageView imageView;

    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        //
         //
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    private void rotation_90() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            TransformationRotation rotation90 = new TransformationRotation(90);
            Image imageMirroir = rotation90.transform(currentImage);
            imageView.setImage(imageMirroir);
        }
    }

    @FXML
    private void rotation_180() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            TransformationRotation rotation180 = new TransformationRotation(180);
            Image imageMirroir = rotation180.transform(currentImage);
            imageView.setImage(imageMirroir);
        }
    }

    @FXML
    private void rotation_270() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            TransformationRotation rotation270 = new TransformationRotation(270);
            Image imageMirroir = rotation270.transform(currentImage);
            imageView.setImage(imageMirroir);
        }
    }

    @FXML
    private void HorizontalSymetry() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            TransformationSymetrie horizontalSymetry = new TransformationSymetrie(TransformationSymetrie.SymetryType.HORIZONTAL);
            Image imageMirroir = horizontalSymetry.transform(currentImage);
            imageView.setImage(imageMirroir);
        }
    }

    @FXML
    private void VerticalSymetry() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            TransformationSymetrie verticalSymetry = new TransformationSymetrie(TransformationSymetrie.SymetryType.VERTICAL);
            Image imageMirroir = verticalSymetry.transform(currentImage);
            imageView.setImage(imageMirroir);
        }
    }
}
