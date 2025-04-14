package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class FiltreSobel extends ImageFiltreAbstract{

        private final int[][] horizontalKernel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        private final int[][] verticalKernel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        @Override
        public Image apply(Image image) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            WritableImage outputImage = createWritableImage(image);
            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = outputImage.getPixelWriter();

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    double gxRed = 0, gyRed = 0;
                    double gxGreen = 0, gyGreen = 0;
                    double gxBlue = 0, gyBlue = 0;

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            Color neighbor = getPixelColor(pixelReader, x + j, y + i);
                            gxRed += neighbor.getRed() * horizontalKernel[i + 1][j + 1];
                            gyRed += neighbor.getRed() * verticalKernel[i + 1][j + 1];
                            gxGreen += neighbor.getGreen() * horizontalKernel[i + 1][j + 1];
                            gyGreen += neighbor.getGreen() * verticalKernel[i + 1][j + 1];
                            gxBlue += neighbor.getBlue() * horizontalKernel[i + 1][j + 1];
                            gyBlue += neighbor.getBlue() * verticalKernel[i + 1][j + 1];
                        }
                    }

                    double magnitudeRed = Math.sqrt(gxRed * gxRed + gyRed * gyRed);
                    double magnitudeGreen = Math.sqrt(gxGreen * gxGreen + gyGreen * gyGreen);
                    double magnitudeBlue = Math.sqrt(gxBlue * gxBlue + gyBlue * gyBlue);

                    double magnitude = (magnitudeRed + magnitudeGreen + magnitudeBlue) / 3.0;
                    magnitude = Math.min(1.0, magnitude); // Clamp value

                    Color newColor = new Color(magnitude, magnitude, magnitude, 1.0);
                    setPixelColor(pixelWriter, x, y, newColor);
                }
            }
            return outputImage;
        }
    }

