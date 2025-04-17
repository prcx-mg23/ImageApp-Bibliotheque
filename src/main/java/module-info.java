module com.imglibrary.imageapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires com.google.gson;

    opens com.imglibrary.imageapp to javafx.fxml, com.google.gson;
    exports com.imglibrary.imageapp;
}