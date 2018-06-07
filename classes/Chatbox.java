package classes;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nathan Henry and Jake Goodman
 * @since 2018-05-23
 * @version 4 (current version)
 *
 * Version 4:
 * Author: Nathan Henry
 * Date: 2018-06-2
 * Description: Added function to report players by clicking on their names, and program flows to proceeding levels
 *              after reporting someone
 * Time spent: 2.5 hours
 *
 * Version 3:
 * Author: Nathan Henry
 * Date: 2018-05-30
 * Description: Added all top resources.buttons and can move freely between chatbox display and pinboard
 * Time spent: 1 hour
 *
 * Version 2:
 * Author: Jake Goodman
 * Date: 2018-05-25
 * Description: Now can display photos and the formatting of the level looks much nicer. The combo boxes all
 *              sized properly and no errors are present
 * Time spent: 2.5 hours
 *
 * Version 1:
 * Author: Jake Goodman
 * Date: 2018-05-23
 * Description: Reads from a file and displays questions for each described person, and that person will respond
 *              with the specified answer
 * Time spent: 1 hour
 */
public class Chatbox extends StackPane {
    private VBox submitSection;
    private ScrollPane chatbox;
    private VBox chatContent;
    private Map<String, Map<String, String>> responder; // map -> personName : [map -> question : answer]
    private StackPane veilPanes;
    private StackPane questionsLeft;
    private HBox leftNumber;
    private String left;
    private int levelNum;
    private Photo toPinboard;
    private ArrayList<HBox> text;
    private Hint hint;
    private String predator;

