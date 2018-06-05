import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nathan Henry
 * @since 2018-05-23
 * @version 3 (current version)
 *
 * Version 3:
 * Author: Nathan Henry
 * Date: 2018-05-26
 * Description: Added veils and changed help section. Also made pinboard work with chatbox. Added top buttons.
 * Time spent: 3 hour
 *
 * Version 2:
 * Author: Nathan Henry
 * Date: 2018-05-24
 * Description: Made pinboard compatible with the profile class
 * Time spent: 3 hours
 *
 * Version 1:
 * Author: Nathan Henry
 * Date: 2018-05-23
 * Description: Edited assets for the pinboard and added them all, with formatting. Made sound work.
 * Time spent: 2 hours
 */
public class Pinboard extends Scene {

    private List<Person> persons;
    private StackPane veilPanes;
    private String left; // questions left in chatbox

    public Pinboard(int levelNum, String levelType, String leftNum, ArrayList<HBox> text)
    {
        super(new Group());
        if (levelType.equals("profile")) {
            LevelBuilder levelBuilder = new LevelBuilder("src/level_description/level" + levelNum + ".txt");
            persons = levelBuilder.getPersons();
        }
        veilPanes = new StackPane();
        left = leftNum;

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(0, 0, 0, 0));
        stackPane.getChildren().add(new Photo("buttons/Pinboard.png", 723, 601));

        StackPane topButtonsLay = new StackPane();
        topButtonsLay.setPadding(new Insets(50, 0, 0, 400));
        GridPane topButtons = new GridPane();
        Photo sound = new Photo("buttons/sound/SoundButton.png");
        Photo soundOff = new Photo ("buttons/sound/SoundOff.png");
        sound.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            topButtons.getChildren().set(0, soundOff);
        });
        soundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.start();
            topButtons.getChildren().set(0, sound);
        });
        Photo help = new Photo("buttons/help/HelpButton.png");
        StackPane helpWindow = new StackPane();
        StackPane body1 = new StackPane();
        StackPane exit1 = new StackPane();
        exit1.setPadding(new Insets(10, 211-24, 263-24, 10));
        Scene helpScene = new Scene(helpWindow);
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.setResizable(false);
        helpStage.setWidth(211);
        helpStage.setHeight(263);
        helpStage.initStyle(StageStyle.UNDECORATED);
        if (levelType.equals("profile"))
            body1.getChildren().add(new Photo("buttons/help/PinHelpProf.png"));
        else
            body1.getChildren().add(new Photo("buttons/help/PinHelp.png"));
        Photo closeButton = new Photo ("buttons/ExitButton.png");
        exit1.getChildren().add(closeButton);

        helpWindow.getChildren().add(body1);
        helpWindow.getChildren().add(exit1);

        Photo home = new Photo("buttons/HomeButton.png", 40, 40);
        StackPane homeWindow = new StackPane();
        StackPane exit2 = new StackPane();
        exit2.setPadding(new Insets(5, 129, 62, 5));
        Scene hwScene = new Scene(homeWindow);
        Stage hwStage = new Stage();
        hwStage.setTitle("Home");
        hwStage.setScene(hwScene);
        hwStage.setResizable(false);
        hwStage.setWidth(153);
        hwStage.setHeight(86);
        hwStage.initStyle(StageStyle.UNDECORATED);
        VBox menuBox = new VBox();
        menuBox.setPadding(new Insets(30, 0, 0, 0));
        Photo toMainMenu = new Photo("buttons/mainmenu/ToMainMenu.png");
        Photo quitGame = new Photo("buttons/QuitGameB.png");
        menuBox.getChildren().add(toMainMenu);
        menuBox.getChildren().add(quitGame);
        menuBox.setPickOnBounds(false);
        Photo closeButton2 = new Photo ("buttons/ExitButton.png");
        exit2.getChildren().add(closeButton2);

        homeWindow.getChildren().add(new Photo("buttons/HomeMenu.png"));
        homeWindow.getChildren().add(exit2);
        homeWindow.getChildren().add(menuBox);

        help.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                helpStage.show();
        });
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.close());

        home.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                hwStage.show();
        });

        addVeil(helpStage);
        addVeil(hwStage);

        closeButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.close());

        toMainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            Main.setStage(new Scene(new MainMenu()), 550, 500);
        });
        quitGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            System.exit(0);
        });

        topButtons.setHgap(10);
        if (BackgroundMusic.isPlaying() == false) {
            topButtons.add(soundOff, 0, 0);
        }
        else
        {
            topButtons.add(sound, 0, 0);
        }
        topButtons.add(help, 1, 0);
        topButtons.add(home, 2, 0);

        topButtonsLay.getChildren().add(topButtons);

        if (levelType.equals("profile")) {
            StackPane nameButtonsLay = new StackPane();
            nameButtonsLay.setPadding(new Insets(325, 0, 0, 50));
            VBox nameButtons = new VBox(-7);
            Photo pButton;
            for (Person p : persons) {
                pButton = new Photo("buttons/name_buttons/" + p.getName() + "Button.png");
                pButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    this.setRoot(new Profile(p, levelNum));
                    e.consume();
                });
                nameButtons.getChildren().add(pButton);
            }
            nameButtonsLay.getChildren().add(nameButtons);

            StackPane layer1 = new StackPane();
            layer1.getChildren().addAll(new Rectangle(600, 750, Color.BLACK), stackPane, topButtonsLay, nameButtonsLay);
            StackPane root = new StackPane();
            topButtonsLay.setPickOnBounds(false);
            nameButtonsLay.setPickOnBounds(false);
            veilPanes.setPickOnBounds(false);
            root.getChildren().addAll(layer1, veilPanes);
            this.setRoot(root);
        }
        else
        {
            StackPane goToChat = new StackPane();
            goToChat.setPadding(new Insets(325, 0, 0, 70));
            Photo toChat = new Photo("buttons/ToChat.png");
            toChat.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.setRoot(new Chatbox("src/level_description/level" + levelNum + ".txt", levelNum, left, text)));
            goToChat.getChildren().add(toChat);

            StackPane layer1 = new StackPane();
            layer1.getChildren().addAll(new Rectangle(600, 750, Color.BLACK), stackPane, topButtonsLay, goToChat);
            StackPane root = new StackPane();
            topButtonsLay.setPickOnBounds(false);
            goToChat.setPickOnBounds(false);
            veilPanes.setPickOnBounds(false);
            root.getChildren().addAll(layer1, veilPanes);
            this.setRoot(root);
        }
    }

    public void addVeil(Stage stage)
    {
        StackPane vPane = new StackPane();
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        veil.visibleProperty().bind(stage.showingProperty());
        vPane.getChildren().add(veil);
        vPane.setPickOnBounds(false);
        veilPanes.getChildren().add(vPane);
    }
}
