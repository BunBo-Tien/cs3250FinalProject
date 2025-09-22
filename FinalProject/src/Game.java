/*
 * Game - Player / Dealer / Deck  Association: game dieu phoi cac Player, Dealer, va Deck
 * Game biet ve cac doi tuong nay, nhung chung co the ton tai doc lap ngoai game (VD: ban tao Deck truoc khi tao game)
 */

import java.util.Scanner;

public class Game {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private Scanner scanner;

    public Game() {
        this.deck = new Deck(); //Giả sử đã có lớp Deck
        this.player = new Player("Người chơi");
        this.dealer = new Dealer("Nhà cái");
        this.scanner = new Scanner(System.in);
    }
    
    public void playGame() {
        System.out.println("Xin chào! Bắt đầu chơi Blackjack!");
        
        //Bước 1: Chuẩn bị và chia bài
        deck.shuffle();
        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
        
        //Hiển thị bài ban đầu
        player.showHand(false);
        dealer.showHand(true); // Ẩn lá bài đầu tiên của Dealer
        
        //Bước 2: Lượt chơi của người chơi
        System.out.println("---------------------------------");
        playerTurn();

        //Nếu người chơi không bị bù, đến lượt của Dealer
        if (player.getHand().calculateScore() <= 21) {
            dealerTurn();
            determineWinner();
        } else {
            determineWinner();
        }
        
        System.out.println("---------------------------------");
        System.out.println("Kết thúc ván chơi.");
    }
    
    private void playerTurn() {
        String choice;
        do {
            System.out.print("Bạn muốn Hit hay Stand? (h/s): ");
            choice = scanner.nextLine().toLowerCase();
            if (choice.equals("h")) {
                player.hit(deck);
                player.showHand(false);
                if (player.getHand().calculateScore() > 21) {
                    System.out.println("Bạn đã bị BÙ! Tổng điểm: " + player.getHand().calculateScore());
                    break;
                }
            }
        } while (choice.equals("h"));
    }

    private void dealerTurn() {
        System.out.println("\nĐến lượt của Nhà cái.");
        dealer.showHand(false); // Mở lá bài úp
        dealer.play(deck); // Dealer tự động chơi theo luật
        dealer.showHand(false);
    }
    
    private void determineWinner() {
        int playerScore = player.getHand().calculateScore();
        int dealerScore = dealer.getHand().calculateScore();

        System.out.println("\n--- Kết quả cuối cùng ---");
        System.out.println("Điểm của bạn: " + playerScore);
        System.out.println("Điểm của Nhà cái: " + dealerScore);

        if (playerScore > 21) {
            System.out.println("Bạn bị BÙ! Nhà cái thắng.");
        } else if (dealerScore > 21) {
            System.out.println("Nhà cái bị BÙ! Bạn thắng.");
        } else if (playerScore > dealerScore) {
            System.out.println("Bạn thắng!");
        } else if (playerScore < dealerScore) {
            System.out.println("Bạn thua. Nhà cái thắng.");
        } else {
            System.out.println("Hòa! Cả hai cùng có điểm số bằng nhau.");
        }
    }
}
