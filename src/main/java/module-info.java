module com.imglibrary.imageapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.imglibrary.imageapp to javafx.fxml;
    exports com.imglibrary.imageapp;
}