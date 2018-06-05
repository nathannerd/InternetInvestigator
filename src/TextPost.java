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

public class TextPost extends Post {
    TextArea text;
    private Text numLikes;

    public TextPost (Date postingDate, TextArea text) {
        super(postingDate);
        this.text = text;

        StackPane stackPane = new StackPane();

        Rectangle dateBox = new Rectangle();
        dateBox.setFill(Color.WHITESMOKE);
        dateBox.setWidth(150);
        dateBox.setHeight(60);

        Text date = new Text(super.formatDate());
        date.setFont(new Font(25));
        date.setWrappingWidth(150);

        Rectangle likeBox = new Rectangle();
        likeBox.setFill(Color.WHITESMOKE);
        likeBox.setWidth(100);
        likeBox.setHeight(40);

        Photo likeImage = new Photo("buttons/facebook_like.png", 40);
        likeImage.setAlignment(Pos.BOTTOM_LEFT);

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

        StackPane.setAlignment(likeBox, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(dateBox, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(date, Pos.BOTTOM_RIGHT);
        stackPane.getChildren().addAll(text, likeBox, likeImage, dateBox, date);

        this.getChildren().add(stackPane);
    }

    public TextArea getText() {
        return text;
    }

    @Override
    public HBox timelineView() {
        HBox view = new HBox();

        // Rectangle object is made, and sized with the given photoHeight
        Rectangle rect = new Rectangle(500, 100, Color.LIGHTGRAY);

        // Text object is made. Its content is the given caption
        Text text = new Text();
        text.setWrappingWidth(400);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setText(this.text.getCaption());
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // VBox contains the text and is aligned at the top left
        VBox vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.TOP_LEFT);

        // StackPane contains both the rectangle and the VBox
        StackPane content = new StackPane();
        content.getChildren().addAll(rect, vBox);
        view.setMinWidth(400);
        view.getChildren().addAll(content);
        return view;
    }

    @Override
    public void addComment(Person person, String comment) {
        text.addComment(person, comment);
    }

    @Override
    public void addComments(Person[] people, String[] comments) {
        text.addComments(people, comments);
    }
}