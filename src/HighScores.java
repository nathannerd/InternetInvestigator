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

public class HighScores extends Scene {
    private StackPane root;

    public HighScores()
    {
        super(new StackPane());

        root = new StackPane();
        this.setRoot(root);

        StackPane mainMenuPane = new StackPane();
        mainMenuPane.setPickOnBounds(false);
        mainMenuPane.setPadding(new Insets(480, 0, 0, 200));
        Photo menuButton = new Photo("buttons/MainMenuB.png");
        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Main.setStage(new Scene(new MainMenu()), 550, 500);
        });
        mainMenuPane.getChildren().add(menuButton);

        StackPane clearPane = new StackPane();
        clearPane.setPickOnBounds(false);
        clearPane.setPadding(new Insets(70, 370-54, 480-70, 54));
        Photo clearScores = new Photo("buttons/ClearScores.png");
        clearScores.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try
            {
                PrintWriter writer = new PrintWriter(new FileWriter("HighScores.txt"));
                writer.close();
            } catch (IOException e2) {}
            root.getChildren().remove(3);
        });
        clearPane.getChildren().add(clearScores);

        root.getChildren().add(new Photo("screens/HighScoresScreen.png"));
        root.getChildren().add(mainMenuPane);
        root.getChildren().add(clearPane);
        displayScores();
    }

    private void displayScores()
    {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();

        StackPane scorePane = new StackPane();
        scorePane.setPickOnBounds(false);
        scorePane.setPadding(new Insets(130, 0, 0, 50));

        VBox scoreList = new VBox(7);
        scoreList.setPickOnBounds(false);

        Text text;

        try {
            BufferedReader r = new BufferedReader(new FileReader("HighScores.txt"));
            String temp;
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

                text = new Text(t);
                text.setFill(Color.WHITE);
                text.setFont(new Font("Courier New", 20));
                scoreList.getChildren().add(text);
            }
            r.close();
        } catch (IOException e) {}

        scorePane.getChildren().add(scoreList);
        root.getChildren().add(scorePane);
    }
}