    /**
     * Chatbox level template
     * This class works similar to LevelBuilder in that it reads from a level description file and builds the level
     * accordingly.
     * A chatbox level is built to model a group chat between characters in the level
     * The layout is a StackPane and you can ask questions to all other players to figure out who is most likely
     * to be an internet predator.
     * @param file link to level description file
     * @param levelNum number of the level
     * @param left questions left
     * @param text text already in the chatbox
     */
    public Chatbox(String file, int levelNum, String left, ArrayList<HBox> text) {
        // call to parent's default constructor
        super();
        veilPanes = new StackPane();
        veilPanes.setPickOnBounds(false);
        VBox layoutAll = new VBox();
        this.left = left;
        this.levelNum = levelNum;
        this.text = text;
        predator = ""; // predator is set to empty string by default

        // method which initializes the bottom section of a chatbox
        initSubmitSection(file);

        // method which initializes the default view of a chatbox
        initChatbox();

        questionsLeft = new StackPane();
        questionsLeft.setPickOnBounds(false);
        questionsLeft.setPadding(new Insets(650, 0, 0, 450));

        // If there are "0" questions left, the user is not given the option to continue to question the other
        // characters and must report somebody.
        // Else, questions left is displayed
        if (left.equals("0")) {
            Button endLevel = new Button("End Level");
            endLevel.setOnAction(e -> {}); // end of level
            questionsLeft.getChildren().add(endLevel);
        } else {
            leftNumber = new HBox();
            leftNumber.getChildren().add(new Photo("resources/screens/QuestionsLeft.png"));
            Text t = new Text(this.left);
            t.setFont(new Font(25));
            leftNumber.getChildren().add(t);
            questionsLeft.getChildren().add(leftNumber);
        }
        HBox topButtons = new HBox(18);

        // To Pinboard button
        StackPane sp = new StackPane();
        sp.setPadding(new Insets(8, 0, 0, 0));
        toPinboard = new Photo("resources/buttons/ToPinboard.png");
        sp.getChildren().add(toPinboard);

        // Upon clicking toPinboard, the stage is reset to a new Pinboard
        toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Runner.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));

        // Sound resources.buttons to turn resources.sound on and off
        Photo sound = new Photo("resources/buttons/sound/SoundButton.png");
        Photo soundOff = new Photo("resources/buttons/sound/SoundOff.png");

        // Upon clicking resources.sound, background music stops and button is switched to soundOff
        sound.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            topButtons.getChildren().set(2, soundOff);
        });

        // Upon clicking soundOff, background music restarts and button is switched to resources.sound
        soundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.start();
            topButtons.getChildren().set(2, sound);
        });

        // help section
        Photo help = new Photo("resources/buttons/help/HelpButton.png", 40, 40);
        StackPane helpWindow = new StackPane();
        StackPane exit1 = new StackPane();
        exit1.setPickOnBounds(false);
        exit1.setPadding(new Insets(10, 211 - 24, 386 - 24, 10));

        // stage to be displayed when helpButton is clicked, created below
        Scene helpScene = new Scene(helpWindow);
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.setResizable(false);
        helpStage.setWidth(211);
        helpStage.setHeight(386);
        helpStage.initStyle(StageStyle.UNDECORATED);
        Photo profileHelp = new Photo("resources/buttons/help/ChatHelp.png");
        Photo closeButton = new Photo("resources/buttons/ExitButton.png");
        exit1.getChildren().add(closeButton);

        helpWindow.getChildren().addAll(profileHelp, exit1);

        // home section
        Photo home = new Photo("resources/buttons/HomeButton.png", 40, 40);
        StackPane homeWindow = new StackPane();
        StackPane exit2 = new StackPane();
        exit2.setPadding(new Insets(5, 129, 62, 5));

        // stage to be displayed when home is clicked, created below
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
        Photo toMainMenu = new Photo("resources/buttons/mainmenu/ToMainMenu.png");
        Photo quitGame = new Photo("resources/buttons/QuitGameB.png");
        menuBox.getChildren().add(toMainMenu);
        menuBox.getChildren().add(quitGame);
        menuBox.setPickOnBounds(false);
        Photo closeButton2 = new Photo("resources/buttons/ExitButton.png");
        exit2.getChildren().add(closeButton2);

        homeWindow.getChildren().addAll(new Photo("resources/buttons/HomeMenu.png"), exit2, menuBox);

        // Upon clicking help, helpStage is shown
        help.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.show());

        // Upon clicking closeButton, helpStage is shown
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.close());

        // Upon clicking home, hwStage is shown
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.show());

        // Upon clicking closeButton2, hwStage is shown
        closeButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.close());

        // Upon clicking toMainMenu, background music terminates and the main stage is set to a new MainMenu
        toMainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            Runner.setStage(new Scene(new MainMenu()), 550, 500);
        });

        // Upon clicking quitGame, program terminates
        quitGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.exit(0));

        addVeil(hwStage);
        addVeil(helpStage);

        topButtons.getChildren().addAll(new Photo("resources/buttons/chatbox/ChatTitle.png"), sp);

        if (!BackgroundMusic.isPlaying()) {
            topButtons.getChildren().add(soundOff);
        } else {
            topButtons.getChildren().add(sound);
        }

        topButtons.getChildren().addAll(help, home);

        // if there are zero questions, do not add the submit section to the layout
        if (left.equals("0")) {
            layoutAll.getChildren().addAll(topButtons, chatbox);
        } else {
            layoutAll.getChildren().addAll(topButtons, chatbox, submitSection);
        }
        this.getChildren().addAll(layoutAll, questionsLeft, veilPanes);
    }

    /**
     * Initializes the submit section of the chatbox level
     * @param file level description file
     */
    private void initSubmitSection(String file) {
        responder = new HashMap<>();
        submitSection = new VBox(15);
        submitSection.setMinHeight(75);

        // map -> person : list of questions to ask them
        Map<String, List<String>> questions = new HashMap<>();
        String line;
        String name = "";
        String currLevel = file.substring(23, 29);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // while there are lines to read in the file
            while ((line = br.readLine()) != null) {
                switch (Chatbox.numOfTabs(line)) {
                    case 0:
                        if (line.equals("")) continue;
                        name = line.substring(0, line.indexOf(','));
                        if (line.substring(line.indexOf(' '), line.indexOf(':')).equals(" Predator"))
                            predator = name;
                        questions.put(name, new ArrayList<>());
                        responder.put(name, new HashMap<>());
                        break;
                    case 1:
                        String question = line.substring(0, line.indexOf('|'));
                        String answer = line.substring(line.indexOf('|') + 1);
                        questions.get(name).add(question);
                        responder.get(name).put(question, answer);
                        break;
                    default:
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        ComboBox questionChoice = new ComboBox();
        questionChoice.setPromptText("Message Options");

        // personChoice is populated with all people in the level
        ComboBox personChoice = new ComboBox();
        for (Map.Entry<String, List<String>> entry : questions.entrySet()) {
            personChoice.getItems().add(entry.getKey());
        }
        personChoice.setPromptText("Select a person to question");

        // questionChoice is dynamically populated. When personChoice is clicked, questionChoie is populated
        // with possible questions for that person
        personChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Object selectedPerson = personChoice.getValue();
                    if (selectedPerson != null) {
                        questionChoice.setItems(FXCollections.observableArrayList(
                                questions.get(selectedPerson.toString()))
                        );
                    }
                }
        );

        // submit button
        Button submit = new Button("Enter message");

        // Upon clicking submit, the following code is executed
        submit.setOnAction(event -> {
            // Only process the information if personChoice and questionChoice are both not null
            if (questionChoice.getValue() != null && personChoice.getValue() != null) {
                // If the following expression evaluates to null, then the user did not double click on the person
                // choice. The code inside of it statement displays an error box, telling the user to remember to
                // double click the person.
                // Else, display the corresponding answer to the selected question
                if (responder.get(personChoice.getValue().toString()).get(questionChoice.getValue()) == null) {
                    StackPane root = new StackPane();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setWidth(150);
                    stage.setHeight(150);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.UNDECORATED);

                    StackPane exit1 = new StackPane();
                    exit1.setPickOnBounds(false);
                    exit1.setPadding(new Insets(10, 150 - 24, 150 - 24, 10));
                    Photo exitButton = new Photo("resources/buttons/ExitButton.png");
                    exit1.getChildren().add(exitButton);

                    root.getChildren().add(new Photo("resources/screens/ChatError.png"));
                    root.getChildren().add(exit1);
                    exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                        stage.close();
                    });
                    addVeil(stage);

                    stage.show();
                    left = Integer.toString(Integer.parseInt(left) + 1);
                } else {
                    addMessage("You", questionChoice.getValue().toString());
                    addMessage(
                            personChoice.getValue().toString(),
                            responder.get(personChoice.getValue().toString()).get(questionChoice.getValue().toString())
                    );
                }
            } else if (questionChoice.getValue() == null) left = Integer.toString(Integer.parseInt(left) + 1);

            // If the number of questions left is not 1, simply decrease the number of questions left and update
            // the toPinboard node's event handler property
            if (!left.equals("1")) {
                try {
                    left = Integer.toString(Integer.parseInt(left) - 1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Upon clicking toPinboard, reset the main stage to new Pinboard with updated number of questions left
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                        Runner.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600)
                );
                Text t = new Text(left);
                t.setFont(new Font(25));
                leftNumber.getChildren().set(1, t);
            } else {
                try {
                    left = Integer.toString(Integer.parseInt(left) - 1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                // Upon clicking toPinboard, reset the main stage to new Pinboard with updated number of questions left
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                        Runner.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600)
                );

                // clear out submitSection
                submitSection.getChildren().removeAll(personChoice, questionChoice, submit);
                this.getChildren().remove(submitSection);

                // When the user runs out of questions, the submit section disappears and only a single button is
                // on display, which says "end level".
                Button endLevel = new Button("End Level");

                // Following code executed upon clicking endLevel
                endLevel.setOnAction(e -> {
                    StackPane root = new StackPane();
                    StackPane namesPane = new StackPane();
                    namesPane.setPickOnBounds(false);
                    namesPane.setPadding(new Insets(130, 0, 0, 30));
                    VBox names = new VBox(25);
                    names.setPickOnBounds(false);
                    root.getChildren().add(new Photo("resources/screens/QuestionsUp.png"));
                    Photo nameButton;

                    // Iterating through each person in the level
                    for (Map.Entry<String, List<String>> entry : questions.entrySet()) {
                        nameButton = new Photo("resources/buttons/name_buttons2/" + entry.getKey() + "Button.png");

                        // Upon clicking on a character's name: add or take away a win depending on if the usaer
                        // selected the correct person, and proceed to the next level
                        nameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> {
                            BackgroundMusic.stop();
                            if (entry.getKey().equals(predator))
                                FinalResults.addWin();
                            else
                                FinalResults.addLoss();
                            switch (levelNum) {
                                case 2:
                                    Runner.setStage(new Scene(new Briefing3()), 550, 500);
                                    break;
                                case 4:
                                    Runner.setStage(new Scene(new Briefing5()), 550, 500);
                                    break;
                                case 5:
                                    Runner.setStage(new Scene(new Briefing6()), 550, 500);
                                    break;
                            }
                        });
                        names.getChildren().add(nameButton);
                    }
                    namesPane.getChildren().add(names);
                    root.getChildren().add(namesPane);
                    Runner.setStage(new Scene(root), 550, 500);
                }); // end of level
                questionsLeft.getChildren().set(0, endLevel);
                return;
            }
        });

        submitSection.getChildren().addAll(personChoice, questionChoice, submit);
        HBox.setMargin(personChoice, new Insets(40, 20, 40, 20));
        HBox.setMargin(questionChoice, new Insets(40, 20, 40, 20));
        HBox.setMargin(submit, new Insets(40, 20, 40, 20));

        for (Map.Entry<String, List<String>> entry : questions.entrySet()) {
            System.out.printf("");
        }
    }

    /**
     * Finds the number of tabs at the start of a String.
     * Used for verifying formatting rules in level description files.
     * The number of tabs is the number of spaces mod 4
     * @param line line to be analyzed
     * @return number of tabs
     */
    public static int numOfTabs(String line) {
        int spaces = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                spaces++;
            } else {
                if (spaces % 4 == 0)
                    return spaces / 4;
                else
                    throw new IllegalArgumentException(String.format(
                            "Invalid file formatting on this line: %s", line
                    ));
            }
        }
        // if the method reaches this point, line is a blank line
        return -1;
    }

    /**
     * Initializes upper section of this window, where the text will eventually be displayed
     */
    private void initChatbox() {
        chatbox = new ScrollPane();
        chatContent = new VBox(10);
        for (HBox line : text) {
            chatContent.getChildren().add(line);
        }
        chatbox.setMinHeight(500);
        chatbox.setMaxHeight(500);
        chatbox.setMinWidth(600);
        chatbox.setMaxWidth(600);
        chatbox.setContent(chatContent);
    }

    /**
     * Adds a message to the chatbox.
     * message could be a link to a photo. If it is a photo, then the photo is displayed and no text is displayed
     * @param name name of the person giving the message
     * @param message content of their message, either words or an image
     */
    public void addMessage(String name, String message) {
        // if the line ends in ".jpg", it represents the link to a photo
        if (message.length() > 4 && message.substring(message.length() - 4).equals(".jpg")) {
            Photo photo = new Photo(String.format(
                    "levels/level%s/%s", levelNum, message
            ));
            addMessage(name, photo);
        } else {
            HBox line = new HBox(30);

            Text personName = new Text(name + ":");
            personName.setFont(new Font(20));

            // The name of the messenger is displayed in red unless the message comes from the user, in which case
            // it's blue
            if (personName.getText().equals("You:")) {
                personName.setFill(Color.BLUE);
                Text chatMessage = new Text(message);
                chatMessage.setFont(new Font(20));
                chatMessage.setFill(Color.BLACK);
                chatMessage.setWrappingWidth(450);

                line.getChildren().addAll(personName, chatMessage);
                chatContent.getChildren().add(line);
                text.add(line);
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Runner.setStage(
                        new Pinboard(levelNum, "chat", left, text),
                        750, 600)
                );
            } else {
                personName.setFill(Color.RED);
                Text chatMessage = new Text(message);
                chatMessage.setFont(new Font(20));
                chatMessage.setFill(Color.BLACK);
                chatMessage.setWrappingWidth(450);

                line.getChildren().addAll(personName, chatMessage);
                chatContent.getChildren().add(line);
                text.add(line);
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Runner.setStage(
                        new Pinboard(levelNum, "chat", left, text),
                        750, 600
                ));

                // To report players in this level format, you must click on their names. The following code
                // sets up the player the opportunity to report a player
                StackPane reportWin = new StackPane();
                Scene scene = new Scene(reportWin);
                Stage stage = new Stage();
                stage.setTitle("Report");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setWidth(235);
                stage.setHeight(107);
                stage.initStyle(StageStyle.UNDECORATED);
                BorderPane yesNo = new BorderPane();
                yesNo.setPickOnBounds(false);
                HBox buttons = new HBox(20);
                buttons.setPadding(new Insets(0, 0, 5, 25));
                buttons.setPickOnBounds(false);
                Photo yes = new Photo("resources/buttons/YesButton.png");
                Photo no = new Photo("resources/buttons/NoButton.png");
                buttons.getChildren().addAll(yes, no);
                yesNo.setBottom(buttons);
                reportWin.getChildren().addAll(new Photo("resources/buttons/report/ReportWindow.png"), yesNo);

                // display the newly created stage upon clicking personName
                personName.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.show());

                // If they click yes, add either a win or a loss proceed to the next level
                yes.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    stage.close();
                    BackgroundMusic.stop();
                    if (personName.getText().equals(predator + ":"))
                        FinalResults.addWin();
                    else
                        FinalResults.addLoss();
                    switch (levelNum) {
                        case 2:
                            Runner.setStage(new Scene(new Briefing3()), 550, 500);
                            break;
                        case 4:
                            Runner.setStage(new Scene(new Briefing5()), 550, 500);
                            break;
                        case 5:
                            Runner.setStage(new Scene(new Briefing6()), 550, 500);
                            break;
                    }
                });

                // If they click no, continue gameplay as normal
                no.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.close());
                addVeil(stage);
            }
        }
    }

    /**
     * Adds a message to the chatbox if the message content is a photo
     * @param name name of the messanger
     * @param photo Photo to display
     */
    public void addMessage(String name, Photo photo) {
        HBox line = new HBox(30);

        Text personName = new Text(name + ":");
        personName.setFont(new Font(20));
        personName.setFill(Color.RED);

        Photo chatPhoto = new Photo(photo.getLink(), 100);

        line.getChildren().addAll(personName, chatPhoto);
        chatContent.getChildren().add(line);
        text.add(line);
        toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Runner.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));

        // To report players in this level format, you must click on their names. The following code
        // sets up the player the opportunity to report a player
        StackPane reportWin = new StackPane();
        Scene scene = new Scene(reportWin);
        Stage stage = new Stage();
        stage.setTitle("Report");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setWidth(235);
        stage.setHeight(107);
        stage.initStyle(StageStyle.UNDECORATED);
        BorderPane yesNo = new BorderPane();
        yesNo.setPickOnBounds(false);
        HBox buttons = new HBox(20);
        buttons.setPadding(new Insets(0, 0, 5, 25));
        buttons.setPickOnBounds(false);
        Photo yes = new Photo("resources/buttons/YesButton.png");
        Photo no = new Photo("resources/buttons/NoButton.png");
        buttons.getChildren().addAll(yes, no);
        yesNo.setBottom(buttons);
        reportWin.getChildren().addAll(new Photo("resources/buttons/report/ReportWindow.png"), yesNo);

        // display the newly created stage upon clicking personName
        personName.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.show());

        // If they click yes, add either a win or a loss proceed to the next level
        yes.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            stage.close();
            BackgroundMusic.stop();
            if (personName.getText().equals(predator + ":"))
                FinalResults.addWin();
            else
                FinalResults.addLoss();
            switch (levelNum) {
                case 2:
                    Runner.setStage(new Scene(new Briefing3()), 550, 500);
                    break;
                case 4:
                    Runner.setStage(new Scene(new Briefing5()), 550, 500);
                    break;
                case 5:
                    Runner.setStage(new Scene(new Briefing6()), 550, 500);
                    break;
            }
        });

        // If they click no, continue gameplay as normal
        no.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.close());
        addVeil(stage);
    }

    /**
     * The passed in stage is highlighted and all other stages are dimmed in color and are unclickable
     * If hint text is not an empty string, a bottom window is also highlighted with a hint from brother
     * @param stage stage to be highlighted
     */
    private void addVeil(Stage stage) {
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