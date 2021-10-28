package com.ntm.dictionary;

import java.sql.*;
import java.io.*;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
// import javafx.scene.web.*;
import com.jfoenix.controls.*;
import org.kordamp.ikonli.javafx.*;
import com.sun.speech.freetts.*;

/** Held logics to control functions used in main FXML */
public class DictionaryApplicationController extends DictionaryManagement {
    @FXML
    private StackPane stackPane;
    @FXML
    private TextField wordSearch;
    @FXML
    private ListView<String> wordViewList;
    // TODO maybe change this to html
    @FXML
    private TextArea thisWordExplain;
    @FXML
    private FontIcon speakIcon;
    @FXML
    private Label thisWordPronounce;
    @FXML
    private HBox contactSupport;
    @FXML
    private HBox ggtransTab;

    /**
     * Function to load an ArrayList to the ListView.
     *
     * @param data The ArrayList to load.
     */
    private void loadData(ArrayList<Word> data) {
        this.wordViewList.getItems().clear();
        for (int i = 0; i < data.size(); i++) {
            this.wordViewList.getItems().add(data.get(i).getWordTarget());
        }
        if (wordViewList.getItems().isEmpty()) {
            this.thisWordExplain.setText("");
        } else {
            wordViewList.getSelectionModel().select(0);
            this.displayThisWord();
        }
    }

    /**
     * Function to search for and displays the selected wordTarget's
     * explaination.
     */
    @FXML
    private void displayThisWord() {
        String thisWordTarget =
                wordViewList.getSelectionModel().getSelectedItem();
        String foundWordExplain = this.getWordExplain(thisWordTarget);
        String foundWordPronounce = this.getWordPronounce(thisWordTarget);

        this.thisWordExplain.setText(foundWordExplain);
        this.thisWordPronounce.setText(foundWordPronounce);
    }

