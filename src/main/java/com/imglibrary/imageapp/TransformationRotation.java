package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class TransformationRotation extends ImageTransformationAbstract {

        private final int angle; // Angle de rotation en degrés (pour l'instant, gérons uniquement 90, 180, 270)

        public TransformationRotation(int angle) {
            this.angle = angle % 360; // Normaliser l'angle
        }

        @Override
        public Image transform(Image image) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage rotatedImage;
            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter;

            switch (angle) {
                case 90:
                    rotatedImage = new WritableImage(height, width);
                    pixelWriter = rotatedImage.getPixelWriter();
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            Color color = pixelReader.getColor(x, y);
                            pixelWriter.setColor(y, width - 1 - x, color);
                        }
                    }
                    break;
                case 180:
                    rotatedImage = new WritableImage(width, height);
                    pixelWriter = rotatedImage.getPixelWriter();
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            Color color = pixelReader.getColor(x, y);
                            pixelWriter.setColor(width - 1 - x, height - 1 - y, color);
                        }
                    }
                    break;
                case 270:
                    rotatedImage = new WritableImage(height, width);
                    pixelWriter = rotatedImage.getPixelWriter();
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            Color color = pixelReader.getColor(x, y);
                            pixelWriter.setColor(height - 1 - y, x, color);
                        }
                    }
                    break;
                case 0:
                default:
                    return image; // Pas de rotation ou angle non géré pour l'instant
            }

            return rotatedImage;

    }
}
