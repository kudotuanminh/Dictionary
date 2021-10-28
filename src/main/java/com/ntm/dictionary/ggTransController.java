package com.ntm.dictionary;

import java.net.*;
import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import com.jfoenix.controls.*;

/** Held logics to control functions used in ggtrans FXML */
public class ggTransController extends DictionaryManagement {
    @FXML
    private StackPane stackPane;
    @FXML
    private ComboBox<String> sourceComboBox;
    @FXML
    private ComboBox<String> targetComboBox;
    @FXML
    private TextArea sourceTextArea;
    @FXML
    private TextArea targetTextArea;
    @FXML
    private HBox contactSupport;
    @FXML
    private HBox dictionaryTab;

    @FXML
    private void translate(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            String text = sourceTextArea.getText();
            String langFrom =
                    sourceComboBox.getSelectionModel().getSelectedItem();
            String langTo =
                    targetComboBox.getSelectionModel().getSelectedItem();

            URL url = new URL(
                    "https://script.google.com/macros/s/AKfycbzLFsIFyKdl05Ppfi-3fVPQQ1dfwNY5dc2HLTWnvzdYS3D4Wgg/exec"
                            + "?q=" + URLEncoder.encode(text, "UTF-8")
                            + "&target=" + langTo + "&source=" + langFrom);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader googleReader =
                    new BufferedReader(new InputStreamReader(
                            connection.getInputStream(), "UTF-8"));

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = googleReader.readLine()) != null) {
                response.append(inputLine);
            }

            googleReader.close();

            targetTextArea.setText(response.toString());
        }
    }

    @FXML
    private void mainAppScene(MouseEvent event) throws Exception {
        Stage stage = (Stage) dictionaryTab.getScene().getWindow();
        Parent root =
                FXMLLoader.load(new File("src/main/app.fxml").toURI().toURL());

        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Function to view credit when clicked on About tab. */
    @FXML
    private void viewCredit(MouseEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Team Members"));
        content.setBody(new Text(
                "Ngo Tuan Minh - 20020059\nTran Ngoc Truc Linh - 20020113\nVo Dinh Huy - 20020198"));

        JFXDialog dialog = new JFXDialog(stackPane, content,
                JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
    }

    @FXML
    public void initialize() {
        final ObservableList<String> langList = FXCollections
                .observableArrayList("en", "ko", "vi", "zh-CN", "zh-TW");
        this.targetTextArea.setEditable(false);

        this.sourceComboBox.setItems(langList);
        this.sourceComboBox.getSelectionModel().select(0);
        this.targetComboBox.setItems(langList);
        this.targetComboBox.getSelectionModel().select(2);
    }
}
