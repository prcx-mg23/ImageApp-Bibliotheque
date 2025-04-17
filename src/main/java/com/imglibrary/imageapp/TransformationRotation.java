package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// Classe qui herite de la classe ImageTransformation

public class TransformationRotation extends ImageTransformationAbstract {
    // Angle de rotation
        private final int angle;

        //constructeur
        public TransformationRotation(int angle) {
            this.angle=angle%360;
        }

        @Override
        public Image transform(Image image) {
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();


            //Déclaration d'un objet  WritableImage, PixelReader, PixelWriter
            WritableImage RotatedImage;
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter pixelWriter;

            switch (angle) {
                //rotation à 90° la hauteur devient la largeur et la largeur la hauteur
                case 90:
                    RotatedImage=new WritableImage(height, width);
                    pixelWriter=RotatedImage.getPixelWriter();
                    for (int y=0; y<height; y++) {
                        for (int x=0; x<width; x++) {
                            Color color=pixelReader.getColor(x, y);
                            pixelWriter.setColor(y, width - 1 - x, color);
                        }
                    }
                    break;
                case 180:
                    //rotation à 180°, la hauteur et la largeur et la hauteur ne changent pas mais les pixels sont renversé (x,y) devient (width - 1 - x, height - 1 - y)
                    RotatedImage=new WritableImage(width, height);
                    pixelWriter=RotatedImage.getPixelWriter();
                    for (int y=0; y<height; y++) {
                        for (int x=0; x<width; x++) {
                            Color color=pixelReader.getColor(x, y);
                            pixelWriter.setColor(width-1-x, height-1-y, color);
                        }
                    }
                    break;

                default:
                    return image;
            }

            return RotatedImage;
    }
}
