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
        System.out.print("\033\143");
        System.out.print("Word to search: ");
        String s = keyboard.nextLine();

        String leftAlignFormat = "| %-4d | %-15s | %-15s |%n";

        System.out.format("+------+-----------------+-----------------+%n");
        System.out.format("| No.  | English         | Vietnamese      |%n");
        System.out.format("+------+-----------------+-----------------+%n");

        int dem;
        int stt = 0;
        for (int i = 0; i < this.getSize(); i++) {
            Word currentWord = this.getWord(i);
            dem = 0;
            for (int j = 0; j < s.length(); j++) {
                if (Character.toLowerCase(s.charAt(j)) == Character
                        .toLowerCase(currentWord.getWordTarget().charAt(j))) {
                    dem++;
                }
            }
            if (dem != s.length()) {
                continue;
            } else {
                stt++;
                System.out.format(leftAlignFormat, stt, currentWord.getWordTarget(), currentWord.getWordExplain());
            }
        }

        System.out.format("+------+-----------------+-----------------+%n");
    }

    /**
     * Function to edit the words that have existed in data already.
     */
    public void editWordInData(Scanner keyboard) {
        System.out.print("\033\143");
        System.out.print("Enters number of words to edit: ");

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

        String leftAlignFormat = "| %-4d | %-15s | %-15s |%n";


        for (int i = 0; i < n; i++) {
            boolean check = false;
                System.out.print("Word to edit: ");
                String s = keyboard.nextLine();
                for (int j = 0; j < this.getSize(); j++) {
                    Word currentWord = this.getWord(j);
                    //System.out.println(currentWord.getWordTarget() + " " + s);
                    if (s.equals(currentWord.getWordTarget())) {
                        System.out.println("1. Edit the word.");
                        System.out.println("2. Edit the meaning of word in the database.");
                        System.out.print("\nEnters a number (1, 2) correlate to your choice: ");
                        int choice = keyboard.nextInt();
                        keyboard.nextLine();
                        System.out.println(choice);
                        switch (choice) {
                            case 1:
                                System.out.printf("\nEnters word target: ");
                                String target = keyboard.nextLine();
                                currentWord.setWordTarget(target);
                                break;
                            case 2:
                                System.out.printf("Enters word explaination: ");
                                String explain = keyboard.nextLine();
                                currentWord.setWordExplain(explain);
                                break;
                            default:
                                System.out.printf(
                                        "Invalid menu option! Press any key and enters a single POSITIVE integer! ");
                                keyboard.nextLine();
                                break;
                        }
                        break;
                    }
                }
        }
    }
}
