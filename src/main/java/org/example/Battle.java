package org.example;

import java.util.Scanner;


import java.util.HashSet;
import java.util.Set;

public class Battle {
    private Map playerMap;
    private Map enemyMap;
    private boolean playerTurn = true; // Переключение между ходами
    private Set<String> playerShots; // Хранит выстрелы игрока
    private Set<String> enemyShots; // Хранит выстрелы противника

    public Battle() {
        playerMap = new Map();
        enemyMap = new Map();
        playerMap.createMap();
        enemyMap.createMap();
        playerShots = new HashSet<>();
        enemyShots = new HashSet<>();
    }

    public void startBattle() {
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            if (playerTurn) {
                System.out.println("Ваш ход. Введите координату для атаки (например, a1): ");
                String coordinate = getValidCoordinate(scanner);
                if (playerShots.contains(coordinate)) {
                    System.out.println("Вы уже стреляли в эту клетку. Попробуйте другую.");
                    continue; // Повторяем ход, если координаты уже атакованы
                }
                playerShots.add(coordinate);
                int x = coordinate.charAt(0) - 'a';
                int y = Character.getNumericValue(coordinate.charAt(1)) - 1;

                boolean hit = enemyMap.checkOnMap(x, y);
                if (hit) {
                    System.out.println("Попадание!");
                } else {
                    System.out.println("Мимо!");
                    playerTurn = false;
                }
                enemyMap.printMap(true);
            } else {
                System.out.println("Ход противника!");
                int x, y;
                String enemyCoordinate;
                do {
                    x = (int) (Math.random() * 10);
                    y = (int) (Math.random() * 10);
                    enemyCoordinate = (char) ('a' + x) + String.valueOf(y + 1);
                } while (enemyShots.contains(enemyCoordinate)); // Проверяем, не стрелял ли противник сюда раньше

                enemyShots.add(enemyCoordinate);
                boolean hit = playerMap.checkOnMap(x, y);
                if (hit) {
                    System.out.println("Противник попал!");
                } else {
                    System.out.println("Противник промахнулся!");
                    playerTurn = true; // Передаем ход игроку, если противник промахнулся
                }
                playerMap.printMap(false);
            }
        }
        System.out.println("Игра окончена!");
    }


    // Метод для получения корректных координат
    private String getValidCoordinate(Scanner scanner) {
        String coordinate;
        while (true) {
            coordinate = scanner.nextLine().toLowerCase();
            if (validateCoordinate(coordinate)) {
                break;
            } else {
                System.out.println("Неверные координаты. Попробуйте снова (например, a1): ");
            }
        }
        return coordinate;
    }

    // Валидация координат
    private boolean validateCoordinate(String coordinate) {
        if (coordinate.length() != 2) {
            return false;
        }

        char letter = coordinate.charAt(0);
        char number = coordinate.charAt(1);

        if (letter < 'a' || letter > 'j') {
            return false;
        }

        if (number < '1' || number > '9') {
            return false;
        }

        return true;
    }

    // Логика проверки конца игры (например, все корабли одной из сторон уничтожены)
    private boolean isGameOver() {
        return enemyMap.isAllShipsSunk() || playerMap.isAllShipsSunk();
    }
}
