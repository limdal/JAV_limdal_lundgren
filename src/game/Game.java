package game;

import java.util.Scanner;

import crops.Carrot;
import crops.Crop;
import crops.Potato;
import crops.Strawberry;
import farm.CannotHarvestException;
import farm.Compost;
import farm.Field;
import farm.FieldOccupiedException;
import market.Sellable;
import player.NotEnoughMoneyException;
import player.Player;

/**
 * Runs the farming game in text mode: shows the farm, reads the player's
 * choices from the keyboard and decides when the game is won or lost.
 */
public class Game {

    private static final int STARTING_MONEY = 50;
    private static final int GOAL_MONEY = 300;
    private static final int MAX_TURNS = 30;

    /** Number of different crops the player can buy. */
    private static final int CROP_TYPE_COUNT = 3;

    /** Value returned by sub-menus when the player chooses to go back. */
    private static final int CANCEL = 0;

    private Player player;
    private Scanner scanner;
    private int turn;
    private boolean running;

    /**
     * Prepares a new game: starting money, empty fields and turn 1.
     */
    public Game() {
        player = new Player(STARTING_MONEY);
        scanner = new Scanner(System.in);
        turn = 1;
        running = true;
    }

    /**
     * Plays the game until it is won, lost or the player quits.
     * Crops only grow when the player's action used up a turn.
     */
    public void start() {
        printWelcome();

        while (running) {
            displayFarm();

            boolean turnUsed = playTurn();

            if (turnUsed) {
                player.growAllCrops();
                turn++;
            }

            checkEndOfGame();
        }

        System.out.println("Thanks for playing!");
    }

    private void printWelcome() {
        System.out.println("====================");
        System.out.println("   FARMING GAME");
        System.out.println("====================");
        System.out.println("Earn $" + GOAL_MONEY + " within " + MAX_TURNS
            + " turns to win.");
        System.out.println("Harvested crops go to your barn: sell them to get"
            + " the money.");
        System.out.println("Planting, harvesting, selling and waiting each take"
            + " one turn.");
    }

    /**
     * Shows the main menu and carries out the chosen action.
     *
     * @return true if the action used up a turn, false otherwise
     */
    private boolean playTurn() {
        System.out.println("\nWhat do you want to do?");
        System.out.println("1. Plant crop");
        System.out.println("2. Harvest crop");
        System.out.println("3. Sell everything in the barn");
        System.out.println("4. Wait");
        System.out.println("5. Quit");

        int choice = readInt("Your choice: ", 1, 5);

        if (!running) {
            return false;
        }

        switch (choice) {
            case 1:
                return plantCrop();
            case 2:
                return harvestCrop();
            case 3:
                return sellBarnContents();
            case 4:
                System.out.println("You wait for one turn...");
                return true;
            default:
                running = false;
                return false;
        }
    }

