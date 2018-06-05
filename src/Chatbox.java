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

public class Chatbox extends StackPane {
    private VBox submitSection;
    private ScrollPane chatbox;
    private VBox chatContent;
    private Map<String, Map<String, String>> responder;
    private StackPane veilPanes;
    private StackPane questionsLeft;
    private HBox leftNumber;
    private String left;
    private int levelNum;
    private Photo toPinboard;
    private ArrayList<HBox> text;
    private Hint hint;

    private String predator;

    public Chatbox(String file, int levelNum, String left, ArrayList<HBox> text) {
        super();
        veilPanes = new StackPane();
        veilPanes.setPickOnBounds(false);
        VBox layoutAll = new VBox();
        this.left = left;
        this.levelNum = levelNum;
        this.text = text;
        predator = "";

        initSubmitSection(file);
        initChatbox();
        questionsLeft = new StackPane();
        questionsLeft.setPickOnBounds(false);
        questionsLeft.setPadding(new Insets(650, 0, 0, 450));
        if (left.equals("0")) {
            Button endLevel = new Button("End Level");
            endLevel.setOnAction(e -> {
            }); // end of level
            questionsLeft.getChildren().add(endLevel);
        } else {
            leftNumber = new HBox();
            leftNumber.getChildren().add(new Photo("screens/QuestionsLeft.png"));
            Text t = new Text(this.left);
            t.setFont(new Font(25));
            leftNumber.getChildren().add(t);
            questionsLeft.getChildren().add(leftNumber);
        }
        HBox topButtons = new HBox(18);

        StackPane sp = new StackPane();
        sp.setPadding(new Insets(8, 0, 0, 0));
        toPinboard = new Photo("buttons/ToPinboard.png");
        sp.getChildren().add(toPinboard);
        toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Main.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));

        Photo sound = new Photo("buttons/sound/SoundButton.png");
        Photo soundOff = new Photo("buttons/sound/SoundOff.png");
        sound.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            topButtons.getChildren().set(2, soundOff);
        });
        soundOff.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.start();
            topButtons.getChildren().set(2, sound);
        });

        Photo help = new Photo("buttons/help/HelpButton.png", 40, 40);
        StackPane helpWindow = new StackPane();
        StackPane exit1 = new StackPane();
        exit1.setPickOnBounds(false);
        exit1.setPadding(new Insets(10, 211 - 24, 386 - 24, 10));
        Scene helpScene = new Scene(helpWindow);
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.setResizable(false);
        helpStage.setWidth(211);
        helpStage.setHeight(386);
        helpStage.initStyle(StageStyle.UNDECORATED);
        Photo profileHelp = new Photo("buttons/chatbox/ChatHelp.png");
        Photo closeButton = new Photo("buttons/ExitButton.png");
        exit1.getChildren().add(closeButton);

        helpWindow.getChildren().add(profileHelp);
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
        Photo closeButton2 = new Photo("buttons/ExitButton.png");
        exit2.getChildren().add(closeButton2);

        homeWindow.getChildren().add(new Photo("buttons/HomeMenu.png"));
        homeWindow.getChildren().add(exit2);
        homeWindow.getChildren().add(menuBox);

        help.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.show());
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> helpStage.close());

        home.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.show());
        closeButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> hwStage.close());

        toMainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            BackgroundMusic.stop();
            Main.setStage(new Scene(new MainMenu()), 550, 500);
        });
        quitGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.exit(0));

        addVeil(hwStage);
        addVeil(helpStage);

        topButtons.getChildren().add(new Photo("buttons/chatbox/ChatTitle.png"));
        topButtons.getChildren().add(sp);
        if (!BackgroundMusic.isPlaying()) {
            topButtons.getChildren().add(soundOff);
        } else {
            topButtons.getChildren().add(sound);
        }
        topButtons.getChildren().add(help);
        topButtons.getChildren().add(home);
        if (left.equals("0")) {
            layoutAll.getChildren().addAll(topButtons, chatbox);
        } else {
            layoutAll.getChildren().addAll(topButtons, chatbox, submitSection);
        }
        this.getChildren().addAll(layoutAll, questionsLeft, veilPanes);
    }

    private void initSubmitSection(String file) {
        responder = new HashMap<>();
        submitSection = new VBox(15);
        submitSection.setMinHeight(75);
        // Person / list of questions to ask
        Map<String, List<String>> questions = new HashMap<>();
        String line;
        String name = "";
        String currLevel = file.substring(23, 29);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
            System.out.println("ERROR ERRRR MA GURD");
        }

        ComboBox questionChoice = new ComboBox();
        questionChoice.setPromptText("Message Options");

        ComboBox personChoice = new ComboBox();
        for (Map.Entry<String, List<String>> entry : questions.entrySet()) {
            personChoice.getItems().add(entry.getKey());
        }
        personChoice.setPromptText("Select a person to question");
        personChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Object selectedPerson = personChoice.getValue();
                    if (selectedPerson != null) {
                            questionChoice.setItems(FXCollections.observableArrayList(
                                    questions.get(selectedPerson.toString()))
                            );
                    }
                }
        );

        Button submit = new Button("Enter message");
        submit.setOnAction(event -> {
            if (questionChoice.getValue() != null && personChoice.getValue() != null) {

                addMessage("You", questionChoice.getValue().toString());
                addMessage(
                        personChoice.getValue().toString(),
                        responder.get(personChoice.getValue().toString()).get(questionChoice.getValue().toString())
                );
            }
            if (!left.equals("1")) {
                try {
                    left = Integer.toString(Integer.parseInt(left) - 1);
                } catch (NumberFormatException e) {
                }
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Main.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));
                Text t = new Text(left);
                t.setFont(new Font(25));
                leftNumber.getChildren().set(1, t);
            } else {
                try {
                    left = Integer.toString(Integer.parseInt(left) - 1);
                } catch (NumberFormatException e) {
                }
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Main.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));
                submitSection.getChildren().removeAll(personChoice, questionChoice, submit);
                this.getChildren().remove(submitSection);
                // add suspicion in text
                Button endLevel = new Button("End Level");
                endLevel.setOnAction(e -> {
                    StackPane root = new StackPane();
                    StackPane namesPane = new StackPane();
                    namesPane.setPickOnBounds(false);
                    namesPane.setPadding(new Insets(130, 0, 0, 30));
                    VBox names = new VBox(25);
                    names.setPickOnBounds(false);
                    root.getChildren().add(new Photo("screens/QuestionsUp.png"));
                    Photo nameButton;
                    for (Map.Entry<String, List<String>> entry : questions.entrySet()) {
                        nameButton = new Photo("buttons/name_buttons2/"+entry.getKey()+"Button.png");
                        nameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e2 -> {
                            BackgroundMusic.stop();
                            if (entry.getKey().equals(predator))
                                FinalResults.addWin();
                            else
                                FinalResults.addLoss();
                            switch (levelNum) {
                                case 2:
                                    Main.setStage(new Scene(new Briefing3()), 550, 500);
                                    break;
                                case 4:
                                    Main.setStage(new Scene(new Briefing5()), 550, 500);
                                    break;
                                case 5:
                                    Main.setStage(new Scene(new Briefing6()), 550, 500);
                                    break;
                            }
                        });
                        names.getChildren().add(nameButton);
                    }
                    namesPane.getChildren().add(names);
                    root.getChildren().add(namesPane);
                    Main.setStage(new Scene(root), 550, 500);
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

    public void addMessage(String name, String message) {
        if (message.length() > 4 && message.substring(message.length() - 4).equals(".jpg")
                ) {
            Photo photo = new Photo(String.format(
                    "levels/level%s/%s", levelNum, message
            ));
            addMessage(name, photo);
        } else {
            HBox line = new HBox(30);

            Text personName = new Text(name + ":");
            personName.setFont(new Font(20));
            if (personName.getText().equals("You:")) {
                personName.setFill(Color.BLUE);
                Text chatMessage = new Text(message);
                chatMessage.setFont(new Font(20));
                chatMessage.setFill(Color.BLACK);
                chatMessage.setWrappingWidth(450);

                line.getChildren().addAll(personName, chatMessage);
                chatContent.getChildren().add(line);
                text.add(line);
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Main.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));
            } else {
                personName.setFill(Color.RED);
                Text chatMessage = new Text(message);
                chatMessage.setFont(new Font(20));
                chatMessage.setFill(Color.BLACK);
                chatMessage.setWrappingWidth(450);

                line.getChildren().addAll(personName, chatMessage);
                chatContent.getChildren().add(line);
                text.add(line);
                toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Main.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));

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
                Photo yes = new Photo("buttons/YesButton.png");
                Photo no = new Photo("buttons/NoButton.png");
                buttons.getChildren().addAll(yes, no);
                yesNo.setBottom(buttons);
                reportWin.getChildren().addAll(new Photo("buttons/report/ReportWindow.png"), yesNo);

                personName.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.show());

                yes.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    stage.close();
                    BackgroundMusic.stop();
                    if (personName.getText().equals(predator + ":"))
                        FinalResults.addWin();
                    else
                        FinalResults.addLoss();
                    switch (levelNum) {
                        case 2:
                            Main.setStage(new Scene(new Briefing3()), 550, 500);
                            break;
                        case 4:
                            Main.setStage(new Scene(new Briefing5()), 550, 500);
                            break;
                        case 5:
                            Main.setStage(new Scene(new Briefing6()), 550, 500);
                            break;
                    }
                });

                no.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.close());
                addVeil(stage);
            }
        }
    }

    public void addMessage(String name, Photo photo) {
        HBox line = new HBox(30);

        Text personName = new Text(name + ":");
        personName.setFont(new Font(20));
        personName.setFill(Color.RED);

        Photo chatPhoto = new Photo(photo.getLink(), 100);

        line.getChildren().addAll(personName, chatPhoto);
        chatContent.getChildren().add(line);
        text.add(line);
        toPinboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Main.setStage(new Pinboard(levelNum, "chat", left, text), 750, 600));
    }

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