package com.imglibrary.imageapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;



public class ImageAppMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =FXMLLoader.load(getClass().getResource("ImageApp.fxml"));
        Scene scene =new Scene(root, 900, 600);
        stage.setTitle("Bibliothèque d'images");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
