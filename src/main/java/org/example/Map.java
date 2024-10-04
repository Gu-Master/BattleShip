package org.example;

import java.util.Arrays;
public class Map {
    private char[][] mapChar = new char[10][10];
    private int[][] mapInt = new int[10][10]; // 0 - вода, 1 - корабль, -1 - попадание, -2 - промах

    public void createMap() {
        for (int i = 0; i < 10; i++) {
            Arrays.fill(mapInt[i], 0); // Заполняем карту водой
        }
        placeShipsRandomly();
    }

    private void placeShipsRandomly() {
        int[] ships = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // Размеры кораблей

        for (int shipSize : ships) {
            boolean placed = false;
            while (!placed) {
                int x = (int) (Math.random() * 10);
                int y = (int) (Math.random() * 10);
                boolean horizontal = Math.random() < 0.5;
                if (canPlaceShip(x, y, shipSize, horizontal)) {
                    placeShip(x, y, shipSize, horizontal);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int x, int y, int size, boolean horizontal) {
        if (horizontal) {
            if (x + size > 10) return false;
            for (int i = -1; i <= size; i++) {
                for (int j = -1; j <= 1; j++) {
                    int checkX = x + i;
                    int checkY = y + j;
                    if (isInBounds(checkX, checkY) && mapInt[checkY][checkX] != 0) {
                        return false;
                    }
                }
            }
        } else {
            if (y + size > 10) return false;
            for (int i = -1; i <= size; i++) {
                for (int j = -1; j <= 1; j++) {
                    int checkX = x + j;
                    int checkY = y + i;
                    if (isInBounds(checkX, checkY) && mapInt[checkY][checkX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Метод для проверки, находятся ли координаты в пределах карты
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }


    private void placeShip(int x, int y, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                mapInt[y][x + i] = 1;
            } else {
                mapInt[y + i][x] = 1;
            }
        }
    }

    public void printMap(boolean hideShips) {
        System.out.println("  A B C D E F G H I J");
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 10; j++) {
                if (mapInt[i][j] == 1 && hideShips) {
                    System.out.print(". "); // Если скрываем корабли
                } else if (mapInt[i][j] == 1) {
                    System.out.print("S "); // Корабль
                } else if (mapInt[i][j] == -1) {
                    System.out.print("X "); // Попадание
                } else if (mapInt[i][j] == -2) {
                    System.out.print("O "); // Промах
                } else {
                    System.out.print(". "); // Вода
                }
            }
            System.out.println();
        }
    }

    public boolean checkOnMap(int x, int y) {
        if (mapInt[y][x] == 1) {
            mapInt[y][x] = -1;
            return true;
        } else if (mapInt[y][x] == 0) {
            mapInt[y][x] = -2; // Помечаем промах
            return false;
        } else {
            System.out.println("Вы уже стреляли сюда. Попробуйте другие координаты.");
            return false; // Игрок стреляет в уже проверенную клетку
        }
    }

    public boolean isAllShipsSunk() {
        for (int[] row : mapInt) {
            for (int cell : row) {
                if (cell == 1) { // Если есть хотя бы один корабль
                    return false;
                }
            }
        }
        return true; // Все корабли уничтожены
    }


}
