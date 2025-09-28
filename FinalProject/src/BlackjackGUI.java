import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BlackjackGUI extends Application {

    private Game game;

    //Khai báo các thành phần GUI cần được cập nhật
    //GUI elements we'll need to update as the game progresses.
    private Label dealerHandLabel;
    private Label playerHandLabel;
    private Label statusLabel;
    private Button hitButton;
    private Button standButton;

    @Override
    public void start(Stage primaryStage) {
        //1. Khởi tạo đối tượng game từ lớp logic
        //1. Game logic object.
        game = new Game();

        //2. Thiết lập cửa sổ chính (Stage)
        //2. Set up the main application window (the Stage).
        primaryStage.setTitle("Blackjack");
        primaryStage.setResizable(false);

        //3. Tạo layout chính bằng BorderPane
        //3. Main layout using a BorderPane.
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setStyle("-fx-background-color: #35654d;"); // Màu nền xanh lá cây // A nice green background, like a card table.

        //Phần trên: Tiêu đề
        //Top Section: Title
        Label titleLabel = new Label("Blackjack");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        rootLayout.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        //Phần giữa: Hiển thị thông tin game (dùng VBox)
        //Center Section: Game info display (using a VBox)
        VBox gameInfoPane = new VBox(25);
        gameInfoPane.setAlignment(Pos.CENTER_LEFT);
        gameInfoPane.setPadding(new Insets(20, 0, 20, 0));

        //Label để hiển thị bài của nhà cái
        //Label to show the dealer's hand.
        dealerHandLabel = new Label();
        dealerHandLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        //Label để hiển thị bài của người chơi
        //Label to show the player's hand.
        playerHandLabel = new Label();
        playerHandLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        //Label để hiển thị trạng thái game
        //This label shows the game status, like 'You win!', 'Bust!', etc.
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: #ffeb3b;");

        gameInfoPane.getChildren().addAll(dealerHandLabel, playerHandLabel, statusLabel);
        rootLayout.setCenter(gameInfoPane);

        //Phần dưới: Các nút điều khiển (dùng HBox)
        //Bottom Section: Control buttons (using an HBox)
        HBox buttonPane = new HBox(15);
        buttonPane.setAlignment(Pos.CENTER);

        //Nút "Rút bài" (Hit)
        //The "Hit" button.
        hitButton = new Button("Hit");
        hitButton.setOnAction(event -> handleHitAction());

        //Nút "Dừng" (Stand)
        //The "Stand" button.
        standButton = new Button("Stand");
        standButton.setOnAction(event -> handleStandAction());

        //Nút "Ván mới"
        //The "New Game" button.
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> startNewGame());
        
        //Thiết kế cho các nút
        //A little styling for our buttons to make them look nice.
        for (Button btn : new Button[]{hitButton, standButton, newGameButton}) {
            btn.setStyle("-fx-background-color: #c9a43a; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        }

        buttonPane.getChildren().addAll(hitButton, standButton, newGameButton);
        rootLayout.setBottom(buttonPane);
        BorderPane.setMargin(buttonPane, new Insets(20, 0, 0, 0));

        //Bắt đầu ván chơi đầu tiên
        //New game start
        startNewGame();

        //4. Tạo Scene và hiển thị
        //4. Create the Scene and show the stage.
        Scene scene = new Scene(rootLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Bắt đầu một ván chơi mới.
     * Starts a new game.
     */
    private void startNewGame() {
        game.startNewGame();
        hitButton.setDisable(false);
        standButton.setDisable(false);
        updateView();
    }

    /**
     * Xử lý sự kiện khi người chơi nhấn nút "Hit".
     * Handles what happens when the player clicks the "Hit" button.
     */
    private void handleHitAction() {
        game.playerHits();
        updateView();
        
        if (game.isGameOver()) {
            endTurn();
        }
    }
    
    /**
     * Xử lý sự kiện khi người chơi nhấn nút "Stand".
     * Handles what happens when the player clicks the "Stand" button.
     */
    private void handleStandAction() {
        game.playerStands();
        updateView();
        endTurn();
    }
    
    /**
     * Kết thúc lượt chơi, vô hiệu hóa các nút Hit và Stand.
     * Ends the current turn by disabling the Hit and Stand buttons.
     */
    private void endTurn() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }
    
    /**
     * Cập nhật toàn bộ giao diện dựa trên trạng thái hiện tại của game.
     * This method refreshes the entire UI based on the current state of the game.
     */
    private void updateView() {
        playerHandLabel.setText(String.format("Player Cards (%d): %s",
                game.getPlayer().getHand().calculateScore(),
                game.getPlayer().getHand().toString()));

        if (game.isGameOver()) {
            //If the game is over, show the dealer's final hand and score.
            dealerHandLabel.setText(String.format("Dealer Cards (%d): %s",
                    game.getDealer().getHand().calculateScore(),
                    game.getDealer().getHand().toString()));
        } else {
            //Otherwise, keep the dealer's first card hidden.
            dealerHandLabel.setText("Dealer Cards: " + game.getDealer().getHand().toStringConcealed());
        }

        statusLabel.setText(game.getGameResult());
    }
}





