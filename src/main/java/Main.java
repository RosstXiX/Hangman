import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);

        initializeStage();
    }

    //Creating main stage
    private static void initializeStage() throws IOException {
        Pane root = FXMLLoader.load(Main.class.getResource("MainMenu.fxml"));
        root.getStylesheets().add("styles/style.css");

        Scene scene = new Scene(root);
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        Sounds.playMainMenuMusic();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
