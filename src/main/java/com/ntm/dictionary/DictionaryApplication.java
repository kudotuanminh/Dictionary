package com.ntm.dictionary;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.*;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/app.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        stage.setTitle("Dictionary");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
