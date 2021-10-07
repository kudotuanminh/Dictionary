package com.ntm.dictionary;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class DictionaryApplicationController extends DictionaryManagement {
    private Scanner keyboard;
    @FXML
    private ListView<String> wordViewList;
    @FXML
    private Label thisWordExplain;

    private void loadData(ArrayList<Word> data) {
        for (int i = 0; i < data.size(); i++) {
            wordViewList.getItems().add(data.get(i).getWordTarget());
        }
    }

    @FXML
    private void displayThisWord(MouseEvent event) {
        String thisWordTarget = wordViewList.getSelectionModel().getSelectedItem();
        String foundWordExplain = this.getWordExplain(thisWordTarget);
        System.out.println(foundWordExplain);
        thisWordExplain.setText(foundWordExplain);
    }

    @FXML
    public void initialize() {
        this.insertFromFile(keyboard, "GUI");
        loadData(this.words);
    }
}
