/*
 * This is the demo of the black jack game. Some of the comment and console output is written in
 * vietnamese language for me to better understanding what's going on. 
 * 
 * This is the main class that use to execute the code.
 */

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//System.out.println("Welcome to my final project");
		Scanner scanner = new Scanner(System.in);
        String playAgain;

        do {
            System.out.println("---------------------------------");
            System.out.println("Bắt đầu ván Blackjack mới!");
            Game game = new Game();
            game.playGame();
            System.out.println("---------------------------------");
            System.out.print("Bạn có muốn chơi tiếp không? (y/n): ");
            playAgain = scanner.nextLine().toLowerCase();
        } while (playAgain.equals("y"));

        System.out.println("Cảm ơn bạn đã chơi! Hẹn gặp lại.");
        scanner.close();
	}

}
