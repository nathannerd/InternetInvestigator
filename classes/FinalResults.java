package classes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

import java.io.*;

/**
 * @author Nathan Henry
 * @since 2018-05-19
 * @version 2
 *
 * Version 1:
 * 2018-06-05
 * Hours spent: 1
 * Description: Final results screen displays how the user did at the end of the game
 */
public class FinalResults extends Scene {
    private static int wins;
    private static int losses;

    /**
     * Default constructor for FinalResults
     * Shows the user how they did throughout the game.
     * Includes level explanation and what levels they got right/wrong
     */
    public FinalResults()
    {
        // call to parent class
        super(new StackPane());

        StackPane root = new StackPane();
        this.setRoot(root);

        BackgroundMusic bg = new BackgroundMusic();
        // background music terminates for the final results screen
        BackgroundMusic.stop();
        String name = "";

        // displays the number of wins/losses during the playthrough
        StackPane wonLost = new StackPane();
        wonLost.setPadding(new Insets(103, 0, 0, 410));
        VBox wlVBox = new VBox(10);
        Text won = new Text();
        won.setText(Integer.toString(wins));
        won.setFont(new Font(40));
        won.setFill(Color.GREEN);
        Text lost = new Text();
        lost.setText(Integer.toString(losses));
        lost.setFont(new Font(40));
        lost.setFill(Color.RED);
        wlVBox.getChildren().addAll(won, lost);
        wonLost.getChildren().add(wlVBox);

        // to main menu section
        StackPane mmPane = new StackPane();
        mmPane.setPickOnBounds(false);
        mmPane.setPadding(new Insets(460, 170, 38, 170));
        Photo toMainMenu = new Photo("resources/buttons/mainmenu/ToMainMenu_1.png");
        mmPane.getChildren().add(toMainMenu);

        // pane directing user to level overviews
        StackPane overviewPane = new StackPane();
        overviewPane.setPickOnBounds(false);
        overviewPane.setPadding(new Insets(250, 125, 237, 130));
        Photo levelOverview = new Photo("resources/buttons/LevelOverview.png");
        overviewPane.getChildren().add(levelOverview);

        // pane displaying the users scores from all playthroughs
        StackPane scorePane = new StackPane();
        scorePane.setPickOnBounds(false);
        scorePane.setPadding(new Insets(295, 0, 0, 0));
        Text score = new Text(Integer.toString(wins*100/6) + " %");
        score.setFill(Color.WHITE);
        score.setFont(new Font(75));
        scorePane.getChildren().add(score);

        // Upon clicking toMainMenu, set screen to new MainMenu
        toMainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.setRoot(new MainMenu()));

        StackPane root2 = new StackPane();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setHeight(485);
        stage.setWidth(274);
        stage.initStyle(StageStyle.UNDECORATED);

        // Upon clicking levelOverview, new window opens up displaying explanations for each level
        levelOverview.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            StackPane exit1 = new StackPane();
            exit1.setPickOnBounds(false);
            exit1.setPadding(new Insets(10, 274-24, 485-24, 10));
            Photo closeButton = new Photo ("resources/buttons/ExitButton.png");
            exit1.getChildren().add(closeButton);

            StackPane reportPane = new StackPane();
            reportPane.setPickOnBounds(false);
            reportPane.setPadding(new Insets(114, 0, 0, 34));
            VBox reportButtons = new VBox(47);

            Photo reasonButton;
            Photo[] windows = {new Photo("resources/screens/reasons/reason1.png"),
                    new Photo("resources/screens/reasons/reason2.png"),
                    new Photo("resources/screens/reasons/reason3.png"),
                    new Photo("resources/screens/reasons/reason4.png"),
                    new Photo("resources/screens/reasons/reason5.png"),
                    new Photo("resources/screens/reasons/reason6.png")};
            for (int i = 0; i<6; i++) {
                reasonButton = new Photo("resources/buttons/ReasonButton.png");
                StackPane root3 = new StackPane();

                // stage shownWhen clicked reasonButton
                Scene scene2 = new Scene(root3);
                Stage stage2 = new Stage();
                stage2.setScene(scene2);
                stage2.setHeight(328);
                stage2.setWidth(250);
                stage2.initStyle(StageStyle.UNDECORATED);
                StackPane exit2 = new StackPane();
                exit2.setPickOnBounds(false);
                exit2.setPadding(new Insets(10, 250-24, 328-24, 10));
                Photo closeButton2 = new Photo ("resources/buttons/ExitButton.png");
                exit2.getChildren().add(closeButton2);

                root3.getChildren().addAll(windows[i], exit2);

                // Upon clicking reasonButton, show stage2
                reasonButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> stage2.show());

