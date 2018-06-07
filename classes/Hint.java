package classes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Nathan Henry and Jake Goodman
 * @since 2018-05-28
 * @version 2 (current version)
 *
 * Version 2:
 * Author: Jake Goodman
 * Date: 2018-06-06
 * Description: Made the box and font larger so it catches the user's eye. We asked people to play the game, and
 *              they did not notice the brother's comments
 * Time spent: 10 minutes
 *
 * Version 1:
 * Author: Nathan Henry
 * Date: 2018-05-28
 * Description: Little box displays at the bottom displaying a hint from the older brother
 * Time spent: 10 minutes
 */
public class Hint extends StackPane {

    /**
     * Constructor for Hint
     * window displays with a comment from brother when node are clicked in a profile class
     * @param hint content of the hint
     */
    public Hint(String hint) {
        // call to default parent constructor
        super();
        this.setAlignment(Pos.BOTTOM_LEFT);
        this.setPadding(new Insets(0, 0, 20, 20));

        StackPane hintPane = new StackPane();
        hintPane.setMaxHeight(240);
        hintPane.setMaxWidth(480);
        hintPane.setAlignment(Pos.TOP_LEFT);

        Rectangle box = new Rectangle();
        box.setHeight(160);
        box.setWidth(400);
        box.setFill(Color.WHITE);
        box.setStroke(Color.BLACK);

        hintPane.getChildren().add(box);
        this.getChildren().add(hintPane);

        Text t = new Text("Brother: " + hint);
        StackPane.setMargin(t, new Insets(10, 10, 10, 10));
        t.setFont(new Font(24));
        t.setWrappingWidth(380);
        hintPane.getChildren().add(t);
    }
}

