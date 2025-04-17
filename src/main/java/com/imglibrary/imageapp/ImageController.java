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


    //Désactive les boutons et charge les images depuis la source pour les afficher
    @FXML
    public void initialize() {
        prevButton.setDisable(true);
        nextButton.setDisable(true);

        loadImagesFromSourceDirectory("/com/imglibrary/imageapp/images");
    }

    //afficher la première image du repertoire
    // Gestion de l'erreur relative à l'appel de la fnction loadImagesFromSourceDirectory
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
                System.err.println("Repertoire introuvable: " + directoryPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur de chargement de l'image: " + e.getMessage());
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

    //afficher une image
    private void displayImage(File file) {
        Image image=new Image(file.toURI().toString());
        imageView.setImage(image);
        currentImageIndex=imageFiles.indexOf(file);
        updateButtonVisibility();
    }

    //Controller des bouton images précédentes et suivantes

        /*
        showPreviousImage() permet d'aller à l'mage précédente
        en décrémentant l'indice de l'image en cours
        -Le bouton s'active lorsque l'index de l'image dans la liste est supérieur à 0
        c-a-d lorsqu'on se trouve à partir de la 2 ème image du repertoire.
        */
    @FXML
    private void showPreviousImage() {
        if (currentImageIndex>0) {
            currentImageIndex--;
            displayImage(imageFiles.get(currentImageIndex));
        }
    }
        /*
            showNextImage() permet d'aller à l'image suivante en incrémentant l'indice
            de l'image en cours
            - Le bouton se désactive une fois qu'on atteint la dernière image
        */
    @FXML
    private void showNextImage() {
        if (currentImageIndex < imageFiles.size() - 1) {
            currentImageIndex++;
            displayImage(imageFiles.get(currentImageIndex));
        }
    }

    private void updateButtonVisibility() {
        prevButton.setDisable(imageFiles.isEmpty()||currentImageIndex==0);
        nextButton.setDisable(imageFiles.isEmpty()||currentImageIndex==imageFiles.size()-1);
    }

   /*
     Mise en place du fileChooser avec onChooseImage() qui creer un objet fileChooser
     pour permpettre à l'utilisateur de sélectioner une nouvelle image ne faisant pas partie
     du repertoire par défaut dans les formats repertoriés et de l'afficher
    */
    @FXML
    private void onChooseImage() {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choisir une image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile=fileChooser.showOpenDialog(null);
        if (selectedFile!=null) {
            Image image=new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }


    // CONTROLLEURS POUR LES TRANSFORMATIONS

    //  1- Rotations

     /*
          -Rotation_90() dégré  Applique la méthode transform(Image image) de la classe
           TransformationRotation d'angle 90 sur l'image
    */

    @FXML
    private void rotation_90() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationRotation rotation90=new TransformationRotation(90);
            Image RotatedImage = rotation90.transform(currentImage);
            imageView.setImage(RotatedImage);
        }
    }

        /*
            -Rotation_180() dégré  Applique la méthode transform(Image image) de la
            classe TransformationRotation d'angle 180 sur l'image
        */
    @FXML
    private void rotation_180() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationRotation rotation180=new TransformationRotation(180);
            Image RotatedImage = rotation180.transform(currentImage);
            imageView.setImage(RotatedImage);
        }
    }


    //  2- Symetries

    /*
         -HorizontalSymetry() Applique la méthode transform(Image image) de la classe
          TransformationSymetrie pour renverser l'image Horizontalement
   */
    @FXML
    private void HorizontalSymetry() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationSymetrie horizontalSymetry=new TransformationSymetrie(TransformationSymetrie.SymetryType.HORIZONTAL);
            Image imageMirroir=horizontalSymetry.transform(currentImage);
            imageView.setImage(imageMirroir);
        }
    }

    /*
         -VerticalSymetry() Applique la méthode transform(Image image) de la classe
          TransformationSymetrie pour renverser l'image verticalement
   */
    @FXML
    private void VerticalSymetry() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationSymetrie verticalSymetry=new TransformationSymetrie(TransformationSymetrie.SymetryType.VERTICAL);
            Image imageMirroir=verticalSymetry.transform(currentImage);
            imageView.setImage(imageMirroir);
        }
    }

    // 3-Filtres

        //-filtre SwapRgb
         /* applyFiltreSwapRgb() applique le filtre de mélange des composantes RGB
         en apliquant la methode apply(Image image) de la classe FiltreSwapRgb
     */
    @FXML
    private void applyFiltreSwapRgb() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreSwapRgb filtre=new FiltreSwapRgb();
            imageView.setImage(filtre.transform(currentImage));
        }
    }

        //-filtre Noir et Blanc
        /* applyFiltreNB() applique le filtre de mélange des composantes RGB
         en apliquant la methode apply(Image image) de la classe  FiltreNoirBlanc
         */
    @FXML
    private void applyFiltreNB() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreNoirBlanc filtre=new FiltreNoirBlanc();
            imageView.setImage(filtre.transform(currentImage));
        }
    }
         //-filtre Sépia
        /* applyFiltreSepia() applique le filtre de mélange des composantes RGB
         en apliquant la methode apply(Image image) de la classe FiltreSepia
         */
    @FXML
    private void applyFiltreSepia() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreSepia filtre=new FiltreSepia();
            imageView.setImage(filtre.transform(currentImage));
        }
    }
        //-filtre Sobel
        /* applyFiltreSobel() applique le filtre de mélange des composantes RGB
         en apliquant la methode apply(Image image) de la classe FiltreSepia
         */
    @FXML
    private void applyFiltreSobel() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreSobel filtre=new FiltreSobel();
            imageView.setImage(filtre.transform(currentImage));
        }
    }
}