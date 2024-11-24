import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.IOException;


public class MenuController {

    @FXML
    //Window with difficulties
    private void showDifficultySelector(MouseEvent event) throws IOException {
        Sounds.playButtonSound();
        ((Label) event.getSource()).setOnMouseClicked(null);

        Pane difficultySelectorPane = FXMLLoader.load(getClass().getResource("DifficultySelector.fxml"));

        DropShadow a = new DropShadow(100, Color.GREY);
        a.setSpread(0.1);

        difficultySelectorPane.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        difficultySelectorPane.setLayoutX(660);
        difficultySelectorPane.setLayoutY(140);

        Rectangle blur = new Rectangle(1920, 1080);
        blur.setOpacity(0.5);

        if ((((Label) event.getSource()).getParent()).getId() != null) {
            ((Pane) (((Label) event.getSource()).getParent()).getParent()).getChildren().add(difficultySelectorPane);
            return;
        }
        difficultySelectorPane.setEffect(a);
        ((Pane) ((Label) event.getSource()).getParent()).getChildren().add(blur);
        ((Pane) ((Label) event.getSource()).getParent()).getChildren().add(difficultySelectorPane);

    }

    @FXML
    //Method for buttons in difficulty selection window
    private void selectDifficulty(MouseEvent event) throws IOException {
        Sounds.playButtonSound();
        Scene scene = ((Label) event.getSource()).getParent().getParent().getScene();

        //Removing difficulty selection window
        ((Pane) ((Label) event.getSource()).getParent().getParent()).getChildren().removeLast();

        //Loading main game frame
        AnchorPane root = FXMLLoader.load(getClass().getResource("MainFrame.fxml"));

        //Id from buttons
        switch (((Label) event.getSource()).getId()) {
            case "ez":
                Game.startNewGame(0);
                break;
            case "md":
                Game.startNewGame(1);
                break;
            case "hd":
                Game.startNewGame(2);
                break;
        }

        //Set hint to the window
        ((Label) ((AnchorPane) root.getChildren().getLast()).getChildren().get(1)).setText(Game.getCurrentHint());

        //Creating area for guessed letters
        for(int i = 0; i < Game.getWordLength(); i++) {
            StackPane a = FXMLLoader.load(getClass().getResource("GuessingLetter.fxml"));
            a.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            ((HBox) root.getChildren().get(root.getChildren().size() - 2)).getChildren().add(a);
        }

        //Basic stickman image
        root.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        ((ImageView) root.getChildren().get(1)).setImage(new Image("images/0.png"));

        Sounds.stopMainMenuMusic();
        Sounds.playGameMusic();
        scene.setRoot(root);
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

}
