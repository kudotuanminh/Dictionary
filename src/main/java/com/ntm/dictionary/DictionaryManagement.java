package com.ntm.dictionary;

import java.util.Scanner;

public class DictionaryManagement extends Dictionary {
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
    public String search_tester(){
        Scanner input = new Scanner(System.in);

        String wordfind = input.nextLine();
        return wordfind;
    }
}
