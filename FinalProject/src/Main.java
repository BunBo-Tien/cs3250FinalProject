import javafx.application.Application;

/*
 * This is the demo of the black jack game. Some of the comment and console output is written in
 * vietnamese language for me to better understanding what's going on. 
 * 
 * This is the main class that use to execute the code.
 *
 * Lớp Main - Điểm khởi đầu (entry point) của ứng dụng JavaFX.
 * Nhiệm vụ duy nhất của lớp này là khởi chạy lớp giao diện chính (BlackjackGUI).
 */
public class Main {
    public static void main(String[] args) {
        // Gọi phương thức launch của JavaFX Application,
        // truyền vào lớp GUI chính của chúng ta.
        Application.launch(BlackjackGUI.class, args);
    }
}

