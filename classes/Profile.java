package classes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Nathan Henry and Jake Goodman
 * @since 2018-05-17
 * @version 4 (current version)
 *
 * Version 4:
 * Author: Jake Goodman
 * Date: 2018-05-28
 * Description: Fixed bugs on timeline and connected all the panes in timeline
 * Time spent: (profile related classes) 4
 *
 * Version 3:
 * Author: Nathan Henry
 * Date: 2018-05-26
 * Description: Added veils and made small modifications to profile.
 * Also added the top resources.buttons (mainmenu, toPinboard, resources.sound, help, and home).
 *
 * Version 2:
 * Author: Jake Goodman
 * Date: 2018-05-20
 * Description: Replaced the number guidelines with parts of a profile (and worked on other profile-related classes)
 * Hours spent: (on all profile related classes) 7 hours
 *
 * Version 1:
 * Author: Nathan Henry
 * Date: 2018-05-17
 * Description: Set the numbers and properties for the sections in the profile page
 * Hours spent: 2 hours
 */
public class Profile extends StackPane {

    private GridPane gridPane;
    private static StackPane veilPanes;
    private static StackPane hintPane;

    /**
     * Created a profile view for a character in a profile level
     * This constructor populates the entire screen
     * @param person person to base the profile off of
     * @param levelNum the level that this person is from
     */
    public Profile(Person person, int levelNum) {
        super();
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(1, 1, 20, 1));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        veilPanes = new StackPane();
        hintPane = new StackPane();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0 && i == 0) {
                    // Report button mechanic
                    StackPane sp = new StackPane();
                    Photo reportButton = new Photo("resources/buttons/report/ReportButton.png");
                    sp.getChildren().add(reportButton);
                    gridPane.add(sp, j, i, 3, 1);
                    GridPane.setMargin(sp, new Insets(15, 0, 0, 25));

                    StackPane reportWin = new StackPane();

                    // Setting up stage for when report button is clicked
                    Scene scene = new Scene(reportWin);
                    Stage stage = new Stage();
                    stage.setTitle("Home");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.setWidth(235);
                    stage.setHeight(107);
                    stage.initStyle(StageStyle.UNDECORATED);
                    BorderPane yesNo = new BorderPane();
                    yesNo.setPickOnBounds(false);

                    // Yes/No resources.buttons in new window
                    HBox buttons = new HBox(20);
                    buttons.setPadding(new Insets(0, 0, 5, 25));
                    buttons.setPickOnBounds(false);
                    Photo yes = new Photo("resources/buttons/YesButton.png");
                    Photo no = new Photo("resources/buttons/NoButton.png");
                    buttons.getChildren().addAll(yes, no);
                    yesNo.setBottom(buttons);
                    reportWin.getChildren().addAll(new Photo("resources/buttons/report/ReportWindow.png"), yesNo);

                    // Show the newly created stage upon clicking reportButton
                    reportButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.show());

                    /*
                      Upon clicking the yes button: add win or loss depending on the correctness of the player's
                      guess, close the stage, reset the stage to the next level
                     */
                    yes.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        BackgroundMusic.stop();
                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter("src/resources/gamesave/WinsLosses.txt"));
                            if (person.isPredator()) {
                                FinalResults.addWin();
                            }
                            else {
                                FinalResults.addLoss();
                            }
                            writer.println(FinalResults.getWins() + " " + FinalResults.getLosses());
                            writer.close();
                        } catch (IOException e2)
                        {
                        }

                        stage.close();
                        switch (levelNum)
                        {
                            case 1:
                                Runner.setStage(new Scene(new Briefing2()), 550, 500);
                                break;
                            case 3:
                                Runner.setStage(new Scene(new Briefing4()), 550, 500);
                                break;
                            case 6:
                                Runner.setStage(new FinalResults(), 550, 500);
                                break;
                        }
                    });

                    // Simply close the stage upon clicking the no button
                    no.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.close());

                    addVeil(stage, "");
                    j += 2;
                } else if (j == 4 && i == 0) {
                    // "To classes.Pinboard" button is set up
                    StackPane sp = new StackPane();
                    Photo toPinboard = new Photo("resources/buttons/ToPinboard.png");
                    sp.getChildren().add(toPinboard);
                    gridPane.add(sp, j, i, 2, 1);
                    GridPane.setMargin(sp, new Insets(15, 0, 0, 0));

                    // Upon clicking toPinboard, the user is directed back to the pinboard
                    toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Runner.setStage(
                            new Pinboard(levelNum,
                                    "profile",
                                    "",
                                    new ArrayList<>()), 750, 600)
                    );
                    j++;
                } else if (j == 7 && i == 0) {
                    // Top resources.buttons (resources.sound, help, and home)
                    GridPane topButtons = new GridPane();
                    topButtons.setPadding(new Insets(10, 0, 0, 0));

                    // resources.sound and soundOff resources.buttons are created
                    Photo sound = new Photo("resources/buttons/sound/SoundButton.png");
                    Photo soundOff = new Photo("resources/buttons/sound/SoundOff.png");

                    // Upon clicking resources.sound button, background music stops and this button is set to the resources.sound off button
                    sound.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        BackgroundMusic.stop();
                        topButtons.getChildren().set(0, soundOff);
                    });

                    /*
                      Upon clicking soundOff button, background music restarts and this button os set back to the
                      resources.sound button
                     */
                    soundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        BackgroundMusic.start();
                        topButtons.getChildren().set(0, sound);
                    });

                    // help button and help screen are created
                    Photo help = new Photo("resources/buttons/help/HelpButton.png", 40, 40);
                    StackPane helpWindow = new StackPane();
                    StackPane exit1 = new StackPane();
                    exit1.setPickOnBounds(false);
                    exit1.setPadding(new Insets(10, 211-24, 423-24, 10));
                    Scene helpScene = new Scene(helpWindow); // helpWindow is populated on line 179
                    Stage helpStage = new Stage();
                    helpStage.setTitle("Help");
                    helpStage.setScene(helpScene);
                    helpStage.setResizable(false);
                    helpStage.setWidth(211);
                    helpStage.setHeight(423);
                    helpStage.initStyle(StageStyle.UNDECORATED);

                    Photo profileHelp = new Photo("resources/buttons/help/ProfileHelp.png"); // This image has helpful
                    // profile related help for the
                    // user to refer to
                    Photo closeButton = new Photo("resources/buttons/ExitButton.png");
                    exit1.getChildren().add(closeButton);

                    helpWindow.getChildren().addAll(profileHelp, exit1);

                    // home button and home stage are created
                    Photo home = new Photo("resources/buttons/HomeButton.png", 40, 40);
                    StackPane homeWindow = new StackPane();
                    StackPane exit2 = new StackPane();
                    exit2.setPadding(new Insets(5, 129, 62, 5));

                    // homeWindow is populated on line 208
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
                    Photo toMainMenu = new Photo("resources/buttons/mainmenu/ToMainMenu.png"); // Text displays To Runner Menu
                    Photo quitGame = new Photo("resources/buttons/QuitGameB.png"); // Text displays Quit

                    menuBox.getChildren().addAll(toMainMenu, quitGame);
                    menuBox.setPickOnBounds(false);
                    Photo closeButton2 = new Photo("resources/buttons/ExitButton.png");
                    exit2.getChildren().add(closeButton2);

                    // homeWindow populated with HomeMenu button, exit2 and the menuBox
                    homeWindow.getChildren().addAll(new Photo("resources/buttons/HomeMenu.png"), exit2, menuBox);

                    // Upon clicking on help, helpStage is shown
                    help.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.show());

                    // Upon clicking closeButton, helpStage closes
                    closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.close());

                    // Upon clicking home, hwStage is shown
                    home.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.show());

                    // Upon clicking closeButton2, hwStage closes
                    closeButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.close());

                    // Upon clicking toMainMenu, background music terminates and stage switches to new MainMenu
                    toMainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        BackgroundMusic.stop();
                        hwStage.close();
                        Runner.setStage(new Scene(new MainMenu()), 550, 500);
                    });

                    // Upon clicking quitGame, program terminates
                    quitGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                    {
                        hwStage.close();
                        System.exit(0);
                    });

                    /*
                      If background music is not playing, soundOff button is displaying.
                      If background music is playing, resources.sound button is displaying
                     */
                    topButtons.setHgap(10);
                    if (!BackgroundMusic.isPlaying()) {
                        topButtons.add(soundOff, 0, 0);
                    }
                    else {
                        topButtons.add(sound, 0, 0);
                    }
                    topButtons.add(help, 1, 0);
                    topButtons.add(home, 2, 0);

                    gridPane.add(topButtons, j, i, 3, 1);
                    j+=2;

                    addVeil(helpStage, "");
                    addVeil(hwStage, "");
                } else if (j == 0 && i == 1) {
                    // Profile picture creation

                    // profilePic is the same photo as person.profilePic but it is smaller
                    Photo profilePic = person.getNewProfilePic(100);

                    // Scene for when profile is clicked is created. The enlarged profilePic will be 3X the size of the
                    // original
                    Scene scene = new Scene(person.getNewProfilePic(300));
                    Stage stage = new Stage();
                    stage.setTitle(String.format("%s's Profile Picture", person.getName()));
                    stage.setScene(scene);
                    stage.setResizable(false);
                    profilePic.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        stage.show();
                        event.consume();
                    });
                    gridPane.add(profilePic, j, i, 3, 2);
                    GridPane.setMargin(profilePic, new Insets(0, 0, 0, 25));
                    addVeil(stage, person.getHints().get("profilePic"));
                    j += 2;
                } else if (j == 3 && i == 1) {
                    // Name
                    Text name = new Text(person.getName());
                    name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
                    gridPane.add(name, j, i, 2, 1);
                    GridPane.setMargin(name, new Insets(15, 40, 0, 0));
                    j++;
                } else if (j == 0 && i == 2) {
                    // not a layout
                    j += 2;
                } else if (j == 3 && i == 2) {
                    // bio
                    Text text;

                    // If bio is more than 47 characters long, it is displayed with trailing dots
                    if (person.getBio().length() < 47) {
                        text = new Text(person.getBio());
                    } else {
                        text = new Text(person.getBio().substring(0, 50) + "...");
                    }

                    text.setFont(new Font(15));
                    VBox bio = new VBox(text);
                    Text t = new Text(person.getBio());
                    t.setWrappingWidth(360);
                    t.setFont(new Font(20));
                    VBox root = new VBox(t);
                    root.setPadding(new Insets(20, 20, 20, 20));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setMaxWidth(400);
                    stage.setTitle("Bio");
                    stage.setScene(scene);
                    stage.setResizable(false);

                    // Upon clicking bio, a new screen with the full bio is displayed
                    bio.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> stage.show());
                    gridPane.add(bio, j, i, 7, 1);
                    GridPane.setMargin(bio, new Insets(0, 40, 0, 0));
                    addVeil(stage, person.getHints().get("bio"));
                    j += 7;
                } else if (j == 0 && i == 4) {
                    // pictures and friends section with dropdown menus
                    Button photos = new Button("Photos");
                    photos.setMinHeight(43 * 5 / 2);
                    photos.setMinWidth(45 * 3 - 20);
                    GridPane root = new GridPane();
                    root.setHgap(20);
                    root.setVgap(20);

                    // If the person has no photos, an image that has the next "No photos" is displayed upon click
                    // Else, their photos are displayed within a new window
                    if (person.getPhotos().size() == 0) {
                        root.add(new Photo("resources/buttons/NoPhotos.png"), 0, 0);
                    } else {
                        int photoIndex = 0;
                        for (int k = 0; k < 3; k++) {
                            for (int m = 0; m < 3; m++) {
                                if (photoIndex < person.getPhotos().size()) {
                                    Photo p = person.getPhotos().get(photoIndex);
                                    root.add(new Photo(p.getLink(), 200), k, m);
                                    photoIndex++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Photos");
                    stage.setScene(scene);
                    stage.setResizable(false);

                    // Newly created stage is displayed upon clicking photos button
                    photos.setOnAction(e -> stage.show());

                    gridPane.add(photos, j, i, 3, 5);
                    GridPane.setMargin(photos, new Insets(10, 10, 10, 25));

                    addVeil(stage, person.getHints().get("photos"));

                    Button friends = new Button("Friends");
                    friends.setMinHeight(43 * 5 / 2);
                    friends.setMinWidth(45 * 3 - 20);

                    VBox list = new VBox();

                    // This scene contains a ListView of all friends of person
                    Scene scene2 = new Scene(list, 300, 500);
                    Stage stage2 = new Stage();
                    stage2.setTitle("Friends");
                    stage2.setScene(scene2);
                    stage2.setResizable(false);

                    // list is populated with all friends of person
                    for (Person p : person.getFriends()) {
                        Button friend = new Button(p.getName());
                        friend.setMinWidth(300);
                        friend.setMinHeight(100);
                        friend.setOnAction(e -> {
                            Runner.setStage(new Scene(new Profile(p, levelNum)), 750, 600);
                            stage2.close();
                        });
                        list.getChildren().add(friend);
                    }

                    // stage2 is displayed upon clicking friends
                    friends.setOnAction(e -> stage2.show());

                    gridPane.add(friends, j, i + 6, 3, 5);
                    GridPane.setMargin(friends, new Insets(10, 10, 10, 25));
                    addVeil(stage2, person.getHints().get("friends"));
                    j += 10;
                    i += 10;
                } else if (j == 3 && i == 3) {
                    // timeline
                    gridPane.add(person.getTimeline(), j, i, 7, 8);
                    GridPane.setMargin(person.getTimeline(), new Insets(20, 40, 70, 0));
                    j += 7;
                } else {
                    // not a layout
                    white(45, 43, j, i, 1, 1);
                }
            }
        }

        StackPane pPane = new StackPane();
        pPane.setPadding(new Insets(135, 0, 10, 150));
        pPane.getChildren().add(gridPane);
        veilPanes.setPickOnBounds(false);
        hintPane.setPickOnBounds(false);
        this.getChildren().addAll(pPane, veilPanes, hintPane);
    }

    /**
     * For formatting purposes only
     * Adds a while box to gridPane with given dimensions and informations
     * @param width width of the white box
     * @param height height of the white box
     * @param colI column index
     * @param rowI row index
     * @param colSpan column span
     * @param rowSpan row span
     */
    private void white(int width, int height, int colI, int rowI, int colSpan, int rowSpan) {
        gridPane.add(new Rectangle(width, height, Color.WHITESMOKE), colI, rowI, colSpan, rowSpan);
    }

    /**
     * The passed in stage is highlighted and all other stages are dimmed in color and are unclickable
     * If hint text is not an empty string, a bottom window is also highlighted with a hint from brother
     * @param stage stage to be highlighted
     * @param hintText brother's insight
     */
    public static void addVeil(Stage stage, String hintText)
    {
        StackPane vPane = new StackPane();
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        veil.setVisible(false);
        veil.visibleProperty().bind(stage.showingProperty());
        vPane.getChildren().add(veil);
        vPane.setPickOnBounds(false);
        veilPanes.getChildren().add(vPane);

        if (!hintText.equals("")) {
            StackPane hPane = new StackPane();
            Hint hint = new Hint(hintText);
            hint.visibleProperty().bind(stage.showingProperty());
            hPane.getChildren().add(hint);
            hPane.setPickOnBounds(false);
            hintPane.getChildren().add(hPane);
        }
    }
}