package com.ntm.dictionary;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/** Held logics to control function used in FXML */
public class DictionaryApplicationController extends DictionaryManagement {
    private Scanner keyboard;
    @FXML
    private ListView<String> wordViewList;
    @FXML
    private Label thisWordExplain;

    /**
     * Function to load an ArrayList to the ListView.
     *
     * @param data The ArrayList to load.
     */
    private void loadData(ArrayList<Word> data) {
        for (int i = 0; i < data.size(); i++) {
            wordViewList.getItems().add(data.get(i).getWordTarget());
        }
    }

    /**
     * Function to search for and displays the selected wordTarget's explaination.
     */
    @FXML
    private void displayThisWord(MouseEvent event) {
        String thisWordTarget = wordViewList.getSelectionModel().getSelectedItem();
        String foundWordExplain = this.getWordExplain(thisWordTarget);
        thisWordExplain.setText(foundWordExplain);
    }

    @FXML
    public void initialize() {
        this.insertFromFile(keyboard, "GUI");
        loadData(this.words);
    }
}
