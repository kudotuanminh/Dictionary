package com.ntm.dictionary;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import com.jfoenix.controls.*;
import org.kordamp.ikonli.javafx.*;
import com.sun.speech.freetts.*;

/** Held logics to control functions used in FXML */
public class DictionaryApplicationController extends DictionaryManagement {
    private Scanner keyboard;
    @FXML
    private StackPane stackPane;
    @FXML
    private TextField wordSearch;
    @FXML
    private ListView<String> wordViewList;
    @FXML
    private TextArea thisWordExplain;
    @FXML
    private HBox contactSupport;
    @FXML
    private HBox dictionaryTab;
    @FXML
    private FontIcon speakIcon;

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
        if (!wordViewList.getItems().isEmpty()) {
            wordViewList.getSelectionModel().select(0);
            String thisWordTarget = wordViewList.getSelectionModel().getSelectedItem();
            String foundWordExplain = this.getWordExplain(thisWordTarget);
            this.thisWordExplain.setText(foundWordExplain);
        }
    }

    /**
     * Function to search for and displays the selected wordTarget's explaination.
     */
    @FXML
    private void displayThisWord(MouseEvent event) {
        String thisWordTarget = wordViewList.getSelectionModel().getSelectedItem();
        String foundWordExplain = this.getWordExplain(thisWordTarget);
        this.thisWordExplain.setText(foundWordExplain);
    }

    /** Function to search for words when typed in search box. */
    @FXML
    private void onKeyReleasedSearch(KeyEvent event) {
        this.loadData(this.dictionaryLookup(wordSearch.getText(), "GUI"));
    }

    /** Function to create dialogs to add new word to data. */
    @FXML
    private void addWordDialog(MouseEvent event) {
        TextInputDialog wordTargetInput = new TextInputDialog();
        wordTargetInput.setContentText("Enter a new word: ");
        Optional<String> wordTargetResult = wordTargetInput.showAndWait();

        TextInputDialog wordExplainInput = new TextInputDialog();
        wordExplainInput.setContentText("Enter its meaning: ");
        Optional<String> wordExplainResult = wordExplainInput.showAndWait();

        String thisWordTarget = wordTargetResult.get();
        String thisWordExplain = wordExplainResult.get();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("wordTarget: '" + thisWordTarget + "'\nwordExplain: '" + thisWordExplain
                + "'\nDo you want to save this word?");

        ButtonType buttonTypeSave = new ButtonType("Save", ButtonBar.ButtonData.APPLY);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

        if (result.get() == buttonTypeSave) {
            this.addWord(new Word(thisWordTarget, thisWordExplain));
            alert2.setContentText("Done! '" + thisWordTarget + "' has been added to current data.");
            alert2.show();
            this.loadData(this.words);
        } else if (result.get() == buttonTypeCancel) {
            alert2.setContentText("No changes were made.");
            alert2.show();
        }
    }

    /** Function to remove currently selected word from data. */
    @FXML
    private void removeWordDialog(MouseEvent event) {
        if (!wordViewList.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(
                    "Are you sure you want to remove this word from data?\nTHIS ACTION CANNOT BE UNDONE!!");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

            String thisWordTarget = wordViewList.getSelectionModel().getSelectedItem();
            if (result.get() == buttonTypeYes) {
                this.removeWord(this.getIndex(thisWordTarget));
                alert2.setContentText("Done! '" + thisWordTarget + "' has been remove from current data.");
                alert2.show();
                this.loadData(this.words);
            } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO) {
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
    private void editWordDialog(MouseEvent event) {
        if (!wordViewList.getItems().isEmpty()) {
            String thisWordTarget = wordViewList.getSelectionModel().getSelectedItem();
            String thisWordExplain = this.getWordExplain(thisWordTarget);
            int index = getIndex(thisWordTarget);

            TextInputDialog wordTargetInput = new TextInputDialog(thisWordTarget);
            wordTargetInput.setContentText("wordTarget: ");
            Optional<String> wordTargetResult = wordTargetInput.showAndWait();

            TextInputDialog wordExplainInput = new TextInputDialog(thisWordExplain);
            wordExplainInput.setContentText("wordExplain: ");
            Optional<String> wordExplainResult = wordExplainInput.showAndWait();

            String newWordTarget = wordTargetResult.get();
            String newWordExplain = wordExplainResult.get();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("wordTarget: '" + newWordTarget + "'\nwordExplain: '" + newWordExplain
                    + "'\nDo you want to save this word?");

            ButtonType buttonTypeSave = new ButtonType("Save", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

            if (result.get() == buttonTypeSave) {
                this.getWord(index).setWordTarget(newWordTarget);
                this.getWord(index).setWordExplain(newWordExplain);
                alert2.setContentText("Done! '" + newWordTarget + "' has been edited.");
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
    private void exportToFileDialog(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        this.exportToFile("GUI");
        int n = words.size();
        String content = "Finished exporting " + n + (n > 1 ? " words" : " word") + " from database to 'export.txt'.";
        alert.setContentText(content);
        alert.show();
    }

    /** Function to play currently selected word by TTS. */
    @FXML
    private void playVoice(MouseEvent event) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
        }
        try {
            voice.setRate(190);
            voice.setPitch(150);
            voice.setVolume(3);

            String thisWordTarget = wordViewList.getSelectionModel().getSelectedItem();
            voice.speak(thisWordTarget);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    /** Function to view credit when clicked on About tab. */
    @FXML
    private void viewCredit(MouseEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Team Members"));
        content.setBody(new Text("Ngo Tuan Minh - 20020059\nTran Ngoc Truc Linh - 20020113\nVo Dinh Huy - 20020198"));

        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
    }

    @FXML
    public void initialize() {
        this.insertFromFile(keyboard, "GUI");
        this.loadData(this.words);
    }
}
