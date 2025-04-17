package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

//Classe abstraite qui implémente l'interface Image
    /*
    -Possède des méthodes communes pour les toutes les transformations createWritableImage(Image image)
    -Possède une methode abstraite transform() qui est propre à chaque transformations
    */
public abstract class ImageTransformationAbstract implements ImageTransformation{

    //methode qui recupère la hauteur et la largeur de l'image
    protected WritableImage createWritableImage(Image image){
        int width=(int)image.getWidth();
        int height=(int)image.getHeight();
        return new WritableImage(width, height);
    }

    //methode qui recupère la matrice de pixels
    protected Color getPixelColor(PixelReader pixelReader, int x, int y) {
        return pixelReader.getColor(x, y);
    }

    //methode pour changer les pixels
    protected void setPixelColor(PixelWriter pixelWriter, int x, int y, Color color) {
        pixelWriter.setColor(x, y, color);
    }

    @Override
    //methode pour appliquer les transformations propre à chaque type de transformation
    public abstract Image transform(Image image);
}
