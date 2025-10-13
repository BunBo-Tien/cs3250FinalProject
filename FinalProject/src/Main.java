import javafx.application.Application;

/*
 * This is the demo of the black jack game.
 * This is the main class that use to execute the code.
 * 
 *  @author Tien Tran
 *
 */
public class Main {
    public static void main(String[] args) {
        // Gọi phương thức launch của JavaFX Application,
        // truyền vào lớp GUI chính của chúng ta.
        Application.launch(BlackjackGUI.class, args);
    }
}

