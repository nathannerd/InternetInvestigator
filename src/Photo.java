import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author Jake Goodman
 * @since 2018-05-14
 * @version 1
 *
 * Time spent: 4 hours
 */
public class Photo extends HBox {
    private ImageView imageView;
    private Image image;
    private TextArea textArea;
    private String link;

    /**
     * Most basic contructor for Photo
     * Assigns given String to link
     * image initialized to new Image with given link
     * imageView initialized to new ImageView with newly created Image
     * Adds the image view to this object, which is an HBox
     * @param link URL of the photo
     */
    public Photo(String link) {
        super();
        this.link = link;
        image = new Image(link);
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        this.getChildren().addAll(imageView);
    }

    /**
     * Same function as the basic constructor
     * Sets the height to a given height
     * @param link URL of the photo
     * @param height height of the photo
     */
    public Photo(String link, int height) {
        super();
        this.link = link;
        image = new Image(link);
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        this.getChildren().addAll(imageView);
    }

    /**
     * Same function as the basic constructor
     * Sets the height and width to a given height/width
     * @param link URL of the photo
     * @param height height of the photo
     * @param width width of the photo
     */
    public Photo(String link, int height, int width) {
        super();
        this.link = link;
        image = new Image(link);
        imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        this.getChildren().addAll(imageView);
    }

    /**
     * Same function as the basic constructor
     * The third parameter signifies that the second parameter refers to width, and not height
     * A quick but ugly fix to this problem!
     * @param link URL of the photo
     * @param width width of the photo
     * @param setWidth signifies that int parameter refers to width and not height
     */
    public Photo(String link, int width, boolean setWidth) {
        super();
        this.link = link;
        image = new Image(link);
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        this.getChildren().addAll(imageView);
    }

    /**
     * Same function as the basic constructor
     * @param link URL of the photo
     * @param height height of the photo
     * @param caption caption associated with the photo
     */
    public Photo(String link, int height, String caption) {
        super();
        this.link = link;
        image = new Image(link);
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        this.textArea = new TextArea(caption, imageView.getFitHeight());
        this.getChildren().addAll(imageView, this.textArea);
    }

    /**
     * Getter for link
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * Getter for textArea
     * @return textArea
     */
    public TextArea getTextArea() {
        return textArea;
    }

    /**
     * Adds a comment to the photo by calling the add comment method on TextArea
     * @param person the person who left the comment
     * @param comment the comment
     */
    public void addComment(Person person, String comment) {
        textArea.addComment(person, comment);
    }

    /**
     * This would never be used on a real social media website but it makes implementing parts of our game
     * much easier
     * This method has the exact same function as addComment, but allows us to add multiple comments to a photo
     * by passing an array of Persons and an array of comments
     * @param people people who left the comments
     * @param comments comments that the people made
     */
    public void addComments(Person[] people, String[] comments) {
        textArea.addComments(people, comments);
    }
}