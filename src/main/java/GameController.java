import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;

public class GameController {

    @FXML
    private AnchorPane root;

    @FXML
    private void clickedLetter(MouseEvent event) {
        //Remove event from button(with letter) and set x-cross image on it
        ((Label) event.getSource()).setOnMouseClicked(null);
        ImageView img = new ImageView(new Image("images/xCross.png"));
        img.setFitWidth(85);
        img.setFitHeight(110);
        ((StackPane) ((Label) event.getSource()).getParent()).getChildren().add(img);

        //Try letter from button
        char l = ((Label) event.getSource()).getText().toCharArray()[0];
        ArrayList<Integer> result = Game.tryLetter(l, root);
        //If returned ArrayList with indexes of guessed letters
        if (result != null) {
            //Add all of them to the guessed letters area
            for(int i = 0; i < result.size(); i++){
                ((Label) ((StackPane) ((HBox) root.getChildren().get(root.getChildren().size() - 2)).getChildren().get(result.get(i))).getChildren().get(1)).setText(String.valueOf(l));
            }
        //Else if returned empty ArrayList
        } else {
            //Set new image of stickman
            ((ImageView) root.getChildren().get(1)).setImage(new Image(String.format("images/%d.png", Game.getAttempts())));
        }
    }
}
