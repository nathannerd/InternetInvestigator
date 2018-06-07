package classes;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

import java.io.*;

/**
 * @author Nathan Henry
 * @since 2018-05-28
 * @version 1 (current version)
 *
 * Version 1:
 * Author: Nathan Henry
 * Date: 2018-06-2
 * Description: Reads from a file to display the high score information
 * Time spent: 1 hour
 */
public class HighScores extends Scene {
    private StackPane root;

    /**
     * Default constructor for HighScores
     * Displays and formats the all high scores of the game by reading information from a file with the
     * high score information
     */
    public HighScores()
    {
        // call to parent constructor
        super(new StackPane());

        root = new StackPane();
        this.setRoot(root);

        // main menu section
        StackPane mainMenuPane = new StackPane();
        mainMenuPane.setPickOnBounds(false);
        mainMenuPane.setPadding(new Insets(480, 0, 0, 200));
        Photo menuButton = new Photo("resources/buttons/MainMenuB.png");
        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                Runner.setStage(new Scene(new MainMenu()), 550, 500)
        );
        mainMenuPane.getChildren().add(menuButton);

        // section to clear all previous high scores by erasing the high scores text file
        StackPane clearPane = new StackPane();
        clearPane.setPickOnBounds(false);
        clearPane.setPadding(new Insets(70, 370-54, 480-70, 54));
        Photo clearScores = new Photo("resources/buttons/ClearScores.png");

        // Upon clicking clearScores, high scores file is overwritten to be blank
        clearScores.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter("src/resources.gamesave/HighScores.txt"));
                writer.close();
            } catch (IOException e2) {}
            root.getChildren().remove(3);
        });
        clearPane.getChildren().add(clearScores);

        root.getChildren().add(new Photo("resources/screens/HighScoresScreen.png"));
        root.getChildren().add(mainMenuPane);
        root.getChildren().add(clearPane);
        displayScores();
    }

    /**
     * Helper method to display and format the scores
     */
    private void displayScores()
    {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        StackPane scorePane = new StackPane();
        scorePane.setPickOnBounds(false);
        scorePane.setPadding(new Insets(130, 0, 0, 50));

        VBox scoreList = new VBox(7);
        scoreList.setPickOnBounds(false);

        Text text;

        try {
            BufferedReader r = new BufferedReader(new FileReader("src/resources/gamesave/HighScores.txt"));
            String temp;
            // keep reading lines until there are none left to read
            while (true) {
                temp = r.readLine();
                if (temp == null || temp.equals(""))
                    break;
                names.add(temp.substring(0, temp.indexOf('%')));
                scores.add(Integer.parseInt(temp.substring(temp.indexOf('%')+1)));

                String t = names.size() + ". " + names.get(names.size() - 1);

                if (names.size() == 10)
                {
                    for (int i = 1; i <= 22 - names.get(names.size() - 1).length(); i++) {
                        t += " ";
                    }
                }
                else {
                    for (int i = 0; i <= 22 - names.get(names.size() - 1).length(); i++) {
                        t += " ";
                    }
                }

                t += Integer.toString(scores.get(scores.size()-1))+ "%";
                // t is formatted so that no matter the length of the name, it will line up in each column

                text = new Text(t);
                text.setFill(Color.WHITE);
                text.setFont(new Font("Courier New", 20));
                scoreList.getChildren().add(text);
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scorePane.getChildren().add(scoreList);
        root.getChildren().add(scorePane);
    }
}
