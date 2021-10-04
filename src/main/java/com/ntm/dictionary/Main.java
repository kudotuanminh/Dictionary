package com.ntm.dictionary;
import java.io.FileWriter;

/** Main class */
public class Main {
    public static void main(String[] args) {
        DictionaryCommandline dict = new DictionaryCommandline();
        dict.dictionaryAdvanced();
        //Export to File dictionaries.txt
        // source:https://stackoverflow.com/questions/6548157/how-to-write-an-arraylist-of-strings-into-a-text-file
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Vo Dinh Huy\\OneDrive\\Documents\\GitHub\\Dictionary\\resources\\dictionaries.txt");
            for (int i = 0; i < Dictionary.words.size(); i++) {
                fw.write(Dictionary.words.get(i).getWordTarget() + "\n");
                fw.write(Dictionary.words.get(i).getWordExplain() + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Success Export to File");
    }

}

