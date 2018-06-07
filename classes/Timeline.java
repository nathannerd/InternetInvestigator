package classes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jake Goodman, Nathan Henry
 * @since 2018-05-16
 * @version 2
 *
 * Version 1
 * Author: Nathan Henry
 * Time spent: 30 mins
 *
 * Version 2
 * Author: Jake Goodman
 * Time spent on profile classes: 7 hours
 */
public class Timeline extends StackPane {

    private VBox timelineVBox;
    private StackPane veilPanes;
    private Person person;
    private VBox allPosts;
    private ScrollPane container;

    private List<Post> posts = new ArrayList<>();

    /**
     * Timeline takes a Person and formats their posts nicely into a box
     * @param person Person for the timeline to be built upon
     */
    public Timeline(Person person) {
        super();
        timelineVBox = new VBox();
        veilPanes = new StackPane();
        veilPanes.setPickOnBounds(false);
        Rectangle line = new Rectangle(800, 2, Color.BLACK);

        allPosts = new VBox();

        allPosts.setStyle("-fx-background-color: #D3D3D3;");
        allPosts.getChildren().add(line);

        container = new ScrollPane();
        container.setMaxWidth(380);
        container.setMinWidth(380);
        container.setMaxHeight(322);
        container.setMinHeight(322);
        container.setContent(allPosts);

        this.person = person;

        Label title = new Label(String.format("%s's Timeline", person.getName()));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        BorderPane b = new BorderPane(title);
        timelineVBox.getChildren().addAll(b, container);
        this.getChildren().addAll(timelineVBox, veilPanes);
    }

    /**
     * Adds a post to the timeline directly
     * @param post Post to be added
     */
    public void addPost(Post post) {
        posts.add(post);
        HBox box = new HBox(post.timelineView());
        // When clicked on, the post enlarges into how it is normally displayed by the Post class
        box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Scene scene = new Scene(post.clone());
            Stage stage = new Stage();
            Profile.addVeil(stage, post.getHint());
            stage.setTitle(String.format("%s's Post", person.getName()));
            stage.setScene(scene);
            stage.show();
            event.consume();
        });

        Rectangle line = new Rectangle(800, 2, Color.BLACK);
        allPosts.getChildren().addAll(box, line);
    }
}