package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreSepia extends ImageFiltreAbstract{

        @Override
        public Image apply(Image image) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            WritableImage outputImage = createWritableImage(image);
            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = outputImage.getPixelWriter();

            for (int y=0; y<height; y++) {
                for (int x=0; x<width; x++) {
                    Color color = getPixelColor(pixelReader, x, y);
                    double red = color.getRed();
                    double green = color.getGreen();
                    double blue = color.getBlue();

                    double newRed = Math.min(1.0, 0.393 * red + 0.769 * green + 0.189 * blue);
                    double newGreen = Math.min(1.0, 0.349 * red + 0.686 * green + 0.168 * blue);
                    double newBlue = Math.min(1.0, 0.272 * red + 0.534 * green + 0.131 * blue);

                    Color newColor = new Color(newRed, newGreen, newBlue, color.getOpacity());
                    setPixelColor(pixelWriter, x, y, newColor);
                }
            }
            return outputImage;
        }
    }

