package game;

import java.util.Scanner;

import player.Player;
import farm.Field;
import crops.Crop;
import crops.Carrot;
import crops.Potato;
import crops.Strawberry;

public class Game {

    private Player player;
    private Scanner scanner;
    private int turn;

    public Game() {
        player = new Player(50);
        scanner = new Scanner(System.in);
        turn = 1;
    }

    public void start() {

        System.out.println("====================");
        System.out.println("   FARMING GAME");
        System.out.println("====================");

        while (true) {

            displayFarm();

            boolean successfulAction = playTurn();

            if (successfulAction) {
                player.growAllCrops();
                turn++;
            }
        }
    }

    private boolean playTurn() {

    System.out.println("\nWhat do you want to do?");
    System.out.println("1. Plant crop");
    System.out.println("2. Harvest crop");
    System.out.println("3. Wait");
    System.out.println("4. Quit");

    int choice = scanner.nextInt();

    switch (choice) {

        case 1:
            return plantCrop();

        case 2:
            return harvestCrop();

        case 3:
            System.out.println("You wait for one turn...");
            return true;

        case 4:
            System.out.println("Thanks for playing!");
            System.exit(0);
            return false;

        default:
            System.out.println("Invalid choice.");
            return false;
        }
    }   

    private boolean plantCrop() {

        System.out.println("\nChoose crop:");
        System.out.println("1. Carrot     ($5)");
        System.out.println("2. Potato     ($10)");
        System.out.println("3. Strawberry ($20)");

        int choice = scanner.nextInt();

        Crop crop;

        switch (choice) {

            case 1:
                crop = new Carrot();
                break;

            case 2:
                crop = new Potato();
                break;

            case 3:
                crop = new Strawberry();
                break;

            default:
                System.out.println("Invalid crop.");
                return false;
        }

        System.out.print("Choose field (1-9): ");

        int fieldNumber = scanner.nextInt() - 1;

        if (fieldNumber < 0 || fieldNumber >= 9) {
            System.out.println("Invalid field.");
            return false;
        }

        return player.plantCrop(fieldNumber, crop);
    }

    private boolean harvestCrop() {

        System.out.print("Choose field (1-9): ");

        int fieldNumber = scanner.nextInt() - 1;

        if (fieldNumber < 0 || fieldNumber >= 9) {
            System.out.println("Invalid field.");
            return false;
        }

        return player.harvestCrop(fieldNumber);
    }

    private void displayFarm() {

        System.out.println("\n--------------------");
        System.out.println("TURN " + turn);
        System.out.println("Money: $" + player.getMoney());
        System.out.println("--------------------");

        Field[] fields = player.getFields();

        for (int i = 0; i < fields.length; i++) {

            Field field = fields[i];

            System.out.print("Field " + (i + 1) + ": ");

            if (field.isEmpty()) {
                System.out.println("[EMPTY]");
            } else {

                Crop crop = field.getCrop();

                System.out.print(crop.getName());

                if (crop.isRotten()) {
                    System.out.println(" [ROTTEN]");
                } else if (crop.isMature()) {
                    System.out.println(" [READY]");
                } else {
                    System.out.println(
                        " [Growing "
                        + crop.getAge()
                        + "/"
                        + crop.getMatureAge()
                        + "]"
                    );
                }
            }
        }
    }
}