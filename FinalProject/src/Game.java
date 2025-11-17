/*
 * Game - Player / Dealer / Deck  Association: game dieu phoi cac Player, Dealer, va Deck
 * Game biet ve cac doi tuong nay, nhung chung co the ton tai doc lap ngoai game (VD: ban tao Deck truoc khi tao game)
 */
public class Game {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private boolean isGameOver;
    private String gameResult;

    // Scorekeeping variables
    private int playerWins = 0;
    private int dealerWins = 0;

    public Game() {
        this.deck = new Deck();
        // The player is now created with a default name, which will be updated from the GUI.
        this.player = new Player("Player");
        this.dealer = new Dealer("Dealer");
    }

    public void startNewGame() {
        isGameOver = false;
        gameResult = "Your turn. Hit or Stand?";
        
        // The deck will auto-reshuffle when needed, so no need to call shuffle here.
        
        player.getHand().clear();
        dealer.getHand().clear();

        player.hit(deck);
        dealer.hit(deck);
        player.hit(deck);
        dealer.hit(deck);

        if (player.getHand().calculateScore() == 21) {
            isGameOver = true;
            determineWinner();
        }
    }

    public void playerHits() {
        if (isGameOver) {
            return;
        }
        player.hit(deck);
        if (player.getHand().calculateScore() > 21) {
            isGameOver = true;
            determineWinner();
        }
    }

    public void playerStands() {
        if (isGameOver) {
            return;
        }
        dealerTurn();
        isGameOver = true;
        determineWinner();
    }

    private void dealerTurn() {
        dealer.play(deck);
    }
    
    private void determineWinner() {
        int playerScore = player.getHand().calculateScore();
        int dealerScore = dealer.getHand().calculateScore();

        if (playerScore > 21) {
            gameResult = "Player BUST! Dealer wins.";
            dealerWins++;
        } else if (dealerScore > 21) {
            gameResult = "Dealer BUST! Player wins.";
            playerWins++;
        } else if (playerScore > dealerScore) {
            gameResult = "Player wins!";
            playerWins++;
        } else if (playerScore < dealerScore) {
            gameResult = "Player loses. Dealer wins.";
            dealerWins++;
        } else {
            gameResult = "Draw!";
        }
    }

    // --- Getters for GUI ---

    public Player getPlayer() { return player; }
    public Dealer getDealer() { return dealer; }
    public boolean isGameOver() { return isGameOver; }
    public String getGameResult() { return gameResult; }
    public int getPlayerWins() { return playerWins; }
    public int getDealerWins() { return dealerWins; }
}


