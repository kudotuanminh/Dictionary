package com.ntm.dictionary;
import java.io.FileWriter;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    // in ra cac words trong yeu cau 1
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

    public void WordSearcher(String s) {
        //System.out.println("hello");
        int dem = 0 , stt = 0;
        for (int i = 0; i < this.getSize(); i++) {
            Word currentWord = this.getWord(i);
            dem = 0 ;
            for (int j = 0; j < s.length(); j++)   if (s.charAt(j) == (currentWord.getWordTarget().charAt(j)))    ++dem;
            if (dem != s.length())   continue;
            else {
                ++stt;
                System.out.printf("%d\t| %s\t| %s\n", stt ,currentWord.getWordTarget(), currentWord.getWordExplain());
            }
        }
    }

    //tu dien so khai
    public void dictionaryBasic() {
        Scanner keyboard = new Scanner(System.in);

        int choice;
        do {
            System.out.print("\033\143");
            System.out.println("\t\tThis is a simple dictionary\n");
            System.out.println("1. View all words currently in the database.");
            System.out.println("2. Input words from Commandline.");
            System.out.println("3. Search in Kudo.");
            System.out.println("4. Insert From File .");
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
                case 3:
                    System.out.println("Word you need to find :" );
                    String finds = this.search_tester();
                    System.out.println("--------suggested for you-------:" );
                    this.WordSearcher(finds);
                    System.out.println("-----------------------------------------------");
                    keyboard.nextLine();
                    break;
                case 4:
                    this.insertFromFile(keyboard);
                    System.out.print("Press any key to continue... ");
                    keyboard.nextLine();
                    break;
                case 0:
                    System.out.print("\nGoodbye! Please Press any key to export to File");
                    keyboard.nextLine();
                    //System.exit(0);
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


        //Export to File dictionaries.txt
        // source:https://stackoverflow.com/questions/6548157/how-to-write-an-arraylist-of-strings-into-a-text-file
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Vo Dinh Huy\\OneDrive\\Documents\\GitHub\\Dictionary\\resources\\dictionaries.txt");
            for (int i = 0; i < Dictionary.words.size(); i++) {
                fw.write(words.get(i).getWordTarget() + "\n");
                fw.write(words.get(i).getWordExplain() + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Success Export to File");
    }
}
