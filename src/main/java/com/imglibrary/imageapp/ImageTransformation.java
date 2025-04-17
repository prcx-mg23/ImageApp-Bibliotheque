package com.imglibrary.imageapp;

import javafx.scene.image.Image;

//Interface pour toutes les transformations (Rotations, Symetries, filtres)
public interface ImageTransformation{
        Image transform(Image image);
}
