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


public class FinalResults extends Scene {
    private static int wins;
    private static int losses;

    public FinalResults()
    {
        super(new StackPane());
        StackPane root = new StackPane();
        this.setRoot(root); this.setRoot(root);

        BackgroundMusic.stop();
        String name = "";

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

        StackPane mmPane = new StackPane();
        mmPane.setPickOnBounds(false);
        mmPane.setPadding(new Insets(460, 170, 38, 170));
        Photo toMainMenu = new Photo("buttons/mainmenu/ToMainMenu_1.png");
        mmPane.getChildren().add(toMainMenu);

        StackPane overviewPane = new StackPane();
        overviewPane.setPickOnBounds(false);
        overviewPane.setPadding(new Insets(250, 125, 237, 130));
        Photo levelOverview = new Photo("buttons/LevelOverview.png");
        overviewPane.getChildren().add(levelOverview);

        StackPane scorePane = new StackPane();
        scorePane.setPickOnBounds(false);
        scorePane.setPadding(new Insets(295, 0, 0, 0));
        Text score = new Text(Integer.toString(wins*100/6) + " %");
        score.setFill(Color.WHITE);
        score.setFont(new Font(75));
        scorePane.getChildren().add(score);

        toMainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.setRoot(new MainMenu()));

        StackPane root2 = new StackPane();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setHeight(485);
        stage.setWidth(274);
        stage.initStyle(StageStyle.UNDECORATED);

        levelOverview.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            StackPane exit1 = new StackPane();
            exit1.setPickOnBounds(false);
            exit1.setPadding(new Insets(10, 274-24, 485-24, 10));
            Photo closeButton = new Photo ("buttons/ExitButton.png");
            exit1.getChildren().add(closeButton);

            StackPane reportPane = new StackPane();
            reportPane.setPickOnBounds(false);
            reportPane.setPadding(new Insets(114, 0, 0, 34));
            VBox reportButtons = new VBox(47);

            Photo reasonButton;
            for (int i = 0; i<6; i++)
            {
                reasonButton = new Photo("buttons/ReasonButton.png");
                StackPane root3 = new StackPane();
                Scene scene2 = new Scene(root3);
                Stage stage2 = new Stage();
                stage2.setScene(scene2);
                stage2.setHeight(150);
                stage2.setWidth(250);
                stage2.initStyle(StageStyle.UNDECORATED);
                StackPane exit2 = new StackPane();
                exit2.setPickOnBounds(false);
                exit2.setPadding(new Insets(10, 250-24, 150-24, 10));
                Photo closeButton2 = new Photo ("buttons/ExitButton.png");
                exit2.getChildren().add(closeButton2);

                root3.getChildren().add(new Photo("buttons/ReasonWindow.png"));
                root3.getChildren().add(exit2);

                reasonButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> {
                    stage2.show();
                });

                closeButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> {
                    stage2.close();
                });
                addVeil(stage2, root2);
                reportButtons.getChildren().add(reasonButton);
            }
            reportPane.getChildren().add(reportButtons);
            reportButtons.setPickOnBounds(false);

            root2.getChildren().add(new Photo("LevelOverviewWindow.png"));
            root2.getChildren().add(exit1);
            root2.getChildren().add(reportPane);

            closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> stage.close());

            stage.show();
        });

        root.getChildren().add(new Photo("buttons/FinalResults.png", 529, 500));
        root.getChildren().add(wonLost);
        root.getChildren().add(mmPane);
        addVeil(stage, root);
        root.getChildren().add(overviewPane);
        root.getChildren().add(scorePane);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("Score.txt"));
            writer.println("0");
            writer.close();

            BufferedReader r = new BufferedReader(new FileReader("UserName.txt"));
            name = r.readLine();
            r.close();

            ArrayList<Integer> scores = new ArrayList<Integer>();
            ArrayList<String> names = new ArrayList<String>();
            String temp;
            BufferedReader r2 = new BufferedReader(new FileReader("HighScores.txt"));
            for (int i = 0; i<10; i++) {
                temp = r2.readLine();
                if (temp == null || temp == "")
                {
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
                    if(scores.get(j-1) > scores.get(j)){
                        tempScore = scores.get(j-1);
                        scores.set(j-1, scores.get(j));
                        scores.set(j, tempScore);

                        tempNames = names.get(j-1);
                        names.set(j-1, names.get(j));
                        names.set(j, tempNames);
                    }
                }
            }

            PrintWriter writer2 = new PrintWriter(new FileWriter("HighScores.txt"));
            for (int i = 0; i<Math.min(names.size(), 10); i++)
            {
                    if (names.get(i) == null) {
                        break;
                    }
                    writer2.println(names.get(i) + "%" + Integer.toString(scores.get(i)));
            }
            writer2.close();

            PrintWriter writer3 = new PrintWriter(new FileWriter("UserName.txt"));
            writer3.close();
        } catch (IOException e) {}
    }

    public static void addWin()
    {
        wins++;
    }

    public static void addLoss()
    {
        losses++;
    }

    public static int getWins() {
        return wins;
    }

    public static int getLosses() {
        return losses;
    }

    public static void setWinLosses(int w, int l)
    {
        wins = w;
        losses = l;
    }

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
