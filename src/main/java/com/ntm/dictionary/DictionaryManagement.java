package com.ntm.dictionary;

import java.io.*;
import java.util.ArrayList;
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
     * @param args     Arguement to specify whether if program is running in cmdline
     *                 mode or GUI mode.
     */
    public void insertFromFile(Scanner keyboard, String args) {
        try {
            FileInputStream file = new FileInputStream("./resources/dictionaries.txt");
            InputStreamReader inputStream = new InputStreamReader(file, "UTF-8");
            BufferedReader fileReader = new BufferedReader(inputStream);

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
                System.out.print(" from 'dictionaries.txt' to the database.\n\n");
            }

            fileReader.close();
        } catch (IOException e) {
            System.out.print("ERROR: File not found! Press any key to continue... ");
            keyboard.nextLine();
        }
    }

    /**
     * Function to lookup word in the Dictionary's data.
     *
     * @param input The string to search in data.
     * @param args  Arguement to specify whether if program is running in cmdline
     *              mode or GUI mode.
     */
    public ArrayList<Word> dictionaryLookup(String input, String args) {
        String leftAlignFormat = "| %-4d | %-15s | %-15s |%n";
        if (args.equals("cmdline")) {
            System.out.format("+------+-----------------+-----------------+%n");
            System.out.format("| No.  | English         | Vietnamese      |%n");
            System.out.format("+------+-----------------+-----------------+%n");
        }

        ArrayList<Word> wordsFound = new ArrayList<Word>();

        int dem;
        int stt = 0;
        for (int i = 0; i < this.getSize(); i++) {
            Word currentWord = this.getWord(i);
            dem = 0;
            for (int j = 0; j < input.length(); j++) {
                if (Character.toLowerCase(input.charAt(j)) == Character
                        .toLowerCase(currentWord.getWordTarget().charAt(j))) {
                    dem++;
                }
            }
            if (dem != input.length()) {
                continue;
            } else {
                stt++;
                if (args.equals("cmdline")) {
                    System.out.format(leftAlignFormat, stt, currentWord.getWordTarget(), currentWord.getWordExplain());
                }
                wordsFound.add(currentWord);
            }
        }

        if (args.equals("cmdline")) {
            System.out.format("+------+-----------------+-----------------+%n");
        }
        return wordsFound;
    }

    /**
     * Function to edit the words that have existed in data already.
     *
     * @param keyboard The current Scanner that are getting data from keyboard.
     */
    public void editWordInData(Scanner keyboard) {
        String integerPattern = "[+]?\\d+$";
        String x;

        int index;
        do {
            System.out.print("Position of word to edit: ");
            if (keyboard.hasNext(integerPattern)) {
                index = keyboard.nextInt();
                x = String.valueOf(index);
                keyboard.nextLine();
            } else {
                index = -1;
                x = keyboard.nextLine();
            }
            if (index > this.getSize() || index < 1) {
                index = -1;
                System.out.print("\033\143");
                System.out.printf("ERROR: '%s' is not a valid text position.\n", x);
            }
        } while (index == -1);
        Word currentWord = this.getWord(index - 1);

        int choice;
        do {
            System.out.print("\033\143");
            System.out.println("1. Edit the word.");
            System.out.println("2. Edit the meaning of word in the database.");
            System.out.println("3. Delete the word completely from the database.");

            System.out.print("\nEnters a number (1, 2) correlate to your choice: ");

            if (keyboard.hasNext(integerPattern)) {
                choice = keyboard.nextInt();
                x = String.valueOf(choice);
                keyboard.nextLine();
            } else {
                choice = -1;
                x = keyboard.nextLine();
            }
            switch (choice) {
                case 1:
                    String oldTarget = currentWord.getWordTarget();

                    System.out.printf("\nEnters word target: ");
                    String newTarget = keyboard.nextLine();
                    currentWord.setWordTarget(newTarget);

                    System.out.printf("\nDone! '%s' has been changed to '%s'\n", oldTarget, newTarget);
                    break;
                case 2:
                    String oldExplain = currentWord.getWordTarget();

                    System.out.printf("\nEnters word explaination: ");
                    String newExplain = keyboard.nextLine();
                    currentWord.setWordExplain(newExplain);

                    System.out.printf("\nDone! '%s' has been changed to '%s'\n", oldExplain, newExplain);
                    break;
                case 3:
                    this.removeWord(index - 1);
                    System.out.printf("\nDone! Word at '%d' has been removed\n", index);
                    break;
                default:
                    System.out.printf(
                            "'%s' is an invalid menu option! Press any key and enters a single POSITIVE integer! ", x);
                    keyboard.nextLine();
                    break;
            }
        } while (!(choice == 1 || choice == 2 || choice == 3));
    }

    /**
     * Function to export data to 'export.txt'.
     *
     * @param args Arguement to specify whether if program is running in cmdline
     *             mode or GUI mode.
     */
    public void exportToFile(String args) {
        if (args.equals("cmdline")) {
            System.out.print("\033\143");
        }

        try {
            FileWriter fw = new FileWriter("./resources/export.txt");
            for (int i = 0; i < words.size(); i++) {
                fw.write(words.get(i).getWordTarget() + "\n");
                fw.write(words.get(i).getWordExplain() + "\n");
            }
            fw.close();
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
}