                // Close the stage when click close button
                closeButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> stage2.close());

                addVeil(stage2, root2);
                reportButtons.getChildren().add(reasonButton);
            }
            reportPane.getChildren().add(reportButtons);
            reportButtons.setPickOnBounds(false);

            root2.getChildren().add(new Photo("resources/screens/LevelOverviewWindow.png"));
            root2.getChildren().add(exit1);
            root2.getChildren().add(reportPane);

            closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> stage.close());

            stage.show();
        });

        root.getChildren().add(new Photo("resources/buttons/FinalResults.png", 529, 500));
        root.getChildren().add(wonLost);
        root.getChildren().add(mmPane);
        addVeil(stage, root);
        root.getChildren().add(overviewPane);
        root.getChildren().add(scorePane);

        // Reads from the high scores info file
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("src/resources/gamesave/Score.txt"));
            writer.println("0");
            writer.close();

            BufferedReader r = new BufferedReader(new FileReader("src/resources/gamesave/UserName.txt"));
            name = r.readLine();
            r.close();

            ArrayList<Integer> scores = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            String temp;
            BufferedReader r2 = new BufferedReader(new FileReader("src/resources/gamesave/HighScores.txt"));

            // populates names and scores from high scores file
            for (int i = 0; i<10; i++) {
                temp = r2.readLine();
                if (temp == null || temp.equals("")) {
                    break;
                }
                scores.add(Integer.parseInt(temp.substring(temp.indexOf("%")+1)));
                names.add(temp.substring(0, temp.indexOf("%")));
            }
            r2.close();

            scores.add(wins*100/6);
            names.add(name);

            // bubble sort high scores
            int n = scores.size();
            int tempScore;
            String tempNames;
            for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){
                    if(scores.get(j-1) < scores.get(j)){
                        tempScore = scores.get(j-1);
                        scores.set(j-1, scores.get(j));
                        scores.set(j, tempScore);

                        tempNames = names.get(j-1);
                        names.set(j-1, names.get(j));
                        names.set(j, tempNames);
                    }
                }
            }

            PrintWriter writer2 = new PrintWriter(new FileWriter("src/resources/gamesave/HighScores.txt"));
            for (int i = 0; i<Math.min(names.size(), 10); i++)
            {
                if (names.get(i) == null) {
                    break;
                }
                writer2.println(names.get(i) + "%" + Integer.toString(scores.get(i)));
            }
            writer2.close();

            PrintWriter writer3 = new PrintWriter(new FileWriter("src/resources/gamesave/UserName.txt"));
            writer3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increments wins by 1
     */
    public static void addWin()
    {
        wins++;
    }

    /**
     * Increments losses by 1
     */
    public static void addLoss()
    {
        losses++;
    }

    /**
     * Getter for wins
     * @return wins
     */
    public static int getWins() {
        return wins;
    }

    /**
     * Getter for losses
     * @return loses
     */
    public static int getLosses() {
        return losses;
    }

    /**
     * Setter for both wins and losses
     * @param w wins
     * @param l losses
     */
    public static void setWinLosses(int w, int l)
    {
        wins = w;
        losses = l;
    }

    /**
     * The passed in stage is highlighted and all other stages are dimmed in color and are unclickable
     * If hint text is not an empty string, a bottom window is also highlighted with a hint from brother
     * @param stage stage to be highlighted
     * @param pane StackPane in which the vail disables
     */
    private void addVeil(Stage stage, StackPane pane)
    {
        StackPane vPane = new StackPane();
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        veil.visibleProperty().bind(stage.showingProperty());
        vPane.getChildren().add(veil);
        vPane.setPickOnBounds(false);
        pane.getChildren().add(vPane);
    }

}
