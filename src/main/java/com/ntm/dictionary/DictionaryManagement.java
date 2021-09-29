package com.ntm.dictionary;

import java.io.*;
import java.util.Scanner;

/** Held logics to manage the Dictionary. */
public class DictionaryManagement extends Dictionary {
    /**
     * Function to start entering words from commandline to the Dictionary's data.
     *
     * @param keyboard The current Scanner that are getting data from keyboard.
     */
    public void insertFromCommandline(Scanner keyboard) {
        System.out.print("\033\143");
        System.out.print("Enters number of words to input: ");

        String integerPattern = "[+]?\\d+$";
        while (!keyboard.hasNext(integerPattern)) {
            String x = keyboard.nextLine();
            System.out.printf("'%s' is an invalid input! Press any key and enters a single POSITIVE integer! ", x);
            keyboard.nextLine();

            System.out.print("\033\143");
            System.out.print("Enters number of words to input: ");
        }
        int n = keyboard.nextInt();
        keyboard.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.printf("\nEnters #%d word target: ", i + 1);
            String target = keyboard.nextLine();
            System.out.printf("Enters #%d word explaination: ", i + 1);
            String explain = keyboard.nextLine();

            Word newWord = new Word(target, explain);
            this.addWord(newWord);
        }

        System.out.print("\nFinished entering ");
        System.out.printf((n > 1 ? "%d words" : "%d word"), n);
        System.out.print(" from Commandline to the database.\n\n");
    }

    /**
     * Function to start entering words from 'dictionaries.txt' to the Dictionary's
     * data.
     *
     * @param keyboard The current Scanner that are getting data from keyboard.
     */
    public void insertFromFile(Scanner keyboard) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("./resources/dictionaries.txt"));

            System.out.print("\033\143");
            System.out.print(
                    "Please make sure to verify integrity of 'dictionaries.txt' before proceeding.\nPress any key to continue... ");
            keyboard.nextLine();
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

            System.out.print("\nFinished importing ");
            System.out.printf((n > 1 ? "%d words" : "%d word"), n);
            System.out.print(" from 'dictionaries.txt' to the database.\n\n");

            fileReader.close();
        } catch (IOException e) {
            System.out.print("ERROR: File not found! Press any key to continue... ");
            keyboard.nextLine();
        }
    }

    /**
     * Function to lookup word in the Dictionary's data.
     *
     * @param keyboard The current Scanner that are getting data from keyboard.
     */
    public void dictionaryLookup(Scanner keyboard) {

    }
}
