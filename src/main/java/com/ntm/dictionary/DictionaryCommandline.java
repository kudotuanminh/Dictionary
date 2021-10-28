package com.ntm.dictionary;

import java.util.Scanner;

/** Held logics to create a Dictionary in the commandline */
public class DictionaryCommandline extends DictionaryManagement {
    /** Function to print all words that are in the Dictionary's data. */
    public void showAllWords() {
        System.out.print("\033\143");

        String leftAlignFormat = "| %-4d | %-15s | %-15s |%n";

        System.out.format("+------+-----------------+-----------------+%n");
        System.out.format("| No.  | English         | Vietnamese      |%n");
        System.out.format("+------+-----------------+-----------------+%n");

        for (int i = 0; i < this.getSize(); i++) {
            Word currentWord = this.getWord(i);
            System.out.format(leftAlignFormat, i + 1,
                    currentWord.getWordTarget(), currentWord.getWordExplain());
        }

        System.out.format("+------+-----------------+-----------------+%n");
    }


    /**
     * Function to start entering words from commandline to the Dictionary's
     * data.
     *
     * @param keyboard The current Scanner that are getting data from keyboard.
     */
    public void insertFromCommandline(Scanner keyboard) {
        System.out.print("\033\143");
        System.out.print("Enters number of words to input: ");

        String integerPattern = "[+]?\\d+$";
        while (!keyboard.hasNext(integerPattern)) {
            String x = keyboard.nextLine();
            System.out.printf(
                    "'%s' is an invalid input! Press any key and enters a single POSITIVE integer! ",
                    x);
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
                System.out.printf("ERROR: '%s' is not a valid text position.\n",
                        x);
            }
        } while (index == -1);
        Word currentWord = this.getWord(index - 1);

        int choice;
        do {
            System.out.print("\033\143");
            System.out.println("1. Edit the word.");
            System.out.println("2. Edit the meaning of word in the database.");
            System.out.println(
                    "3. Delete the word completely from the database.");

            System.out.print(
                    "\nEnters a number (1, 2) correlate to your choice: ");

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

                    System.out.printf("\nDone! '%s' has been changed to '%s'\n",
                            oldTarget, newTarget);
                    break;
                case 2:
                    String oldExplain = currentWord.getWordTarget();

                    System.out.printf("\nEnters word explaination: ");
                    String newExplain = keyboard.nextLine();
                    currentWord.setWordExplain(newExplain);

                    System.out.printf("\nDone! '%s' has been changed to '%s'\n",
                            oldExplain, newExplain);
                    break;
                case 3:
                    this.removeWord(index - 1);
                    System.out.printf("\nDone! Word at '%d' has been removed\n",
                            index);
                    break;
                default:
                    System.out.printf(
                            "'%s' is an invalid menu option! Press any key and enters a single POSITIVE integer! ",
                            x);
                    keyboard.nextLine();
                    break;
            }
        } while (!(choice == 1 || choice == 2 || choice == 3));
    }

    /**
     * Advanced version of the commandline dictionary.
     */
    public void dictionaryAdvanced() {
        Scanner keyboard = new Scanner(System.in);

        int choice;
        do {
            System.out.print("\033\143");
            System.out.println(
                    "\t\tThis is a simple dictionary, advanced version.\n");
            System.out.println("1. View all words currently in the database.");
            System.out.println("2. Search for words in the database.");
            System.out.println("3. Input words from Commandline.");
            System.out.println(
                    "4. Input words from '/resources/dictionaries.txt'.");
            System.out.println("5. Edit word currently in database.");
            System.out.println("6. Export words to '/resources/export.txt'.");
            System.out.println("0. Quit application.");

            System.out.print(
                    "\nEnters a number (1, 2,...) correlate to your choice: ");
            String integerPattern = "[+]?\\d+$";
            String x;
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
                    this.showAllWords();
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 2:
                    System.out.print("\033\143");
                    System.out.print("Word to search: ");
                    String s = keyboard.nextLine();

                    this.dictionaryLookup(s, "cmdline");
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 3:
                    this.insertFromCommandline(keyboard);
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 4:
                    this.insertFromFile(keyboard, "cmdline");
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 5:
                    this.showAllWords();
                    this.editWordInData(keyboard);
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 6:
                    this.exportToFile("cmdline");
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 0:
                    System.out.print("\nGoodbye!");
                    keyboard.nextLine();
                    System.exit(0);
                    break;
                default:
                    System.out.printf(
                            "'%s' is an invalid menu option! Press any key and enters a single POSITIVE integer! ",
                            x);
                    keyboard.nextLine();
                    break;
            }
        } while (choice != 0);

        keyboard.close();
    }
}
