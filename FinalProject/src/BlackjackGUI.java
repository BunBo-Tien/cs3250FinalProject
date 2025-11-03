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

    private Stage primaryStage;
    private Scene gameScene;
    private Game game;

    // Game Scene Elements
    private HBox dealerCardsBox;
    private HBox playerCardsBox;
    private Label dealerScoreLabel; 
    private Label playerScoreLabel;
    private Label statusLabel;
    private Button hitButton;
    private Button standButton;
    private Label playerWinsLabel;
    private Label dealerWinsLabel;

    private final int CARD_WIDTH = 80; 

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        game = new Game();

        primaryStage.setTitle("Blackjack");
        primaryStage.setResizable(false);

        // 1. Build the main game scene first
        buildGameScene();

        // 2. Create the WelcomeView, telling it what to do when "Play" is clicked
        WelcomeView welcomeView = new WelcomeView(playerName -> {
            game.getPlayer().setName(playerName);
            primaryStage.setScene(gameScene);
            startNewGame();
        });

        // 3. Start with the welcome scene
        primaryStage.setScene(welcomeView.getScene());
        primaryStage.show();
    }
    
    /**
     * Builds the main game screen UI.
     */
    private void buildGameScene() {
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setStyle("-fx-background-color: #35654d;");

        // Top Section: Title and Score
        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Blackjack");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        HBox scoreBox = new HBox(50);
        scoreBox.setAlignment(Pos.CENTER);
        playerWinsLabel = new Label("Player Wins: 0");
        dealerWinsLabel = new Label("Dealer Wins: 0");
        playerWinsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        dealerWinsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        scoreBox.getChildren().addAll(playerWinsLabel, dealerWinsLabel);
        
        topSection.getChildren().addAll(titleLabel, scoreBox);
        rootLayout.setTop(topSection);
        BorderPane.setMargin(topSection, new Insets(0, 0, 20, 0));

        // Center Section: Game area
        VBox gameArea = new VBox(20);
        gameArea.setAlignment(Pos.CENTER);

        // Dealer's area
        VBox dealerArea = new VBox(10);
        dealerArea.setAlignment(Pos.CENTER);
        dealerScoreLabel = new Label("Dealer's Cards:");
        dealerScoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        dealerCardsBox = new HBox(-CARD_WIDTH / 2.5);
        dealerCardsBox.setAlignment(Pos.CENTER);
        dealerArea.getChildren().addAll(dealerScoreLabel, dealerCardsBox);

        // Player's area
        VBox playerArea = new VBox(10);
        playerArea.setAlignment(Pos.CENTER);
        playerScoreLabel = new Label("Player's Cards:");
        playerScoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        playerCardsBox = new HBox(-CARD_WIDTH / 2.5);
        playerCardsBox.setAlignment(Pos.CENTER);
        playerArea.getChildren().addAll(playerScoreLabel, playerCardsBox);

        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: #ffeb3b;");
        gameArea.getChildren().addAll(dealerArea, playerArea, statusLabel);
        rootLayout.setCenter(gameArea);

        // Bottom Section: Control buttons
        HBox buttonPane = new HBox(15);
        buttonPane.setAlignment(Pos.CENTER);

        hitButton = new Button("Hit");
        hitButton.setOnAction(event -> handleHitAction());

        standButton = new Button("Stand");
        standButton.setOnAction(event -> handleStandAction());

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> startNewGame());
        
        for (Button btn : new Button[]{hitButton, standButton, newGameButton}) {
            btn.setStyle("-fx-background-color: #c9a43a; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        }

        buttonPane.getChildren().addAll(hitButton, standButton, newGameButton);
        rootLayout.setBottom(buttonPane);
        BorderPane.setMargin(buttonPane, new Insets(20, 0, 0, 0));

        gameScene = new Scene(rootLayout, 700, 550);
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
    
    private void updateView() {
        playerCardsBox.getChildren().clear();
        dealerCardsBox.getChildren().clear();
        
        for (Card card : game.getPlayer().getHand().getCards()) {
            playerCardsBox.getChildren().add(createCardImageView(card.getImagePath()));
        }
        playerScoreLabel.setText(String.format("%s's Cards (%d)", game.getPlayer().getName(), game.getPlayer().getHand().calculateScore()));

        if (game.isGameOver()) {
            for (Card card : game.getDealer().getHand().getCards()) {
                dealerCardsBox.getChildren().add(createCardImageView(card.getImagePath()));
            }
            dealerScoreLabel.setText(String.format("Dealer's Cards (%d)", game.getDealer().getHand().calculateScore()));
        } else {
            dealerCardsBox.getChildren().add(createCardImageView("/cards/BACK.png"));
            if (game.getDealer().getHand().getCards().size() > 1) {
                Card visibleCard = game.getDealer().getHand().getCards().get(1);
                dealerCardsBox.getChildren().add(createCardImageView(visibleCard.getImagePath()));
            }
            dealerScoreLabel.setText("Dealer's Cards");
        }
       statusLabel.setText(game.getGameResult());

       // Update win counts
       playerWinsLabel.setText(game.getPlayer().getName() + " Wins: " + game.getPlayerWins());
       dealerWinsLabel.setText("Dealer Wins: " + game.getDealerWins());
    }

    private ImageView createCardImageView(String imagePath) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(CARD_WIDTH);
        imgView.setPreserveRatio(true);
        return imgView;
    }
}









