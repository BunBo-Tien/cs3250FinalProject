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
import javafx.scene.layout.StackPane; 
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip; 
import javafx.scene.media.Media;     
import javafx.scene.media.MediaPlayer; 
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
    private Button newGameButton;
    private Label playerWinsLabel;
    private Label dealerWinsLabel;
    private MediaPlayer backgroundMusicPlayer;

    // Design resolution
    private final double DESIGN_WIDTH = 800;
    private final double DESIGN_HEIGHT = 600;
    private final int CARD_WIDTH = 100; 

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        game = new Game();

        primaryStage.setTitle("Blackjack");
        primaryStage.setResizable(true);
        
     // Make icon for the game window.
        try {
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cards/Icon-card.png")));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Không thể tải icon cho cửa sổ: " + e.getMessage());
        }

        // This is the min window that user can resize down.
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);

        // 1. Build the game scene with scaling
        buildGameScene();

        // 2. Welcome scene create
        WelcomeView welcomeView = new WelcomeView(playerName -> {
            game.getPlayer().setName(playerName);
            primaryStage.setScene(gameScene);
            playBackgroundMusic();
            
            startNewGame();
        });

        // 3. Start with Welcome scene
        primaryStage.setScene(welcomeView.getScene());
        primaryStage.show();
    }
    
    /**
     * Builds the main game screen UI with Responsive Scaling.
     */
    private void buildGameScene() {
        // DESIGN_WIDTH x DESIGN_HEIGHT
        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new Insets(20));
        contentPane.setStyle("-fx-background-color: #35654d;");
        
        //Set the fix for Content Pane
        contentPane.setPrefSize(DESIGN_WIDTH, DESIGN_HEIGHT);
        contentPane.setMaxSize(DESIGN_WIDTH, DESIGN_HEIGHT);

        // Build the element inside
        
        // Top Section: Title and Score
        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Blackjack");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        HBox scoreBox = new HBox(50);
        scoreBox.setAlignment(Pos.CENTER);
        playerWinsLabel = new Label("Player Wins: 0");
        dealerWinsLabel = new Label("Dealer Wins: 0");
        playerWinsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        dealerWinsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        scoreBox.getChildren().addAll(playerWinsLabel, dealerWinsLabel);
        
        topSection.getChildren().addAll(titleLabel, scoreBox);
        contentPane.setTop(topSection);
        BorderPane.setMargin(topSection, new Insets(0, 0, 20, 0));

        // Center Section: Game area
        VBox gameArea = new VBox(20);
        gameArea.setAlignment(Pos.CENTER);

        // Dealer's area
        VBox dealerArea = new VBox(10);
        dealerArea.setAlignment(Pos.CENTER);
        dealerScoreLabel = new Label("Dealer's Cards:");
        dealerScoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        dealerCardsBox = new HBox(-CARD_WIDTH / 2.5);
        dealerCardsBox.setAlignment(Pos.CENTER);
        dealerArea.getChildren().addAll(dealerScoreLabel, dealerCardsBox);

        // Player's area
        VBox playerArea = new VBox(10);
        playerArea.setAlignment(Pos.CENTER);
        playerScoreLabel = new Label("Player's Cards:");
        playerScoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        playerCardsBox = new HBox(-CARD_WIDTH / 2.5);
        playerCardsBox.setAlignment(Pos.CENTER);
        playerArea.getChildren().addAll(playerScoreLabel, playerCardsBox);

        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 22px; -fx-font-style: italic; -fx-text-fill: #ffeb3b;");
        gameArea.getChildren().addAll(dealerArea, playerArea, statusLabel);
        contentPane.setCenter(gameArea);

        // Bottom Section: Control buttons
        HBox buttonPane = new HBox(20);
        buttonPane.setAlignment(Pos.CENTER);

        hitButton = new Button("Hit");
        hitButton.setOnAction(event -> handleHitAction());

        standButton = new Button("Stand");
        standButton.setOnAction(event -> handleStandAction());

        newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> startNewGame());
        
        String buttonStyle = "-fx-background-color: #c9a43a; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 8;";
        hitButton.setStyle(buttonStyle);
        standButton.setStyle(buttonStyle);
        newGameButton.setStyle(buttonStyle);

        buttonPane.getChildren().addAll(hitButton, standButton, newGameButton);
        contentPane.setBottom(buttonPane);
        BorderPane.setMargin(buttonPane, new Insets(20, 0, 0, 0));

        StackPane root = new StackPane(contentPane);
        root.setStyle("-fx-background-color: #2b503d;");

        root.widthProperty().addListener((obs, oldVal, newVal) -> updateScale(contentPane, root));
        root.heightProperty().addListener((obs, oldVal, newVal) -> updateScale(contentPane, root));

        gameScene = new Scene(root, DESIGN_WIDTH, DESIGN_HEIGHT);
    }
    
    private void updateScale(BorderPane content, StackPane parent) {
        double scaleX = parent.getWidth() / DESIGN_WIDTH;
        double scaleY = parent.getHeight() / DESIGN_HEIGHT;
        
        double scale = Math.min(scaleX, scaleY);
        
        content.setScaleX(scale);
        content.setScaleY(scale);
    }
    
    private void startNewGame() {
        playNewGameSound();

        game.startNewGame();
        
        hitButton.setDisable(false);
        standButton.setDisable(false);
        newGameButton.setDisable(true);
        
        updateView();
    }

    private void handleHitAction() {
        playHitSound();

        game.playerHits();
        updateView();
        if (game.isGameOver()) endTurn();
    }
    
    private void handleStandAction() {
        playStandSound();

        game.playerStands();
        updateView();
        endTurn();
    }
    
    private void endTurn() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
        newGameButton.setDisable(false); // Mở nút New Game
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
            // Check and show the back of the card
            dealerCardsBox.getChildren().add(createCardImageView("/cards/BACK.png"));
            if (game.getDealer().getHand().getCards().size() > 1) {
                Card visibleCard = game.getDealer().getHand().getCards().get(1);
                dealerCardsBox.getChildren().add(createCardImageView(visibleCard.getImagePath()));
            }
            dealerScoreLabel.setText("Dealer's Cards");
        }
       statusLabel.setText(game.getGameResult());

       playerWinsLabel.setText(game.getPlayer().getName() + " Wins: " + game.getPlayerWins());
       dealerWinsLabel.setText("Dealer Wins: " + game.getDealerWins());
    }

    private ImageView createCardImageView(String imagePath) {
        if (getClass().getResource(imagePath) == null) {
            System.err.println("Could not find image: " + imagePath);
            return new ImageView(); 
        }
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(CARD_WIDTH);
        imgView.setPreserveRatio(true);
        return imgView;
    }

    // --- Sound effects ---
    
    private void playBackgroundMusic() {
        try {
            if (getClass().getResource("/sounds/background.mp3") != null) {
                String path = getClass().getResource("/sounds/background.mp3").toString();
                Media media = new Media(path);
                backgroundMusicPlayer = new MediaPlayer(media);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusicPlayer.setVolume(0.5); 
                backgroundMusicPlayer.play();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void playHitSound() {
        try {
            if (getClass().getResource("/sounds/hit.mp3") != null) {
                AudioClip clip = new AudioClip(getClass().getResource("/sounds/hit.mp3").toString());
                clip.play();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void playStandSound() {
        try {
            if (getClass().getResource("/sounds/stand.mp3") != null) {
                AudioClip clip = new AudioClip(getClass().getResource("/sounds/stand.mp3").toString());
                clip.play();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void playNewGameSound() {
        try {
            if (getClass().getResource("/sounds/shuffle.mp3") != null) {
                AudioClip clip = new AudioClip(getClass().getResource("/sounds/shuffle.mp3").toString());
                clip.play();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}








