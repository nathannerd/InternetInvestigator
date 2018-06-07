package classes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Nathan Henry
 * @since 2018-05-22
 * @version 1
 *
 * Description:
 * Hours spent: 30 mins
 */

public class Intro extends TextScreen {

    /**
     * Default constructor
     * This class has the screen for the user to enter their name and the first log
     */
    public Intro() {
        super();
        BackgroundMusic bg = new BackgroundMusic();

        HBox box = new HBox();
        VBox nameField = new VBox(35);
        nameField.setPadding(new Insets(15, 0, 0, 0));
        TextField textField = new TextField();
        Text prompt = new Text("Enter your name:\n" +
                "(But don't make your name longer than 15 characters)\n(And don't include the \"%\" symbol!)");
        prompt.setFill(Color.WHITE);

        Button b = new Button("Enter");

        // Upon clicking enter button, user is taken to the intro screen, unless the cheat code is entered,
        // in which case a window of the answers to all the levels is displayed
        b.setOnAction(e -> {
            if (textField.getCharacters().length() != 0) {
                if (textField.getCharacters().toString().indexOf('%') != -1) {
                    textField.clear();
                }
                else if (textField.getCharacters().length() > 15) {
                    textField.clear();
                }
                else if (textField.getCharacters().toString().equals("//CHEAT-CODE")) {
                    StackPane root2 = new StackPane();
                    Scene scene = new Scene(root2);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setHeight(485);
                    stage.setWidth(274);
                    stage.initStyle(StageStyle.UNDECORATED);

                    // exit button
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
                    for (int i = 0; i<6; i++)
                    {
                        // shows window for each reason as to why the predator was a predator in each level
                        reasonButton = new Photo("resources/buttons/ReasonButton.png");
                        StackPane root3 = new StackPane();

                        // Creates explanation for each level
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

                        root3.getChildren().add(windows[i]);
                        root3.getChildren().add(exit2);

                        // Upon clicking reasonButton, the corresponding explanation is displayed
                        reasonButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> stage2.show());

                        // Upon clicking closeButton, the stage is closed
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
                }
                else {
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter("src/resources.gamesave/UserName.txt"));
                        writer.println(textField.getCharacters().toString());
                        writer.close();
                    } catch (IOException e2) {
                    }
                    playTimeline();
                }
            }
        });

        box.getChildren().add(textField);
        box.getChildren().add(b);
        box.setAlignment(Pos.BASELINE_CENTER);

        nameField.getChildren().add(prompt);
        nameField.getChildren().add(box);
        nameField.setAlignment(Pos.BASELINE_CENTER);
        this.getChildren().add(new Rectangle(500, 550, Color.BLACK));
        this.getChildren().add(nameField);
    }

    /**
     * Plays the introduction text on the screen
     */
    private void playTimeline() {
        this.getChildren().add(new Briefing1());
        /*
        String[][][] lines = {{{"Log 1."}, {"MONDAY", "September 18, 2017"}, {"Today, in an attempt to make friends", "at my new school, I finally convinced",
                "my parents to let me get a", "social media account."}, {"I added 506 friends."}},

                {{"My older brother had a chat with me."}, {"He says that there are only 500 people", "that go to my school..."}},

                {{"And then I was introduced to the idea of"}, {"\"THE ONLINE PREDATOR\""},
                        {"Online Predator", "Definition:", "An ADULT user online that", "pretends to be younger", "to EXPLOIT others."}},

                {{"The Facts"}, {"In 2006, 464 incidents of child", "luring were reported across Canada."},
                        {"Internet predators are usually", "between the ages of 18 and 55."}, {"Victims of internet predators", "are usually between 11 and 15."},
                        {"33% of teens are friends with people", "on Facebook that they have never met!"}}};
        int[][] rowsAbove = {{1, 1, 2, 3}, {3, 3}, {3, 2, 2}, {1, 2, 1, 1, 1}};

        for (int i2 = 0; i2 < lines.length; i2++) {
            for (int i = 0; i < lines[i2].length; i++) {
                for (int j = 0; j < lines[i2][i].length; j++) {
                    lines[i2][i][j] = indent(lines[i2][i][j]);
                }
            }
        }

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> screen(lines[0], 12, rowsAbove[0], 1)),
                new KeyFrame(Duration.millis(53000), e -> screen(lines[1], 12, rowsAbove[1], 2)),
                new KeyFrame(Duration.millis(81000), e -> screen(lines[2], 12, rowsAbove[2], 3)),
                new KeyFrame(Duration.millis(127000), e -> screen(lines[3], 12, rowsAbove[3], 4)),
                new KeyFrame(Duration.millis(200000), e -> {}));
        timeline.setOnFinished(e -> {
            this.getChildren().removeAll();
            this.getChildren().add(new Briefing1());
        });
        timeline.play();*/
    }

    /**
     * This method contains the timing for each paragraph of each screen.
     * There will be calls to paragraph and newParagraph, with parameters based off of the scene.
     * @param lines
     * @param size
     * @param rowsAbove
     * @param scene
     */
    public void screen(String lines[][], int size, int[] rowsAbove, int scene) {
        super.screen(lines, size, rowsAbove, scene);
        Timeline currentScreen = new Timeline();
        if (scene == 1) {
            currentScreen.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> paragraph(lines, lines[0], 1000, 0)),
                    new KeyFrame(Duration.millis(2000), e -> newParagraph(rowsAbove[1])),
                    new KeyFrame(Duration.millis(3000), e -> paragraph(lines, lines[1], 1000, 1)),
                    new KeyFrame(Duration.millis(11000), e -> newParagraph(rowsAbove[2])),
                    new KeyFrame(Duration.millis(12000), e -> paragraph(lines, lines[2], 1000, 2)),
                    new KeyFrame(Duration.millis(42000), e -> newParagraph(rowsAbove[3])),
                    new KeyFrame(Duration.millis(43000), e -> paragraph(lines, lines[3], 1000, 3)));
        } else if (scene == 2) {
            currentScreen.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> paragraph(lines, lines[0], 1000, 0)),
                    new KeyFrame(Duration.millis(10000), e -> newParagraph(rowsAbove[1])),
                    new KeyFrame(Duration.millis(11000), e -> paragraph(lines, lines[1], 1000, 1)));
        } else if (scene == 3) {
            currentScreen.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> paragraph(lines, lines[0], 1000, 0)),
                    new KeyFrame(Duration.millis(10000), e -> newParagraph(rowsAbove[1])),
                    new KeyFrame(Duration.millis(11000), e -> paragraph(lines, lines[1], 1000, 1)),
                    new KeyFrame(Duration.millis(20000), e -> newParagraph(rowsAbove[2])),
                    new KeyFrame(Duration.millis(21000), e -> paragraph(lines, lines[2], 1000, 2)));
        } else if (scene == 4) {
            currentScreen.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> paragraph(lines, lines[0], 1000, 0)),
                    new KeyFrame(Duration.millis(5000), e -> newParagraph(rowsAbove[1])),
                    new KeyFrame(Duration.millis(8000), e -> paragraph(lines, lines[1], 1000, 1)),
                    new KeyFrame(Duration.millis(23000), e -> newParagraph(rowsAbove[2])),
                    new KeyFrame(Duration.millis(24000), e -> paragraph(lines, lines[2], 1000, 2)),
                    new KeyFrame(Duration.millis(38000), e -> newParagraph(rowsAbove[3])),
                    new KeyFrame(Duration.millis(39000), e -> paragraph(lines, lines[3], 1000, 3)),
                    new KeyFrame(Duration.millis(52000), e -> newParagraph(rowsAbove[4])),
                    new KeyFrame(Duration.millis(53000), e -> paragraph(lines, lines[4], 1000, 4)));
        }
        currentScreen.play();
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