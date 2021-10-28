package com.ntm.dictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import org.sqlite.*;
import java.sql.*;

import Algorithm.Trie;
import Algorithm.BKTree;

/** Held logics to manage the Dictionary. */
public class DictionaryManagement extends Dictionary {
    BKTree bkTree = new BKTree();

    /**
     * Function to start entering words from 'dictionaries.txt' to the
     * Dictionary's data.
     *
     * @param keyboard The current Scanner that are getting data from keyboard.
     * @param args Arguement to specify whether if program is running in cmdline
     *        mode or GUI mode.
     */
    public void insertFromFile(Scanner keyboard, String args) {
        try {
            BufferedReader fileReader =
                    new BufferedReader(new InputStreamReader(
                            new FileInputStream("./resources/dictionaries.txt"),
                            "UTF-8"));

            if (args.equals("cmdline")) {
                System.out.print("\033\143");
                System.out.print(
                        "Please make sure to verify integrity of 'dictionaries.txt' before proceeding.\nPress any key to continue... ");
                keyboard.nextLine();
            }

            int n = 0;
            String line;
            while ((line = fileReader.readLine()) != null) {
                String target = line;
                line = fileReader.readLine();
                String explain = line;

                Word newWord = new Word(target, explain);
                this.addWord(newWord);
                n++;
            }

            if (args.equals("cmdline")) {
                System.out.print("\nFinished importing ");
                System.out.printf((n > 1 ? "%d words" : "%d word"), n);
                System.out
                        .print(" from 'dictionaries.txt' to the database.\n\n");
            }

            fileReader.close();
        } catch (IOException e) {
            if (args.equals("cmdline")) {
                System.out.print(
                        "ERROR: File not found! Press any key to continue... ");
                keyboard.nextLine();
            }
        }
    }

    public Statement newStatement()
            throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        SQLiteConfig config = new SQLiteConfig();
        config.enableFullSync(true);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);

        dataSource.setUrl("jdbc:sqlite:"
                + new File("resources/dictionaries.db").toURI().toString());
        Connection connection = dataSource.getConnection();
        return connection.createStatement();
    }

    /**
     * Function to start entering words from 'dictionaries.db' by SQLite to the
     * Dictionary's data.
     */
    public void insertFromDatabase()
            throws SQLException, ClassNotFoundException {
        this.words.clear();

        Statement statement = this.newStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM 'av'");
        while (resultSet.next()) {
            String thisWordTarget = resultSet.getString("word");
            String thisWordExplain = resultSet.getString("description");
            String thisWordPronounce =
                    "/" + resultSet.getString("pronounce") + "/";
            Word thisWord = new Word(thisWordTarget, thisWordExplain,
                    thisWordPronounce);
            this.bkTree.add(thisWord);
            this.addWord(thisWord);
        }
    }

    /**
     * Function to export data to 'export.txt'.
     *
     * @param args Arguement to specify whether if program is running in cmdline
     *        mode or GUI mode.
     */
    public void exportToFile(String args) {
        if (args.equals("cmdline")) {
            System.out.print("\033\143");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("./resources/export.txt", true),
                    StandardCharsets.UTF_8));
            for (int i = 0; i < words.size(); i++) {
                bw.write(words.get(i).getWordTarget() + "\n");
                bw.write(words.get(i).getWordExplain() + "\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (args.equals("cmdline")) {
            int n = words.size();
            System.out.print("Finished exporting ");
            System.out.printf((n > 1 ? "%d words" : "%d word"), n);
            System.out.print(" from database to 'export.txt'.\n\n");
        }
    }

    /**
     * Function to lookup word in the Dictionary's data.
     *
     * @param input The string to search in data.
     * @param args Arguement to specify whether if program is running in cmdline
     *        mode or GUI mode.
     */
    public ArrayList<Word> dictionaryLookup(String input, String args) {
        String leftAlignFormat = "| %-4d | %-15s | %-15s |%n";
        if (args.equals("cmdline")) {
            System.out.format("+------+-----------------+-----------------+%n");
            System.out.format("| No.  | English         | Vietnamese      |%n");
            System.out.format("+------+-----------------+-----------------+%n");
        }

        ArrayList<Word> wordsFound = new ArrayList<Word>();

        Trie Trie_array = new Trie();
        for (int i = 0; i < this.getSize(); i++) {
            Trie_array.insert(words.get(i).getWordTarget());
        }
        ArrayList<String> result = (ArrayList<String>) Trie.autoComplete(input);
        for (int i = 0; i < result.size(); i++) {
            Word currentWord = this.getWord(this.getIndex(result.get(i)));
            if (args.equals("cmdline")) {
                System.out.format(leftAlignFormat, i + 1,
                        currentWord.getWordTarget(),
                        currentWord.getWordExplain());
            }
            wordsFound.add(currentWord);
        }

        if (wordsFound.isEmpty()) {
            wordsFound.addAll(findSimilarWords(input, args));
        }

        if (args.equals("cmdline")) {
            System.out.format("+------+-----------------+-----------------+%n");
        }

        return wordsFound;
    }

    public ArrayList<Word> findSimilarWords(String input, String args) {
        String leftAlignFormat = "| %-4d | %-15s | %-15s |%n";
        if (args.equals("cmdline")) {
            System.out.format("|---------------similar words--------------|%n");
            System.out.format("+------+-----------------+-----------------+%n");
        }
        ArrayList<Word> wordsFound = bkTree.search(input, 2);
        if (args.equals("cmdline")) {
            for (int i = 0; i < wordsFound.size(); ++i) {
                Word currentWord = wordsFound.get(i);
                System.out.format(leftAlignFormat, i + 1,
                        currentWord.getWordTarget(),
                        currentWord.getWordExplain());
            }
        }
        return wordsFound;
    }
}
