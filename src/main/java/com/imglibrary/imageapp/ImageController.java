package com.imglibrary.imageapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
public class ImageController {
    @FXML
    private ImageView imageView;

    @FXML
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        //
        // Définir le répertoire initial du FileChooser sur le dossier "resources"
        // Définir le répertoire initial du FileChooser sur le dossier "resources"
        URL resourcesUrl = getClass().getResource("/com/imglibrary/imgapp/");
        if (resourcesUrl != null) {
            try {
                File resourcesDirectory = Paths.get(resourcesUrl.toURI()).toFile();
                fileChooser.setInitialDirectory(resourcesDirectory);
            } catch (Exception e) {
                e.printStackTrace();
                // En cas d'erreur, on laisse le FileChooser s'ouvrir à son emplacement par défaut
                System.err.println("Erreur lors de la récupération du répertoire resources : " + e.getMessage());
            }
        }

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
