/*
 * Game - Player / Dealer / Deck  Association: game dieu phoi cac Player, Dealer, va Deck
 * Game biet ve cac doi tuong nay, nhung chung co the ton tai doc lap ngoai game (VD: ban tao Deck truoc khi tao game)
 */
public class Game {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private boolean isGameOver;
    private String gameResult; //Save the score to display on the GUI 

    public Game() {
        this.deck = new Deck();
        this.player = new Player("Player");
        this.dealer = new Dealer("Dealer");
    }

    
    	//New Game
        public void startNewGame() {
        isGameOver = false;
        
        
        deck.shuffle();
        player.getHand().clear();
        dealer.getHand().clear();

        //2 card got deal for player and dealer
        player.hit(deck);
        dealer.hit(deck);
        player.hit(deck);
        dealer.hit(deck);

        //check to see if player got 21 at the first deal
        if (player.getHand().calculateScore() == 21) {
            //if player got black jack the game end right away
            isGameOver = true;
            determineWinner();
        }
    }

    
        //player hit 
        public void playerHits() {
        if (isGameOver) {
            return;
        }
        
        player.hit(deck);
        
        //if player got over 21 game end
        if (player.getHand().calculateScore() > 21) {
            isGameOver = true;
            determineWinner();
        }
    }

    
        //player stand
        public void playerStands() {
        if (isGameOver) {
            return;
        }
        
        //dealer turn
        dealerTurn();
        isGameOver = true;
        determineWinner();
    }

    
        //Logic for dealer
        private void dealerTurn() {
        //Dealer have to keep draw card until it got 17 or above
        	dealer.play(deck);
    }
    
    
        //game result
        private void determineWinner() {
        int playerScore = player.getHand().calculateScore();
        int dealerScore = dealer.getHand().calculateScore();

        if (playerScore > 21) {
            gameResult = "Player BUST! Dealer win.";
        } else if (dealerScore > 21) {
            gameResult = "Dealer BUST! Player win.";
        } else if (playerScore > dealerScore) {
            gameResult = "Player win!";
        } else if (playerScore < dealerScore) {
            gameResult = "PLayer lose. Dealer win.";
        } else {
            gameResult = "Draw! same point.";
        }
    }

    //Getters for GUI can access and display

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public String getGameResult() {
        return gameResult;
    }
}
