import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class BlackjackGUI extends Application {

    private Game game;

    //GUI elements we'll need to update.
    //Khai báo các thành phần GUI cần được cập nhật
    //Note: We're using HBox to hold card images instead of a simple Label.
    private HBox dealerCardsBox;
    private HBox playerCardsBox;
    private Label dealerScoreLabel; 
    private Label playerScoreLabel;
    private Label statusLabel;
    private Button hitButton;
    private Button standButton;

    private final int CARD_WIDTH = 80; 

    @Override
    public void start(Stage primaryStage) {
        game = new Game();

        primaryStage.setTitle("Trò chơi Blackjack");
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setStyle("-fx-background-color: #35654d;");

        //Top Section: Title
        Label titleLabel = new Label("Blackjack");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        rootLayout.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(0, 0, 20, 0));


        //Center Section: Game area
        VBox gameArea = new VBox(20);
        gameArea.setAlignment(Pos.CENTER);

        //Dealer's area
        VBox dealerArea = new VBox(10);
        dealerArea.setAlignment(Pos.CENTER);
        dealerScoreLabel = new Label("Bài của Nhà Cái:");
        dealerScoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        dealerCardsBox = new HBox(-CARD_WIDTH / 2.5); // Negative spacing for card overlap effect
        dealerCardsBox.setAlignment(Pos.CENTER);
        dealerArea.getChildren().addAll(dealerScoreLabel, dealerCardsBox);

        //Player's area
        VBox playerArea = new VBox(10);
        playerArea.setAlignment(Pos.CENTER);
        playerScoreLabel = new Label("Bài của Bạn:");
        playerScoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        playerCardsBox = new HBox(-CARD_WIDTH / 2.5); // Negative spacing
        playerCardsBox.setAlignment(Pos.CENTER);
        playerArea.getChildren().addAll(playerScoreLabel, playerCardsBox);

        //Status label
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: #ffeb3b;");

        gameArea.getChildren().addAll(dealerArea, playerArea, statusLabel);
        rootLayout.setCenter(gameArea);

        //Bottom Section: Control buttons
        HBox buttonPane = new HBox(15);
        buttonPane.setAlignment(Pos.CENTER);

        hitButton = new Button("Rút Bài (Hit)");
        hitButton.setOnAction(event -> handleHitAction());

        standButton = new Button("Dừng (Stand)");
        standButton.setOnAction(event -> handleStandAction());

        Button newGameButton = new Button("Ván Mới");
        newGameButton.setOnAction(event -> startNewGame());
        
        for (Button btn : new Button[]{hitButton, standButton, newGameButton}) {
            btn.setStyle("-fx-background-color: #c9a43a; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        }

        buttonPane.getChildren().addAll(hitButton, standButton, newGameButton);
        rootLayout.setBottom(buttonPane);
        BorderPane.setMargin(buttonPane, new Insets(20, 0, 0, 0));

        startNewGame();

        Scene scene = new Scene(rootLayout, 700, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void startNewGame() {
        game.startNewGame();
        hitButton.setDisable(false);
        standButton.setDisable(false);
        updateView();
    }

    private void handleHitAction() {
        game.playerHits();
        updateView();
        if (game.isGameOver()) endTurn();
    }
    
    private void handleStandAction() {
        game.playerStands();
        updateView();
        endTurn();
    }
    
    private void endTurn() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }
    
    //This method refreshes the entire UI, now with images.
    private void updateView() {
        // Clear previous cards
        playerCardsBox.getChildren().clear();
        dealerCardsBox.getChildren().clear();

        // Update player's cards and score
        for (Card card : game.getPlayer().getHand().getCards()) {
            ImageView cardImage = createCardImageView(card.getImagePath());
            playerCardsBox.getChildren().add(cardImage);
        }
        playerScoreLabel.setText(String.format("Bài của Bạn (%d)", game.getPlayer().getHand().calculateScore()));

        // Update dealer's cards and score
        if (game.isGameOver()) {
            // Show all dealer cards
            for (Card card : game.getDealer().getHand().getCards()) {
                ImageView cardImage = createCardImageView(card.getImagePath());
                dealerCardsBox.getChildren().add(cardImage);
            }
            dealerScoreLabel.setText(String.format("Bài của Nhà Cái (%d)", game.getDealer().getHand().calculateScore()));
        } else {
            // Show one card and one card back
            dealerCardsBox.getChildren().add(createCardImageView("/cards/BACK.png"));
            if (game.getDealer().getHand().getCards().size() > 1) {
                Card visibleCard = game.getDealer().getHand().getCards().get(1);
                dealerCardsBox.getChildren().add(createCardImageView(visibleCard.getImagePath()));
            }
            dealerScoreLabel.setText("Bài của Nhà Cái");
        }
        statusLabel.setText(game.getGameResult());
    }

    /**
     * Phương thức trợ giúp để tạo một ImageView cho một lá bài.
     * Nó tải hình ảnh từ thư mục resources và cấu hình kích thước cho ImageView.
     * Helper method to create an ImageView for a card.
     * It loads the image from the resources folder and configures the ImageView's size.
     * * @param imagePath Đường dẫn đến tệp hình ảnh trong thư mục resources (ví dụ: "/cards/A-S.png").
     * The path to the image file within the resources folder (e.g., "/cards/A-S.png").
     * @return Một đối tượng ImageView đã được cấu hình. A configured ImageView node.
     */
    private ImageView createCardImageView(String imagePath) {
        // Use getResource to ensure the image is loaded from the classpath (resources folder)
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(CARD_WIDTH);
        imgView.setPreserveRatio(true);
        return imgView;
    }
}