    /**
     * Lets the player buy a crop and choose a field to plant it in.
     *
     * @return true if a crop was planted, which uses up the turn
     */
    private boolean plantCrop() {
        System.out.println("\nChoose crop:");

        for (int i = 1; i <= CROP_TYPE_COUNT; i++) {
            Crop crop = createCrop(i);
            System.out.println(i + ". " + crop.getName()
                + ": costs $" + crop.getPlantingCost()
                + ", sells for $" + crop.getBasePrice()
                + ", ready after " + crop.getMatureAge() + " turns"
                + ", rots after " + crop.getRottenAge() + " turns");
            System.out.println("   (" + crop.getDescription() + ")");
        }
        System.out.println(CANCEL + ". Back");

        int choice = readInt("Your choice: ", CANCEL, CROP_TYPE_COUNT);

        if (choice == CANCEL) {
            return false;
        }

        int fieldNumber = chooseField();

        if (fieldNumber == CANCEL) {
            return false;
        }

        Crop crop = createCrop(choice);

        try {
            player.plantCrop(fieldNumber - 1, crop);
            System.out.println("You planted a " + crop.getName()
                + " in field " + fieldNumber + ".");
            return true;
        } catch (NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        } catch (FieldOccupiedException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * Sells everything in the barn.
     *
     * @return true if something was sold, which uses up the turn
     */
    private boolean sellBarnContents() {
        if (player.isBarnEmpty()) {
            System.out.println("Your barn is empty, there is nothing to sell.");
            return false;
        }

        int earned = player.sellBarnContents();
        System.out.println("You sold everything at the market for $"
            + earned + ".");
        return true;
    }

    /**
     * Lets the player choose a field to harvest.
     *
     * @return true if something was harvested, which uses up the turn
     */
    private boolean harvestCrop() {
        int fieldNumber = chooseField();

        if (fieldNumber == CANCEL) {
            return false;
        }

        try {
            Sellable harvested = player.harvestCrop(fieldNumber - 1);

            if (harvested instanceof Compost) {
                System.out.println("The crop had rotted."
                    + " You put it on the compost heap.");
            } else {
                System.out.println("You harvested " + harvested.getName()
                    + " worth $" + harvested.getSellPrice()
                    + " and stored it in the barn.");
            }

            Field field = player.getField(fieldNumber - 1);

            if (!field.isEmpty()) {
                System.out.println("The " + field.getCrop().getName()
                    + " plant is growing back.");
            }

            return true;
        } catch (CannotHarvestException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Asks the player for a field number.
     *
     * @return a field number between 1 and the number of fields,
     *         or CANCEL if the player wants to go back
     */
    private int chooseField() {
        return readInt("Choose field (1-" + Player.FIELD_COUNT + ", "
            + CANCEL + " to go back): ", CANCEL, Player.FIELD_COUNT);
    }

    /**
     * Creates a new crop from its number in the planting menu.
     *
     * @param choice the menu number, between 1 and CROP_TYPE_COUNT
     * @return a freshly planted crop of the chosen type
     */
    private static Crop createCrop(int choice) {
        switch (choice) {
            case 1:
                return new Carrot();
            case 2:
                return new Potato();
            case 3:
                return new Strawberry();
            default:
                throw new IllegalArgumentException("Unknown crop: " + choice);
        }
    }

    /**
     * Finds the lowest planting cost among all crops, so that the price
     * list lives in the crop classes only.
     */
    private static int getCheapestCropCost() {
        int cheapest = createCrop(1).getPlantingCost();

        for (int i = 2; i <= CROP_TYPE_COUNT; i++) {
            cheapest = Math.min(cheapest, createCrop(i).getPlantingCost());
        }

        return cheapest;
    }

    /**
     * Reads a whole number between min and max (both included) from the
     * keyboard, asking again until the answer is valid. If the input is
     * closed, the game is stopped and CANCEL is returned.
     */
    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);

            if (!scanner.hasNextLine()) {
                System.out.println();
                running = false;
                return CANCEL;
            }

            String line = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(line);

                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // Not a number: handled by the message below.
            }

            System.out.println("Please enter a number between " + min
                + " and " + max + ".");
        }
    }

    /**
     * Stops the game if the goal is reached, the turns have run out, or
     * the player can no longer earn any money.
     */
    private void checkEndOfGame() {
        if (!running) {
            return;
        }

        int moneyAvailable = player.getMoney() + player.getBarnValue();

        if (player.getMoney() >= GOAL_MONEY) {
            System.out.println("\nYou earned $" + player.getMoney()
                + " and reached the goal. You win!");
            running = false;
        } else if (turn > MAX_TURNS) {
            System.out.println("\nThe season is over and you only have $"
                + player.getMoney() + ". You lose!");
            running = false;
        } else if (moneyAvailable < getCheapestCropCost()
                && !player.hasCropInFields()) {
            System.out.println("\nYou can't afford any seeds and have no crops"
                + " left to sell. You lose!");
            running = false;
        }
    }

    private void displayFarm() {
        System.out.println("\n--------------------");
        System.out.println("TURN " + turn + "/" + MAX_TURNS);
        System.out.println("Money: $" + player.getMoney()
            + " (goal: $" + GOAL_MONEY + ")");
        System.out.println("--------------------");

        for (int i = 0; i < Player.FIELD_COUNT; i++) {
            Field field = player.getField(i);

            System.out.print("Field " + (i + 1) + ": ");

            if (field.isEmpty()) {
                System.out.println("[EMPTY]");
            } else {
                System.out.println(field.getCrop());
            }
        }

        System.out.println("Barn: " + player.describeBarn());
    }
}
