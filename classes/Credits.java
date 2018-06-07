package classes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * @author Nathan Henry
 * @since 2018-06-07
 * @version 1
 *
 * Version 1:
 * Author: Nathan Henry
 * Date: 2018-06-07
 * Description: Small animation of credits going from the top of the screen to the bottom.
 * Time spent: 10 minutes
 */
public class Credits extends Scene {

    /**
     * No parameter constructor.
     * Starts the animation, and when it is finished,
     * it goes back to the main menu.
     */
    public Credits()
    {
        super(new StackPane());

        StackPane root = new StackPane();
        this.setRoot(root);

        Text text = new Text("Made By:\nJake Goodman\nAND\nNathan Henry");
        text.setFont(new Font(30));
        text.setFill(Color.WHITE);

        root.getChildren().add(new Rectangle(500, 550, Color.BLACK));
        root.getChildren().add(text);
        root.setAlignment(Pos.TOP_CENTER);
        text.setTextAlignment(TextAlignment.CENTER);

        javafx.animation.Timeline timeline = new javafx.animation.Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                new KeyValue(text.translateYProperty(), 0)), new KeyFrame(Duration.millis(5000),
                new KeyValue(text.translateYProperty(), 550)));
        timeline.play();
        timeline.setOnFinished(e -> this.setRoot(new MainMenu()));
    }
}
