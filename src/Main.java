import game.Game;

/**
 * Entry point of the farming game.
 */
public class Main {

    /**
     * Starts a new game in the terminal.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
