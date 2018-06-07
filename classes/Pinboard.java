package classes;

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
 * Description: Added veils and changed help section. Also made pinboard work with chatbox. Added top resources.buttons.
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
 * Description: Edited assets for the pinboard and added them all, with formatting. Made resources.sound work.
 * Time spent: 2 hours
 */
public class Pinboard extends Scene {

    private List<Person> persons;
    private StackPane veilPanes;
    private String left; // questions left in chatbox

    /**
     * Creates a new Pinboard
     * @param levelNum could be 1-6 inclusive
     * @param levelType could be "profile" or "chatbox". Each type is made completely differently and this difference
     *                  is dealt with inside of this constructor
     * @param leftNum number of questions left in a chatbox level
     * @param text the text that had already been written in chatbox
     */
    public Pinboard(int levelNum, String levelType, String leftNum, ArrayList<HBox> text)
    {
        super(new Group()); // call to parent constructor

        // If the level type is profile, then new LevelBuilder is make which constructs a profile level type
        if (levelType.equals("profile")) {
            LevelBuilder levelBuilder = new LevelBuilder("src/level_description/level" + levelNum + ".txt");
            persons = levelBuilder.getPersons();
        }

        veilPanes = new StackPane();
        left = leftNum;

        // stackPane is the main screen of a Pinboard
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(0, 0, 0, 0));
        stackPane.getChildren().add(new Photo("resources/screens/Pinboard.png", 723, 601));

        // the top row of resources.buttons is created
        StackPane topButtonsLay = new StackPane();
        topButtonsLay.setPadding(new Insets(50, 0, 0, 400));
        GridPane topButtons = new GridPane();
        Photo sound = new Photo("resources/buttons/sound/SoundButton.png");
        Photo soundOff = new Photo("resources/buttons/sound/SoundOff.png");

        // if resources.sound button is clicked: stop music and change to soundOff button
        sound.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            topButtons.getChildren().set(0, soundOff);
        });

        // if soundOff button is clicked: start music again and change to resources.sound button
        soundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.start();
            topButtons.getChildren().set(0, sound);
        });

        // help section created
        Photo help = new Photo("resources/buttons/help/HelpButton.png");
        StackPane helpWindow = new StackPane();
        StackPane body1 = new StackPane();
        StackPane exit1 = new StackPane();
        exit1.setPadding(new Insets(10, 211-24, 263-24, 10));

        // scene for help window enlarging is created
        Scene helpScene = new Scene(helpWindow);
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.setResizable(false);
        helpStage.setWidth(211);
        helpStage.setHeight(263);
        helpStage.initStyle(StageStyle.UNDECORATED);

        // different help resources.screens are displayed depending on level type
        if (levelType.equals("profile"))
            body1.getChildren().add(new Photo("resources/buttons/help/PinHelpProf.png"));
        else
            body1.getChildren().add(new Photo("resources/buttons/help/PinHelp.png"));

        // exit button is created
        Photo closeButton = new Photo("resources/buttons/ExitButton.png");
        exit1.getChildren().add(closeButton);

        // body1 and exit1 are added to help window
        helpWindow.getChildren().addAll(body1, exit1);

        // home button is created
        Photo home = new Photo("resources/buttons/HomeButton.png", 40, 40);
        StackPane homeWindow = new StackPane();
        StackPane exit2 = new StackPane();
        exit2.setPadding(new Insets(5, 129, 62, 5));

        // scene for home button enlargement is created
        Scene hwScene = new Scene(homeWindow);
        Stage hwStage = new Stage();
        hwStage.setTitle("Home");
        hwStage.setScene(hwScene);
        hwStage.setResizable(false);
        hwStage.setWidth(153);
        hwStage.setHeight(86);
        hwStage.initStyle(StageStyle.UNDECORATED);

        // menu box displays: Runner Menu and Quit
        // this is created below in a VBox
        VBox menuBox = new VBox();
        menuBox.setPadding(new Insets(30, 0, 0, 0));
        Photo toMainMenu = new Photo("resources/buttons/mainmenu/ToMainMenu.png");
        Photo quitGame = new Photo("resources/buttons/QuitGameB.png");
        menuBox.getChildren().add(toMainMenu);
        menuBox.getChildren().add(quitGame);
        menuBox.setPickOnBounds(false);
        Photo closeButton2 = new Photo("resources/buttons/ExitButton.png");
        exit2.getChildren().add(closeButton2);

        // homeMenu photo, exit2 and menuBox are added to the homeWindow
        homeWindow.getChildren().addAll(new Photo("resources/buttons/HomeMenu.png"), exit2, menuBox);

        // shows helpStage upon clicking help button
        help.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> { helpStage.show(); });

        // helpStage closes upon clicking close button
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.close());

        // shows hwStage upon clicking home
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> { hwStage.show(); });

        addVeil(helpStage);
        addVeil(hwStage);

        // closes hwStage upon clicking closeButton2
        closeButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.close());

        // screen changes to MainMenu upon clicking toMainMenu
        toMainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            Runner.setStage(new Scene(new MainMenu()), 550, 500);
        });

        // program finishes upon clicking quitGame
        quitGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            System.exit(0);
        });

        topButtons.setHgap(10);
        if (!BackgroundMusic.isPlaying()) {
            topButtons.add(soundOff, 0, 0);
        }
        else {
            topButtons.add(sound, 0, 0);
        }
        topButtons.add(help, 1, 0);
        topButtons.add(home, 2, 0);

        topButtonsLay.getChildren().add(topButtons);

        /* The contents of the left sheet of paper on the pinboard is defined in the following if else statement
           If the level type is profile, suspects are pasted on the sheet of paper and display their respective
           profile pages are displayed upon clicking the name.
           If the level type is chatbox, the left sheet of paper simply shows "TO THE CHAT". The user is taken to
           the chatbox when they click "TO THE CHAT"
         */
        if (levelType.equals("profile")) {
            StackPane nameButtonsLay = new StackPane();
            nameButtonsLay.setPadding(new Insets(325, 0, 0, 50));
            VBox nameButtons = new VBox(-7);
            Photo pButton;
            for (Person p : persons) {
                // There is an image of each character's name in the folder resources.buttons/name_buttons/
                pButton = new Photo("resources/buttons/name_buttons/" + p.getName() + "Button.png");
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
        else {
            StackPane goToChat = new StackPane();
            goToChat.setPadding(new Insets(325, 0, 0, 70));
            Photo toChat = new Photo("resources/buttons/chatbox/ToChat.png"); // This photo displays text saying
                                                                         // "TO THE CHAT"
            toChat.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.setRoot(new Chatbox(
                    "src/level_description/level" + levelNum + ".txt", levelNum, left, text)
            ));
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

    /**
     * The passed in stage is highlighted and all other stages are dimmed in color and are unclickable
     * If hint text is not an empty string, a bottom window is also highlighted with a hint from brother
     * @param stage stage to be highlighted
     * @param hintText brother's insight
     */
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
