package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

//Classe TransformationSymetrie  qui herite de transformation pour les symétries
public class TransformationSymetrie extends ImageTransformationAbstract{

        public enum SymetryType{
            HORIZONTAL,
            VERTICAL
        }

        private final SymetryType type;

        public TransformationSymetrie(SymetryType type)
        {
            this.type=type;
        }

        @Override
        public Image transform(Image image){
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            WritableImage imageMirroir=createWritableImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter pixelWriter= imageMirroir.getPixelWriter();

            switch (type){
                case HORIZONTAL:
                    for (int y=0; y<height; y++){
                        for (int x=0; x<width; x++){
                            Color color=pixelReader.getColor(x, y);
                            pixelWriter.setColor(width - 1 - x, y, color);
                        }
                    }
                    break;
                case VERTICAL:
                    for (int y=0; y<height; y++){
                        for (int x=0; x<width; x++){
                            Color color=pixelReader.getColor(x, y);
                            pixelWriter.setColor(x, height - 1 - y, color);
                        }
                    }
                    break;
            }

            return imageMirroir;

    }
}
