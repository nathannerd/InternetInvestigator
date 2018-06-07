package classes;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Runner extends Application {

    public static Stage pStage;
    public static Group root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        pStage = primaryStage;
        root = new Group();

        pStage.setTitle("Internet Investigator");
        pStage.setScene(new Scene(root));
        pStage.setHeight(550);
        pStage.setWidth(500);
        pStage.setResizable(false);

        /*root.getChildren().add(new MainMenu());
        root.getChildren().add(new Splashscreen());*/
        root.getChildren().add(new Briefing6());
        pStage.show();
    }

    public static void setStage(Scene s, int height, int width)
    {
        pStage.setScene(s);
        pStage.setHeight(height);
        pStage.setWidth(width);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
