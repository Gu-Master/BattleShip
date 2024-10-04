package org.example;

import java.util.Scanner;

public class Main {
    public void printMenu(){
        System.out.println("Start game(restart) - 1");
        System.out.println("End game - q");
    }

    public static void main(String[] args) {
        Main menu = new Main();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            menu.printMenu();
            String command = scanner.nextLine();
            if (command.equals("1")) {
                Battle battle = new Battle();
                battle.startBattle();
            } else if (command.equals("q")) {
                break;
            }
        }
    }
}
