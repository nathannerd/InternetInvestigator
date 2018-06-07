package classes;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Date;

/**
 * @author Jake Goodman
 * @since 2018-05-13
 * @version 1 (current version)
 *
 * Version 1:
 * Author: Jake Goodman
 * Date: 2018-05-13
 * Description: Basic functionality of PicturePost, with date and likes working. Modifications have been made
 *              over the days but no big refactoring of the file that it would call for a new version!
 * Time spent: 5 hours
 */
public class PicturePost extends Post {
    private Photo photo;
    private Text numLikes;
    private StackPane stackPane;

    /**
     * Calls super constructor with given Date
     * photo is initialized to given Photo
     * Sets up view for a picture post which contains: box to display the date, box to display the number of likes,
     *                                                 and a list of the people who liked the post is displayed upon
     *                                                 hovering the number of likes
     * @param postingDate the Date of this post
     * @param photo the photo that was posted
     */
    public PicturePost (Date postingDate, Photo photo) {
        super(postingDate);

        this.stackPane = new StackPane();
        this.photo = photo;

        // white box which appears to contain the date
        Rectangle dateBox = new Rectangle();
        dateBox.setFill(Color.WHITESMOKE);
        dateBox.setWidth(150);
        dateBox.setHeight(60);

        // text displaying the date
        Text date = new Text(super.formatDate());
        date.setFont(new Font(25));
        date.setWrappingWidth(150);

        // white box which appears to contain the number of likes and the like image
        Rectangle likeBox = new Rectangle();
        likeBox.setFill(Color.WHITESMOKE);
        likeBox.setWidth(100);
        likeBox.setHeight(40);

        // the classic Facebook thumbs up image
        Photo likeImage = new Photo("resources/buttons/facebook_like.png", 40);
        likeImage.setAlignment(Pos.BOTTOM_LEFT);

        // numlikes hoverProperty is set to display a list of all people who liked the posts
        numLikes = new Text("0");
        numLikes.setFont(new Font(40));
        numLikes.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
            if (show) {
                ListView<String> list = new ListView<>();
                ObservableList<String> items = FXCollections.observableArrayList (
                        super.getPeopleLiked()
                );
                items.add(0, "People Liked:");
                list.setItems(items);
                likeImage.getChildren().add(list);
            } else {
                likeImage.getChildren().remove(likeImage.getChildren().size() - 1);
            }
        });

        likeImage.setSpacing(15);
        likeImage.getChildren().add(numLikes);

        // places all nodes in the correct alignment
        StackPane.setAlignment(likeBox, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(dateBox, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(date, Pos.BOTTOM_RIGHT);
        this.stackPane.getChildren().addAll(photo, likeBox, likeImage, dateBox, date);

        this.getChildren().add(this.stackPane);
    }

    /**
     * Constructor for PicturePost
     * This constructor is used for copying a PicturePost
     * When a copy of a PicturePost is required because the original cannot be used, this constructor is used
     * @param post PicturePost to be copied
     */
    public PicturePost(PicturePost post) {
        super(post.getPostingDate());
        this.setStackPane(post.getStackPane());
    }

    /**
     * Getter for the caption of this post
     * @return caption of this post
     */
    public String getCaption() {
        return photo.getTextArea().getCaption();
    }

    /**
     * Getter for photo
     * @return photo
     */
    public Photo getPhoto() {
        return photo;
    }

    /**
     * Sets up an HBox of how this post should look inside of a timeline
     * @return HBox timeline view of the post
     */
    @Override
    public HBox timelineView() {
        HBox view = new HBox();

        Photo newPhoto = new Photo(photo.getLink(), 100);
        // Rectangle object is made, and sized with the given photoHeight
        Rectangle rect = new Rectangle(500, 100, Color.LIGHTGRAY);

        // Text object is made. Its content is the given caption
        Text text = new Text();
        text.setWrappingWidth(400);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText(photo.getTextArea().getCaption());
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // VBox contains the text and is aligned at the top left
        VBox vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.TOP_LEFT);

        // StackPane contains both the rectangle and the VBox
        StackPane content = new StackPane();
        content.getChildren().addAll(rect, vBox);
        view.setMaxWidth(400);
        view.getChildren().addAll(newPhoto, content);
        return view;
    }

    /**
     * Adds a comment to this post
     * @param person person who left the comment
     * @param comment comment
     */
    @Override
    public void addComment(Person person, String comment) {
        photo.addComment(person, comment);
    }

    /**
     * Adds multiple comments to this post
     * @param people list of people who left the comments
     * @param comments comments
     */
    @Override
    public void addComments(Person[] people, String[] comments) {
        photo.addComments(people, comments);
    }

    /**
     * Adds one like to this post
     * Increments numLikes by one
     * @param person person who left the like
     */
    @Override
    public void addLike(Person person) {
        super.addLike(person);
        numLikes.setText(Integer.toString(super.getNumLikes()));
    }

    /**
     * Getter for stackPane
     * @return stackPane
     */
    public StackPane getStackPane() {
        return stackPane;
    }

    /**
     * Setter for stackPane
     * @param s StackPane to be set to
     */
    public void setStackPane(StackPane s) {
        stackPane = s;
        this.getChildren().add(stackPane);
    }
}