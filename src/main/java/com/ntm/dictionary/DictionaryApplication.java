package com.ntm.dictionary;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.Parent;
import java.io.*;

import javafx.fxml.FXMLLoader;

/** Helds logics to load JavaFX's GUI by FXML. */
public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =
                FXMLLoader.load(new File("src/main/app.fxml").toURI().toURL());

        stage.setTitle("Dictionary");
        stage.getIcons().addAll(
                new Image(new File("src/images/icon.png").toURI().toString()));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
