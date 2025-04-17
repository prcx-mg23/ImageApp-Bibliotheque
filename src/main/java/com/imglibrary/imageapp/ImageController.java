package com.imglibrary.imageapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.nio.file.Paths;

import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import java.util.Map;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.*;
import java.io.*;
import java.util.Optional;
import java.awt.image.BufferedImage;

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
    private TextField tagField;

    @FXML
    private Button addTagButton;

    private Image imageOriginale;
    private ImageMetaData imageMetadata;
    private List<ImageMetaData> metadataList = new ArrayList<>();



    @FXML
    public void initialize() {
        if (addTagButton != null) {
            addTagButton.setOnAction(e ->ajouterTag());
        }
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        loadImagesFromSourceDirectory("/com/imglibrary/imageapp/images");
    }

    //afficher la première image du repertoire

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
                System.err.println("repertoire introuvable: " + directoryPath);
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

        imageOriginale = image;
        Optional<ImageMetaData> existing = metadataList.stream()
                .filter(meta -> meta.getImagePath().equals(file.getAbsolutePath()))
                .findFirst();
        if (existing.isPresent()) {
            imageMetadata = existing.get();
        } else {
            imageMetadata = new ImageMetaData(file.getAbsolutePath());
            metadataList.add(imageMetadata);
        }

    }

    //Controller des bouton images précédentes et suivante
    @FXML
    private void showPreviousImage() {
        if (currentImageIndex>0) {
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
        prevButton.setDisable(imageFiles.isEmpty()||currentImageIndex==0);
        nextButton.setDisable(imageFiles.isEmpty()||currentImageIndex==imageFiles.size()-1);
    }


    @FXML
    private void onChooseImage() {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choisir une image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile=fileChooser.showOpenDialog(null);
        if (selectedFile!=null) {
            imageOriginale = new Image(selectedFile.toURI().toString());
            imageView.setImage(imageOriginale);
            imageMetadata = new ImageMetaData(selectedFile.getAbsolutePath());
            metadataList.add(imageMetadata);
        }
    }

    // CONTROLLEURS POUR LES TRANSFORMATIONS

    //  1- Rotations
    @FXML
    private void rotation_90() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationRotation rotation90=new TransformationRotation(90);
            Image RotatedImage = rotation90.transform(currentImage);
            imageView.setImage(RotatedImage);
            enregistrerTransformation("Rotation", Map.of("angle", "90"));
        }
    }

    @FXML
    private void rotation_180() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationRotation rotation180=new TransformationRotation(180);
            Image RotatedImage = rotation180.transform(currentImage);
            imageView.setImage(RotatedImage);
            enregistrerTransformation("Rotation", Map.of("angle", "180"));
        }
    }


    //  2- Symetries
    @FXML
    private void HorizontalSymetry() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationSymetrie horizontalSymetry=new TransformationSymetrie(TransformationSymetrie.SymetryType.HORIZONTAL);
            Image imageMirroir=horizontalSymetry.transform(currentImage);
            imageView.setImage(imageMirroir);
            enregistrerTransformation("Symetrie", Map.of("type", "HORIZONTAL"));

        }
    }

    @FXML
    private void VerticalSymetry() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            TransformationSymetrie verticalSymetry=new TransformationSymetrie(TransformationSymetrie.SymetryType.VERTICAL);
            Image imageMirroir=verticalSymetry.transform(currentImage);
            imageView.setImage(imageMirroir);
            enregistrerTransformation("Symetrie", Map.of("type", "VERTICAL"));
        }
    }

    // 3-Filtres
    @FXML
    private void applyFiltreSwapRgb() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreSwapRgb filtre=new FiltreSwapRgb();
            imageView.setImage(filtre.transform(currentImage));
            enregistrerTransformation("FiltreSwapRgb", Map.of());
        }
    }

    @FXML
    private void applyFiltreNB() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreNoirBlanc filtre=new FiltreNoirBlanc();
            imageView.setImage(filtre.transform(currentImage));
            enregistrerTransformation("FiltreNoirBlanc", Map.of());
        }
    }

    @FXML
    private void applyFiltreSepia() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreSepia filtre=new FiltreSepia();
            imageView.setImage(filtre.transform(currentImage));
            enregistrerTransformation("FiltreSepia", Map.of());
        }
    }

    @FXML
    private void applyFiltreSobel() {
        Image currentImage=imageView.getImage();
        if (currentImage!=null) {
            FiltreSobel filtre=new FiltreSobel();
            imageView.setImage(filtre.transform(currentImage));
            enregistrerTransformation("FiltreSobel", Map.of());
        }
    }

    //CONTROLLEUR POUR LES TAGS
    private void ajouterTag(){
        String tag = tagField.getText();
        if (tag != null && !tag.isBlank() && imageMetadata != null) {
            imageMetadata.addTag(tag);
            tagField.clear();
        }
    }

    private void enregistrerTransformation(String type, Map<String, String> params) {
        if (imageMetadata != null) {
            imageMetadata.addTransformation(new TransformationInfo(type, params));
        }
    }

    public void sauvegarderMetadata() {
        try {
            GestionMetaData.save(metadataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargerMetadata() {
        try {
            metadataList = GestionMetaData.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //CONTROLLEUR POUR LA SECURITE
    @FXML
    private void securiserImage() {
        Image currentImage = imageView.getImage();
        if (currentImage!= null) {
            byte[] imageData = getImageBytes(currentImage);
            String password = "marlowe";
            try {
                byte[] encryptedData = GestionSecurite.securiserImage(imageData, password);

                Image encryptedImage = new Image(new ByteArrayInputStream(encryptedData));
                imageView.setImage(encryptedImage);

                File file = new File("image_chiffree.jpg");
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(encryptedData);
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la sécurisation de l'image : " + e.getMessage());
            }
        }
    }

    @FXML
    private void dechiffrerImage() {
        Image currentImage = imageView.getImage();
        if (currentImage!= null) {
            byte[] imageData = getImageBytes(currentImage);

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Mot de passe");
            dialog.setHeaderText("Entrez le mot de passe pour déchiffrer l'image");
            Optional<String> password = dialog.showAndWait();
            if (password.isPresent() && password.get().equals("marlowe")) {
                try {
                    byte[] decryptedData = GestionSecurite.dechiffrerImage(imageData, password.get());
                    Image image = new Image(new ByteArrayInputStream(decryptedData));
                    imageView.setImage(image);
                } catch (Exception e) {
                    System.err.println("Erreur lors du déchiffrement de l'image : " + e.getMessage());
                }
            } else {
                System.out.println("Mot de passe incorrect");
            }
        }
    }

    private byte[] getImageBytes(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.setArgb(x, y, reader.getArgb(x, y));
            }
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BufferedImage bufferedImage = toBufferedImage(writableImage);
            javax.imageio.ImageIO.write(bufferedImage, "jpg", bos);
        } catch (IOException e) {
            System.err.println("Erreur lors de la conversion de l'image : " + e.getMessage());
        }
        return bos.toByteArray();
    }

    private BufferedImage toBufferedImage(WritableImage image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        PixelReader reader = image.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = reader.getArgb(x, y);
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;
                bufferedImage.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return bufferedImage;
    }
}