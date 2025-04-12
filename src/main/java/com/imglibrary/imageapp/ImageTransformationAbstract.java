package com.imglibrary.imageapp;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class ImageTransformationAbstract implements ImageTransformation {
    protected WritableImage createWritableImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        return new WritableImage(width, height);
    }

    protected Color getPixelColor(PixelReader pixelReader, int x, int y) {
        return pixelReader.getColor(x, y);
    }

    protected void setPixelColor(PixelWriter pixelWriter, int x, int y, Color color) {
        pixelWriter.setColor(x, y, color);
    }

    @Override
    public abstract Image transform(Image image);
}
