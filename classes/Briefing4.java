package classes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Nathan Henry
 * @since 2018-06-03
 * @version 1
 *
 * Hours spent: 15 minutes
 *
 * This class extents TextScreen and creates a timeline consisting of letters printed onto the screen
 * with a typewriter animation.
 */

public class Briefing4 extends TextScreen {
    Pinboard pinboard;

    /**
     * No parameter constructor contains creation and initialization of lines and rows above paragraphs (rowsAbove).
     * Also, a timeline consisting of a call to screen and an action for when timeline is finished.
     */
    public Briefing4()
    {
        super();

        BackgroundMusic bm = new BackgroundMusic();

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("src/resources/gamesave/Score.txt"));
            writer.println("4");
            writer.close();
        } catch (IOException e) {}

        String[][][] lines = {{{"Log 5."},
                {"MONDAY", "December 25, 2017"},
                {"Our next predator to catch is in book club."},
                {"The first meeting was last Thursday,", "and the next meeting is in three days."},
                {"We started our latest book,", "The Outsiders, last meeting.", "I must catch the predator before Thursday."},
                {"Report the predator!"}}};

        int[][] rowsAbove = {{1, 1, 1, 1, 1, 1}};

        for (int i2 = 0; i2 < lines.length; i2++) {
            for (int i = 0; i < lines[i2].length; i++) {
                for (int j = 0; j < lines[i2][i].length; j++) {
                    lines[i2][i][j] = indent(lines[i2][i][j]); // indents all the lines
                }
            }
        }

        pinboard = new Pinboard(4, "chat", "9", new ArrayList<HBox>());

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> screen(lines[0], 12, rowsAbove[0], 1)),
                new KeyFrame(Duration.millis(61000), e -> {}));
        timeline.setOnFinished(e -> {
            Runner.setStage(pinboard, 750, 600);
        });
        timeline.play();
    }

    /**
     * Override of screen in TextSceen. Contains the timeline for the screen.
     * @param lines
     * @param size
     * @param rowsAbove
     * @param scene
     */
    public void screen(String lines[][], int size, int[] rowsAbove, int scene) {
        super.screen(lines, size, rowsAbove, scene);
        Timeline currentScreen = new Timeline();
        currentScreen.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> paragraph(lines, lines[0], 1000, 0)),
                new KeyFrame(Duration.millis(2000), e -> newParagraph(rowsAbove[1])),
                new KeyFrame(Duration.millis(3000), e -> paragraph(lines, lines[1], 1000, 1)),
                new KeyFrame(Duration.millis(8000), e -> newParagraph(rowsAbove[2])),
                new KeyFrame(Duration.millis(9000), e -> paragraph(lines, lines[2], 1000, 2)),
                new KeyFrame(Duration.millis(17000), e -> newParagraph(rowsAbove[3])),
                new KeyFrame(Duration.millis(18000), e -> paragraph(lines, lines[3], 1000, 3)),
                new KeyFrame(Duration.millis(32000), e -> newParagraph(rowsAbove[4])),
                new KeyFrame(Duration.millis(33000), e -> paragraph(lines, lines[4], 1000, 4)),
                new KeyFrame(Duration.millis(52000), e -> newParagraph(rowsAbove[5])),
                new KeyFrame(Duration.millis(53000), e -> paragraph(lines, lines[5], 1000, 5)));
        currentScreen.play();
    }
}
