package classes;

import javafx.animation.*;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * @author Nathan Henry
 * @since 2018-05-20
 * @version 3
 *
 * This class is the template for text resources.screens in the game.
 * It uses the classes Type and Enter as resources.sound affects and
 * animates text letter by letter on a black screen like a
 * typewriter.
 *
 * Hours spent on version 3: 6 hours
 *
 * In version 3, I redid everything and made TextScreen a
 * super class, with subclasses being individual sections
 * of the game where there will be a text screen.
 *
 * Version 2:
 * 2018-05-19
 * Hours spent: 3 hours
 *
 * In version 2, I used the JavaFX Animation class,
 * though I formatted everything badly with messy
 * code and I decided to redo everything.
 *
 * Version 1:
 * 2018-05-19
 * Hours spent: 2 hours
 *
 * In version 1, I tried to animate by adding and removing
 * nodes, while using Thread.sleep(). After a lot of coding,
 * I found that this approach wouldn't work.
 */
public class TextScreen extends StackPane {
    private Thread t1 = new Thread(new Type());
    private Thread t2 = new Thread(new Enter());
    private VBox rows;
    private HBox lineBox;
    private Text text;
    private ArrayList<ArrayList<Rectangle>> boxes;
    private ArrayList<ArrayList<StackPane>> panes;

    /**
     * Constructor, will be overridden in subclasses.
     */
    public TextScreen() {
        super();
    }

    /**
     * Used to animate each screen, consisting of calls to paragraph for each paragraph.
     * Will be called within an overridden method of screen in a subclass.
     * @param lines
     * @param size
     * @param rowsAbove
     * @param scene
     */
    public void screen(String lines[][], int size, int[] rowsAbove, int scene)
    {
        this.getChildren().removeAll();
        boxes = new ArrayList<ArrayList<Rectangle>>(); // contains all the boxes covering the letters
        panes = new ArrayList<ArrayList<StackPane>>(); // the panes containing both the boxes and text
        this.getChildren().add(new Rectangle(500, 550, Color.BLACK)); // background black box
        int counter = 0;
        rows = new VBox(10);

        for (int i2 = 0; i2<lines.length; i2++) {
            addRow(rowsAbove[i2]);
            boxes.add(new ArrayList<Rectangle>());
            panes.add(new ArrayList<StackPane>());
            counter = 0;
            for (int i = 0; i < lines[i2].length; i++) {
                lineBox = new HBox(); // new HBox consisting of panes with letters and boxes covering letters, for each line
                for (int j = 0; ; j++) {
                    if (j == lines[i2][i].length()) break; // breaks out of loop when j is the length of the line
                    text = new Text(Character.toString(lines[i2][i].charAt(j))); // Text node for each letter
                    text.setFill(Color.WHITE); // white text
                    text.setFont(new Font("Courier New", size));
                    boxes.get(i2).add(new Rectangle(size - 2, size + 4, Color.BLACK));
                    panes.get(i2).add(new StackPane(text, boxes.get(i2).get(counter)));
                    lineBox.getChildren().add(panes.get(i2).get(counter));
                    // the stackpane with text and a box is added to linebox
                    counter++;
                }
                rows.getChildren().add(lineBox); // adds lineBox to the rows
            }
        }
        this.getChildren().add(rows);
    }

    /**
     * Animates a paragraph, letter by letter.
     * @param lineList
     * @param lines
     * @param delay
     * @param paragraphNum
     */
    public void paragraph(String[][] lineList, String[] lines, int delay, int paragraphNum){
        Timeline timeline = new Timeline();
        int counter = 0;
        int spaces = 0;
        for (int i = 0; i<lineList[paragraphNum].length; i++) { // loops through the paragraph
            for (int j = 0;; j++) {
                if (j == lines[i].length()) // if it is the end of a line
                {
                    timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(delay + 180 * (counter-1)), e -> t2.run(),
                            new KeyValue(boxes.get(paragraphNum).get(counter-1).translateXProperty(), 0)), new KeyFrame(Duration.millis(delay + 180 * (counter-1) + 1),
                            new KeyValue(boxes.get(paragraphNum).get(counter-1).scaleXProperty(), 0)), new KeyFrame(Duration.millis(delay + 180 * counter + 500),
                            new KeyValue(boxes.get(paragraphNum).get(counter-1).scaleXProperty(), 0)));
                    break;
                }

                if (lines[i].charAt(j) == ' ' && spaces == 0) { // if there is a space, but just one space in a row
                    spaces++;
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay + 180 * counter), new KeyValue(boxes.get(paragraphNum).get(counter).scaleXProperty(), 1)));
                }
                else if (lines[i].charAt(j) != ' '){ // if there is a space, out of a series of spaces
                    spaces = 0;
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay + 180 * counter), e -> t1.run(),
                            new KeyValue(boxes.get(paragraphNum).get(counter).scaleXProperty(), 1)));
                }

                if (spaces < 1) { // if the character is not a space
                    timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(delay + 180 * counter + 1),
                            new KeyValue(boxes.get(paragraphNum).get(counter).scaleXProperty(), 0)), new KeyFrame(Duration.millis(delay + 180 * (counter + 1)),
                            new KeyValue(boxes.get(paragraphNum).get(counter).scaleXProperty(), 0)));
                }

                counter++; // increment counter for timing
            }
        }
        timeline.play();
    }

    /**
     * returns a string with spaces in front of it for indentation.
     * @param line
     * @return
     */
    public String indent(String line)
    {
        String newLine = "";
        for (int i = 0; i<2; i++)
        {
            newLine += " ";
        }
        newLine += line;
        return newLine;
    }

    /**
     * Plays the enter resources.sound file based on the amount of spaces between paragraphs.
     * @param lines
     */
    public void newParagraph(int lines)
    {
        Timeline timeline = new Timeline();
        for (int i = 0; i<lines; i++) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i*500), e -> t2.run())); // makes the enter resources.sound of a typewriter
        }
        timeline.play(); // plays the resources.sound
    }

    /**
     * Adds an empty row (with a letter the same colour as the background) above a paragraph.
     * @param numRows the number of rows added above the paragraph
     */
    public void addRow(int numRows)
    {
        for (int i = 0; i<numRows; i++)
        {
            lineBox = new HBox();
            text = new Text("J"); // J so the row is the height of a letter
            text.setFill(Color.BLACK);
            lineBox.getChildren().add(text);
            rows.getChildren().add(lineBox); // adds a new line with one letter to rows
        }
    }

}