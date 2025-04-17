package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class FiltreSobel extends ImageTransformationAbstract{


        public Image transform(Image image) {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            PixelReader reader = image.getPixelReader();
            WritableImage outputImage = new WritableImage(width, height);
            PixelWriter writer = outputImage.getPixelWriter();

            int[][] gx={
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}
            };

            int[][] gy={
                    {-1, -2, -1},
                    { 0,  0,  0},
                    { 1,  2,  1}
            };

            for (int y=1; y<height-1; y++) {
                for (int x=1; x<width-1; x++) {
                    double sumX=0;
                    double sumY=0;

                    for (int j=-1; j<=1; j++) {
                        for (int i=-1; i<=1; i++) {
                            Color color=reader.getColor(x+i, y+j);
                            double gray=color.grayscale().getRed();
                            sumX +=gray*gx[j+1][i+1];
                            sumY +=gray*gy[j+1][i+1];
                        }
                    }

                    // Calcul du gradient
                    double gradient=Math.sqrt(sumX*sumX+sumY*sumY);

                    // Normalisation
                    gradient=Math.min(gradient,1.0); // Clamp entre 0 et 1

                    Color edgeColor=new Color(gradient, gradient, gradient, 1.0);
                    writer.setColor(x, y, edgeColor);
                }
            }

            return outputImage;
        }
    }