    /** Function to search for words when typed in search box. */
    @FXML
    private void search(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (wordSearch.getText().isEmpty()) {
                this.loadData(this.words);
            } else {
                this.loadData(
                        this.dictionaryLookup(wordSearch.getText(), "GUI"));
            }
        }
    }

    /** Function to create dialogs to add new word to data. */
    @FXML
    private void addWordDialog() throws SQLException, ClassNotFoundException {
        TextInputDialog wordTargetInput = new TextInputDialog();
        wordTargetInput.setContentText("Enter a new word: ");
        Optional<String> wordTargetResult = wordTargetInput.showAndWait();
        String thisWordTarget = wordTargetResult.get();

        if (this.getIndex(thisWordTarget) == -1) {
            TextInputDialog wordExplainInput = new TextInputDialog();
            wordExplainInput.setContentText("Enter its meaning: ");
            Optional<String> wordExplainResult = wordExplainInput.showAndWait();
            String thisWordExplain = wordExplainResult.get();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("wordTarget: '" + thisWordTarget
                    + "'\nwordExplain: '" + thisWordExplain
                    + "'\nDo you want to save this word?");

            ButtonType buttonTypeSave =
                    new ButtonType("Save", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel =
                    new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

            if (result.get() == buttonTypeSave) {
                this.addWord(new Word(thisWordTarget, thisWordExplain));
                Statement statement = this.newStatement();
                statement.executeUpdate(
                        "INSERT INTO av (word, description, pronounce) "
                                + "VALUES ('" + thisWordTarget + "', '"
                                + thisWordExplain + "', '')");

                alert2.setContentText("Done! '" + thisWordTarget
                        + "' has been added to current database.");
                alert2.show();
                this.loadData(this.words);
            } else if (result.get() == buttonTypeCancel) {
                alert2.setContentText("No changes were made.");
                alert2.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("\"" + thisWordTarget
                    + "\" already exist. Maybe try edit the word instead?");
            alert.show();
        }
    }

    /** Function to remove currently selected word from data. */
    @FXML
    private void removeWordDialog()
            throws SQLException, ClassNotFoundException {
        if (!wordViewList.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(
                    "Are you sure you want to remove this word from database?\nTHIS ACTION CANNOT BE UNDONE!!");

            ButtonType buttonTypeYes =
                    new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo =
                    new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

            String thisWordTarget =
                    wordViewList.getSelectionModel().getSelectedItem();
            if (result.get() == buttonTypeYes) {
                this.removeWord(this.getIndex(thisWordTarget));
                Statement statement = this.newStatement();
                statement.executeUpdate(
                        "DELETE FROM av WHERE word='" + thisWordTarget + "'");

                alert2.setContentText("Done! '" + thisWordTarget
                        + "' has been remove from current database.");
                alert2.show();
                this.loadData(this.words);
            } else if (result.get()
                    .getButtonData() == ButtonBar.ButtonData.NO) {
                alert2.setContentText("No changes were made.");
                alert2.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No word found.");
            alert.show();
        }
    }

    /** Function to edit currently selected word in data. */
    @FXML
    private void editWordDialog() throws SQLException, ClassNotFoundException {
        if (!wordViewList.getItems().isEmpty()) {
            String thisWordTarget =
                    wordViewList.getSelectionModel().getSelectedItem();
            String thisWordExplain = this.getWordExplain(thisWordTarget);
            int index = getIndex(thisWordTarget);

            TextInputDialog wordTargetInput =
                    new TextInputDialog(thisWordTarget);
            wordTargetInput.setContentText("wordTarget: ");
            Optional<String> wordTargetResult = wordTargetInput.showAndWait();

            TextInputDialog wordExplainInput =
                    new TextInputDialog(thisWordExplain);
            wordExplainInput.setContentText("wordExplain: ");
            Optional<String> wordExplainResult = wordExplainInput.showAndWait();

            String newWordTarget = wordTargetResult.get();
            String newWordExplain = wordExplainResult.get();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("wordTarget: '" + newWordTarget
                    + "'\nwordExplain: '" + newWordExplain
                    + "'\nDo you want to save this word?");

            ButtonType buttonTypeSave =
                    new ButtonType("Save", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel =
                    new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

            if (result.get() == buttonTypeSave) {
                this.getWord(index).setWordTarget(newWordTarget);
                this.getWord(index).setWordExplain(newWordExplain);
                Statement statement = this.newStatement();
                statement.executeUpdate("UPDATE av SET word='" + newWordTarget
                        + "', description='" + newWordExplain + "' WHERE word='"
                        + thisWordTarget + "'");

                alert2.setContentText(
                        "Done! '" + newWordTarget + "' has been edited.");
                alert2.show();
                this.loadData(this.words);
            } else if (result.get() == buttonTypeCancel) {
                alert2.setContentText("No changes were made.");
                alert2.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No word found.");
            alert.show();
        }
    }

    /** Function to export data to 'export.txt'. */
    @FXML
    private void exportToFileDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        this.exportToFile("GUI");
        int n = words.size();
        String content =
                "Finished exporting " + n + (n > 1 ? " words" : " word")
                        + " from database to 'export.txt'.";
        alert.setContentText(content);
        alert.show();
    }

    /** Function to load backed-up database. */
    @FXML
    private void loadBackupDialog()
            throws IOException, SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(
                "Are you sure you want to restore backed-up database? This will undo all changes you've made to the data.\nTHIS ACTION CANNOT BE UNDONE!!");

        ButtonType buttonTypeYes =
                new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

        if (result.get() == buttonTypeYes) {
            InputStream is =
                    new FileInputStream(new File("resources/backup.db"));
            OutputStream os =
                    new FileOutputStream(new File("resources/dictionaries.db"));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

            alert2.setContentText("Restore completed.");
            alert2.show();
            this.insertFromDatabase();
            this.loadData(this.words);

            is.close();
            os.close();
        } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO) {
            alert2.setContentText("No changes were made.");
            alert2.show();
        }
    }

    /** Function to play currently selected word by TTS. */
    @FXML
    private void playVoice() {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
        }
        try {
            voice.setRate(190);
            voice.setPitch(150);
            voice.setVolume(3);

            String thisWordTarget =
                    wordViewList.getSelectionModel().getSelectedItem();
            voice.speak(thisWordTarget);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void ggTransScene() throws Exception {
        Stage stage = (Stage) ggtransTab.getScene().getWindow();
        Parent root = FXMLLoader
                .load(new File("src/main/ggtrans.fxml").toURI().toURL());

        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Function to view credit when clicked on About tab. */
    @FXML
    private void viewCredit() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Team Members"));
        content.setBody(new Text(
                "Ngo Tuan Minh - 20020059\nTran Ngoc Truc Linh - 20020113\nVo Dinh Huy - 20020198"));

        JFXDialog dialog = new JFXDialog(stackPane, content,
                JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        this.insertFromDatabase();
        this.loadData(this.words);
        this.thisWordExplain.setEditable(false);
    }
}
