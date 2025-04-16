package com.imglibrary.imageapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.stage.FileChooser;

public class ImageController {

    @FXML
    private ImageView imageView;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    private List<File> imageFiles = new ArrayList<>();
    private int currentImageIndex = 0;
    private File currentDirectory;

    @FXML
    public void initialize() {
        // Disable buttons initially
        prevButton.setDisable(true);
        nextButton.setDisable(true);

        loadImagesFromSourceDirectory("/com/imglibrary/imageapp/images"); // Load on startup
    }

    private void loadImagesFromSourceDirectory(String directoryPath) {
        try {
            URL resourcesUrl = getClass().getResource(directoryPath);
            if (resourcesUrl != null) {
                currentDirectory = Paths.get(resourcesUrl.toURI()).toFile();
                loadImageFiles(currentDirectory);
                if (!imageFiles.isEmpty()) {
                    displayImage(imageFiles.get(0)); // Show the first image
                }
            } else {
                System.err.println("Directory not found: " + directoryPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    private void loadImageFiles(File directory) {
        imageFiles.clear();
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") ||
                name.toLowerCase().endsWith(".jpg") ||
                name.toLowerCase().endsWith(".jpeg"));
        if (files != null) {
            for (File file : files) {
                imageFiles.add(file);
            }
        }
        currentImageIndex = 0; // Reset index
        updateButtonVisibility();
    }

    private void displayImage(File file) {
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        currentImageIndex = imageFiles.indexOf(file);
        updateButtonVisibility();
    }

    @FXML
    private void showPreviousImage() {
        if (currentImageIndex > 0) {
            currentImageIndex--;
            displayImage(imageFiles.get(currentImageIndex));
        }
    }

    @FXML
    private void showNextImage() {
        if (currentImageIndex < imageFiles.size() - 1) {
            currentImageIndex++;
            displayImage(imageFiles.get(currentImageIndex));
        }
    }

    private void updateButtonVisibility() {
        prevButton.setDisable(imageFiles.isEmpty() || currentImageIndex == 0);
        nextButton.setDisable(imageFiles.isEmpty() || currentImageIndex == imageFiles.size() - 1);
    }


    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

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

    @FXML
    private void applyFiltreSwapRgb() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            FiltreSwapRgb filtre = new FiltreSwapRgb();
            imageView.setImage(filtre.apply(currentImage));
        }
    }

    @FXML
    private void applyFiltreNB() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            FiltreNoirBlanc filtre = new FiltreNoirBlanc();
            imageView.setImage(filtre.apply(currentImage));
        }
    }

    @FXML
    private void applyFiltreSepia() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            FiltreSepia filtre = new FiltreSepia();
            imageView.setImage(filtre.apply(currentImage));
        }
    }

    @FXML
    private void applyFiltreSobel() {
        Image currentImage = imageView.getImage();
        if (currentImage != null) {
            FiltreSobel filtre = new FiltreSobel();
            imageView.setImage(filtre.apply(currentImage));
        }
    }
}