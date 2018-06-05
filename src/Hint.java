import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Hint extends StackPane {

    public Hint(String hint)
    {
        super();
        this.setAlignment(Pos.BOTTOM_LEFT);
        this.setPadding(new Insets(0, 0, 40, 40));
        StackPane hintPane = new StackPane();
        hintPane.setMaxHeight(120);
        hintPane.setMaxWidth(240);
        hintPane.setAlignment(Pos.TOP_LEFT);
        Rectangle box = new Rectangle();
        box.setHeight(80);
        box.setWidth(200);
        box.setFill(Color.WHITE);
        box.setStroke(Color.BLACK);
        hintPane.getChildren().add(box);
        this.getChildren().add(hintPane);
        Text t = new Text("Brother: " + hint);
        StackPane.setMargin(t, new Insets(2, 2, 2, 2));
        t.setFont(new Font(16));
        t.setWrappingWidth(195);
        hintPane.getChildren().add(t);
    }
}
