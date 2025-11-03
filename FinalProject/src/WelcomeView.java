import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

/**
 * A class dedicated to building and managing the welcome screen.
 * Its single responsibility is to create the welcome UI and notify a listener
 * when the user is ready to play.
 */
public class WelcomeView {

    private Scene scene;

    /**
     * Constructs the welcome view.
     * @param onPlayClicked A callback function (Consumer) that will be executed
     * when the "Play Game" button is clicked. It provides the player's name.
     */
    public WelcomeView(Consumer<String> onPlayClicked) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #35654d;");
        layout.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome to Blackjack!");
        welcomeLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label nameLabel = new Label("Enter your name:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        TextField nameInput = new TextField("Player");
        nameInput.setMaxWidth(200);
        nameInput.setPromptText("Enter name here");

        Button playButton = new Button("Play Game");
        playButton.setStyle("-fx-background-color: #c9a43a; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        
        playButton.setOnAction(e -> {
            String playerName = nameInput.getText();
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Player";
            }
            // Execute the callback with the player's name
            onPlayClicked.accept(playerName);
        });

        layout.getChildren().addAll(welcomeLabel, nameLabel, nameInput, playButton);
        scene = new Scene(layout, 700, 550);
    }

    /**
     * Returns the constructed scene to be displayed on the stage.
     * @return The welcome scene.
     */
    public Scene getScene() {
        return this.scene;
    }
}
