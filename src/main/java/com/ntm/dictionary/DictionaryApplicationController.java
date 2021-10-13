package com.ntm.dictionary;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import com.jfoenix.controls.*;

/** Held logics to control functions used in FXML */
public class DictionaryApplicationController extends DictionaryManagement {
    private Scanner keyboard;
    @FXML
    StackPane stackPane;
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
