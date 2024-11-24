import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Game {
    //File with questions
    private static final File JSON = new File("src/main/resources/questions.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static int attempts;
    private static String currentHint; //Question
    private static String currentWord;
    private static int guessedLetters;
    //3 ArrayLists for 3 difficulties with 5 numbers of recently questions
    private static final ArrayList<ArrayList<Integer>> recentlyUsed = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(-1, -1, -1, -1, -1)),
                                                                                                    new ArrayList<>(Arrays.asList(-1, -1, -1, -1, -1)),
                                                                                                    new ArrayList<>(Arrays.asList(-1, -1, -1, -1, -1))));

    public static void startNewGame(int difficulty) throws IOException {

        String[] a = pickWord(difficulty);
        currentHint = a[0];
        currentWord = a[1];
        attempts = 0;
        guessedLetters = 0;

    }

    //Random word picker
    private static String[] pickWord(int difficulty) throws IOException {
        String s = switch (difficulty) {
            case 0 -> "easy";
            case 1 -> "medium";
            case 2 -> "hard";
            default -> throw new IllegalStateException("Unexpected value: " + difficulty);
        };

        int x;
        do {
            //If number of question contains in recently asked pick another word
            x = (int) (Math.random() * MAPPER.readTree(JSON).get(s).size());
        } while (recentlyUsed.get(difficulty).contains(x));

        //Add picked word to recently picked
        recentlyUsed.get(difficulty).removeFirst();
        recentlyUsed.get(difficulty).add(x);

        JsonNode b = MAPPER.readTree(JSON).get(s).get(x);

        //Return question and answer
        return new String[] {b.get("q").asText(), b.get("a").asText()};
    }

    public static ArrayList<Integer> tryLetter(char letter, Pane root) {
        //List for indexes of guessed letters
        ArrayList<Integer> x = new ArrayList<>();

        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.toCharArray()[i] == letter) {
                x.add(i);
                guessedLetters++;
            }
        }

        //If at least one letter guessed
        if (!x.isEmpty()) {
            //If word guessed
            if(guessedLetters == currentWord.length()) {
                showEnd(root, "w");
            }
            //Else
            return x;
        }

        //If no letters guessed
        attempts++;

        //If lose
        if(attempts == 4) {
            showEnd(root, "l");
        }

        return null;
    }

    //Show endgame window
    private static void showEnd(Pane root, String x) {
        root.setMouseTransparent(true);
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            Pane lw;
            try {
                Rectangle blur = new Rectangle(1920, 1080);
                blur.setOpacity(0.5);
                lw = FXMLLoader.load(Game.class.getResource("WinLose.fxml"));
                lw.getStylesheets().add(Game.class.getResource("/styles/style.css").toExternalForm());
                DropShadow a = new DropShadow(100, Color.GREY);
                a.setSpread(0.1);
                lw.setEffect(a);
                root.getChildren().add(blur);
                lw.setLayoutX(660);
                lw.setLayoutY(140);
                if (x.equals("w")) {
                    ((Label) lw.getChildren().get(0)).setText("Ви перемогли!");
                } else if (x.equals("l")) {
                    ((Label) lw.getChildren().get(0)).setText("Ви програли!");
                    ((Label) lw.getChildren().getLast()).setText("Правильна вiдповiдь:\n" + currentWord);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            root.getChildren().add(lw);
            root.setMouseTransparent(false);
        });
        delay.play();
    }

    public static int getWordLength() {
        return currentWord.length();
    }

    public static int getAttempts() {
        return attempts;
    }

    public static String getCurrentHint() {
        return currentHint;
    }

}
