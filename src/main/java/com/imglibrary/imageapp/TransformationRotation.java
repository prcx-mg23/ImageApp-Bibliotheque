package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// Classe qui herite de la classe ImageTransformation

public class TransformationRotation extends ImageTransformationAbstract {

        private final int angle;


        public TransformationRotation(int angle) {
            this.angle=angle%360;
        }

        @Override
        public Image transform(Image image) {
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();

            WritableImage RotatedImage;
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter pixelWriter;

            switch (angle) {

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
