import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
     * No parameter constructor
     */
    public Intro() {
        super();
        BackgroundMusic bg = new BackgroundMusic();


        HBox box = new HBox();
        VBox nameField = new VBox(5);
        nameField.setPadding(new Insets(10, 0, 0, 0));
        TextField textField = new TextField();
        Text prompt = new Text("Enter your name:");
        prompt.setFill(Color.WHITE);

        Button b = new Button("Enter");
        b.setOnAction(e -> {
            if (textField.getCharacters().length() != 0) {
                if (textField.getCharacters().toString().indexOf('%') != -1)
                {
                    textField.clear();
                }
                else {
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter("UserName.txt"));
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

    private void playTimeline() {
        this.getChildren().add(new Briefing1());/*
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

}

