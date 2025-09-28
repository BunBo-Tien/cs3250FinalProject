/*
 * Game - Player / Dealer / Deck  Association: game dieu phoi cac Player, Dealer, va Deck
 * Game biet ve cac doi tuong nay, nhung chung co the ton tai doc lap ngoai game (VD: ban tao Deck truoc khi tao game)
 */
public class Game {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private boolean isGameOver;
    private String gameResult; // Lưu trữ chuỗi kết quả để hiển thị trên GUI

    public Game() {
        this.deck = new Deck();
        this.player = new Player("Player");
        this.dealer = new Dealer("Dealer");
    }

    
    	//Bắt đầu một ván chơi mới. Được gọi bởi nút "Ván Mới" trên GUI.
        public void startNewGame() {
        isGameOver = false;
        
        
        deck.shuffle();
        player.getHand().clear();
        dealer.getHand().clear();

        //Chia hai lá bài ban đầu cho mỗi người
        player.hit(deck);
        dealer.hit(deck);
        player.hit(deck);
        dealer.hit(deck);

        //Kiểm tra trường hợp người chơi có Blackjack ngay từ đầu
        if (player.getHand().calculateScore() == 21) {
            //Nếu người chơi có Blackjack, lượt chơi kết thúc ngay lập tức
            isGameOver = true;
            determineWinner();
        }
    }

    
        //Xử lý khi người chơi rút thêm bài. Được gọi bởi nút "Hit" trên GUI.
        public void playerHits() {
        if (isGameOver) {
            return; //Không làm gì nếu ván chơi đã kết thúc
        }
        
        player.hit(deck);
        
        //Nếu người chơi bị bù (quá 21 điểm), ván chơi kết thúc
        if (player.getHand().calculateScore() > 21) {
            isGameOver = true;
            determineWinner();
        }
    }

    
        // Xử lý khi người chơi dừng lượt. Được gọi bởi nút "Stand" trên GUI.
        public void playerStands() {
        if (isGameOver) {
            return; //Không làm gì nếu ván chơi đã kết thúc
        }
        
        //Đến lượt của nhà cái
        dealerTurn();
        //Xác định người thắng cuộc và kết thúc ván chơi
        isGameOver = true;
        determineWinner();
    }

    
        //Logic tự động chơi của nhà cái.
        private void dealerTurn() {
        //Nhà cái sẽ rút bài cho đến khi có 17 điểm trở lên
        	dealer.play(deck);
    }
    
    
        //Xác định người thắng và đặt thông báo kết quả.
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

    //Các phương thức Getter để GUI có thể truy cập và hiển thị thông tin

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
