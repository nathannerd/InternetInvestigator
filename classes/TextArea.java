package classes;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

/**
 * @author Jake Goodman
 * @since 2018-05-15
 * @version 1
 *
 * Version 1:
 * Author: Jake Goodman
 * Date: 2018-05-13
 * Description: The text area where captions and comments are located. This has been modified throughout the whole
 *              project but there has been no durastic refactoring that is worth a new version
 * Time spent: 5 hours
 *
 * Time spent on profile related classes: 7 hours
 * */
public class TextArea extends ScrollPane {

    private VBox vBox;
    private String caption;

    /**
     * caption initialized to given String
     * The height of the photo is given so the size of the TextArea can be sized appropriately
     * @param caption The caption of the post that this instance is associated with
     * @param photoHeight The height of the photo that this instance is associated with
     */
    public TextArea(String caption, double photoHeight) {
        super();
        this.caption = caption;

        // Rectangle object is made, and sized with the given photoHeight
        Rectangle rect = new Rectangle(280, photoHeight * 2, Color.LIGHTGRAY);

        // Text object is made. Its content is the given caption
        Text text = new Text();
        text.setWrappingWidth(280);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText(caption);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // VBox contains the text and is aligned at the top left
        vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.TOP_LEFT);

        // StackPane contains both the rectangle and the VBox
        StackPane content = new StackPane();
        content.getChildren().addAll(rect, vBox);

        this.setPrefSize(300, photoHeight);
        this.setContent(content);
    }

    /**
     * Same function as the previous constructor except the size of the rectangle is hard-coded at 600x500
     * @param caption The caption of the post that this instance is associated with
     */
    public TextArea(String caption) {
        super();
        this.caption = caption;

        // Rectangle object is made, and sized with the given photoHeight
        Rectangle rect = new Rectangle(600, 500, Color.LIGHTGRAY);

        // Text object is made. Its content is the given caption
        Text text = new Text();
        text.setWrappingWidth(600);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText(caption);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // VBox contains the text and is aligned at the top left
        vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.TOP_LEFT);

        // StackPane contains both the rectangle and the VBox
        StackPane content = new StackPane();
        content.getChildren().addAll(rect, vBox);

        this.setPrefSize(600, 500);
        this.setContent(content);
    }

    /**
     * TextArea is created with the appropriate dimensions for a timelineView
     * @param caption The caption of the post that this instance is associated with
     * @param timelineView indicates that this instance should have the dimensions for a timelineView
     */
    public TextArea(String caption, boolean timelineView) {
        super();
        this.caption = caption;

        // Rectangle object is made, and sized with the given photoHeight
        Rectangle rect = new Rectangle(400, 100, Color.LIGHTGRAY);

        // Text object is made. Its content is the given caption
        Text text = new Text();
        text.setWrappingWidth(400);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText(caption);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // VBox contains the text and is aligned at the top left
        vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.TOP_LEFT);

        // StackPane contains both the rectangle and the VBox
        StackPane content = new StackPane();
        content.getChildren().addAll(rect, vBox);

        this.setPrefSize(400, 100);
        this.setContent(content);
    }

    /**
     * Adds a comment to this TextArea
     * @param person person who left the comment
     * @param comment the content of the comment
     */
    public void addComment(Person person, String comment) {
        Text text = new Text();
        text.setWrappingWidth(280);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText(String.format("%s: %s", person.getName(), comment));
        text.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

        vBox.getChildren().addAll(new Text(), text);
    }

    /**
     * Adds multiple comments to this TextArea
     * @param people list of people who left comments
     * @param comments list of comments that were left
     */
    public void addComments(Person[] people, String[] comments) {
        for (int i = 0; i < people.length; i++) {
            addComment(people[i], comments[i]);
        }
    }

    /**
     * Getter for caption
     * @return caption
     */
    public String getCaption() {
        return caption;
    }
}