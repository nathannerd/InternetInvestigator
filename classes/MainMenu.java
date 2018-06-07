package classes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.*;

/**
 * @author Nathan Henry
 * @version 1
 * <p>
 * Hours spent: 1 hour
 * @since 2018-05-22
 */
public class MainMenu extends StackPane {
    private int level;

    public MainMenu() {
        super();
        this.getChildren().add(new Photo("resources/screens/MainMenu.png", 530, 500));

        VBox buttons = new VBox(20);
        Photo newGame = new Photo("resources/buttons/NewGame.png");
        Photo continueB = new Photo("resources/buttons/Continue.png");
        Photo highScores = new Photo("resources/buttons/HighScores.png");
        Photo quit = new Photo("resources/buttons/Quit.png");
        Photo credits = new Photo("resources/buttons/CreditsButton.png");
        level = 0;

        try {
            BufferedReader r = new BufferedReader(new FileReader("src/resources/gamesave/Score.txt"));
            String line = r.readLine();
            if (line != null) {
                level = Integer.parseInt(line);
            }
            r.close();
        } catch (IOException e) {
        }

        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            FinalResults.setWinLosses(0, 0);
            this.getChildren().clear();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter("src/resources/gamesave/Score.txt"));
                writer.println("0");
                writer.close();
            } catch (IOException e2) {
            }
            this.getChildren().add(new Intro());
        });
        continueB.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            this.getChildren().clear();
            continueGame();
        });
        highScores.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Runner.setStage(new HighScores(), 550, 500);
        });
        quit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.exit(0));
        credits.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Runner.setStage(new Credits(), 550, 500));

        if (level != 0) {
            buttons.getChildren().addAll(newGame, continueB, highScores, credits, quit);
        } else {
            buttons.getChildren().addAll(newGame, highScores, credits, quit);
        }

        buttons.setPadding(new Insets(40, 0, 0, 100));
        buttons.setAlignment(Pos.CENTER);
        this.getChildren().add(buttons);
    }

    private void continueGame() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("src/resources/gamesave/WinsLosses.txt"));
            String temp = r.readLine();
            FinalResults.setWinLosses(Integer.parseInt(temp.substring(0, temp.indexOf(' '))), Integer.parseInt(temp.substring(temp.indexOf(' ')+1)));
            r.close();
        } catch (IOException e) {}

        switch (level) {
            case 1:
                this.getChildren().add(new Briefing1());
                break;
            case 2:
                this.getChildren().add(new Briefing2());
                break;
            case 3:
                this.getChildren().add(new Briefing3());
                break;
            case 4:
                this.getChildren().add(new Briefing4());
                break;
            case 5:
                this.getChildren().add(new Briefing5());
                break;
            case 6:
                this.getChildren().add(new Briefing6());
                break;
        }
    }
}
