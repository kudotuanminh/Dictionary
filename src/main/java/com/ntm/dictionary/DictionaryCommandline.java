package com.ntm.dictionary;

import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    public void showAllWords() {
        System.out.print("\033\143");
        System.out.println(" No.\t| English\t| Vietnamese");
        System.out.println("-----------------------------------------------");

        for (int i = 0; i < this.getSize(); i++) {
            Word currentWord = this.getWord(i);
            System.out.printf(" %d\t| %s\t\t| %s\n", i + 1, currentWord.getWordTarget(), currentWord.getWordExplain());
        }

        System.out.println("\n");
    }

    public void dictionaryBasic() {
        Scanner keyboard = new Scanner(System.in);

        int choice;
        do {
            System.out.print("\033\143");
            System.out.println("\t\tThis is a simple dictionary\n");
            System.out.println("1. View all words currently in the database.");
            System.out.println("2. Input words from Commandline.");
            System.out.println("0. Quit application.");

            System.out.print("\nEnters a number (1, 2,...) correlate to your choice: ");
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
                    this.insertFromCommandline(keyboard);
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 0:
                    System.out.print("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.printf(
                            "'%s' is an invalid menu option! Press any key and enters a single POSITIVE integer! ", x);
                    keyboard.nextLine();
                    break;
            }
        } while (choice != 0);

        keyboard.close();
    }

    public static void main(String[] args) {
        DictionaryCommandline dict = new DictionaryCommandline();
        dict.dictionaryBasic();
    }
}