package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class FiltreSwapRgb extends ImageFiltreAbstract{

        @Override
        public Image apply(Image image) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            WritableImage outputImage = createWritableImage(image);
            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = outputImage.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = getPixelColor(pixelReader, x, y);
                    double red = color.getRed();
                    double green = color.getGreen();
                    double blue = color.getBlue();
                    Color newColor = new Color(blue, red, green, color.getOpacity());
                    setPixelColor(pixelWriter, x, y, newColor);
                }
            }
            return outputImage;
        }
}
