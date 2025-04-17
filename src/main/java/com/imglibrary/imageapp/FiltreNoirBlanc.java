package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreNoirBlanc extends ImageTransformationAbstract{

    //flitre Noir et blanc
        @Override
        public Image transform(Image image){
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            WritableImage outputImage=createWritableImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter pixelWriter=outputImage.getPixelWriter();

            for (int y=0; y<height; y++){
                for (int x=0; x<width; x++){
                    Color color=getPixelColor(pixelReader, x, y);
                    double red=color.getRed();
                    double green=color.getGreen();
                    double blue=color.getBlue();
                    double gray=(red+green+blue)/3.0;
                    Color newColor=new Color(gray, gray, gray, color.getOpacity());
                    setPixelColor(pixelWriter, x, y, newColor);
                }
            }
            return outputImage;
        }
}
