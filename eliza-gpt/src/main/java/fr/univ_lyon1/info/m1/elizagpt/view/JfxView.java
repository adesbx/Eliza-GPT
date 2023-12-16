package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.Filter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class of the View (GUI) of the application,
 * Using Observer to know when a message as been sent.
 */
public class JfxView implements Observer {
    private VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private MessageList messageList = null;
    private Controller ctrl = null;

    private Filter filter = null;

    //
    private boolean darkMode = false;
    static final String DARK_STYLE =
            "-fx-background-color: #413939;";

    static final String LIGHT_STYLE =
            "-fx-background-color: #ece9e9;";

    static final String ORANGE_STYLE =
            "-fx-background-color: #ff720e;";

    static final String DARK_STYLE_SEARCHBAR =
            "-fx-background-color: #524f4f;";

    static final String LIGHT_STYLE_SEARCHBAR =
            "-fx-background-color: #fdfafa;";

    static final String LIGHT_COLOR =
            "-fx-text-fill: #ece9e9;";

    static final String DARK_COLOR =
            "-fx-text-fill: #000000;";


    private VBox root = null;
    //
    /**
     * Create the main view of the application.
     */
    public JfxView(
            final Stage stage,
            final int width,
            final int height,
            final MessageList newMessageList,
            final Controller newCtrl
    ) {
        stage.setTitle("Eliza GPT");

        ctrl = newCtrl;
        messageList = newMessageList;
        // De base le filtre sera Substring(défini lors de la méthode createSearchWidget)
        filter = null;

        root = new VBox(10);

        final Pane search = createSearchWidget();
        root.getChildren().add(search);

        ScrollPane dialogScroll = new ScrollPane();
        dialog = new VBox(10);

        dialogScroll.setContent(dialog);
        // scroll to bottom by default:
        dialogScroll.vvalueProperty().bind(dialog.heightProperty());
        root.getChildren().add(dialogScroll);
        dialogScroll.setFitToWidth(true);

        final Pane input = createInputWidget();
        root.getChildren().add(input);

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();

        messageList.addObserver(this);
    }

    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    @Override
    public void update() {
        printAllMessage();
        //printLastMessage();
        //System.out.println("update from observer");
    }

    private void printMessage(final Message message) {
        HBox hBox = new HBox();
        HBox hBox1 = new HBox();
        final Label label = new Label(message.getMessage());
        final Button button = new Button("x");
        hBox.getChildren().add(hBox1);
        hBox1.getChildren().add(label);
        hBox1.getChildren().add(button);
        if (message.getIsFromEliza()) {
            hBox1.setStyle(USER_STYLE);
            hBox.setAlignment(Pos.BASELINE_LEFT);
        } else {
            hBox1.setStyle(ELIZA_STYLE);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
        }
        dialog.getChildren().add(hBox);
        button.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
            ctrl.removeMessage(message.getId());
            // besoin de recharger l'affiche de tout les messages
        });
    }

    /**
     * Le dernier message.
     */
    private void printLastMessage() {
        Message message = messageList.pullLastMessage();
        printMessage(message);
    }

    /**
     * tout les messages.
     */
    private void printAllMessage() {
        messageList = ctrl.getMessageList();
        dialog.getChildren().removeAll(dialog.getChildren());
        for (Message message : messageList.pullAllMessage()) {
            printMessage(message);
        }
    }

    /**
     * Construction de la phrase utilisateur.
     *
     * @param text
     */
    private void buttonSend(final String text) {
        ctrl.treatMessage(text);
    }

    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        final HBox thirdLine = new HBox();


        searchText = new TextField();
        searchText.setOnAction(e -> {
            searchText(searchText);
        });
        ComboBox<Filter> comboBox = new ComboBox<>();
        ObservableList<Filter> list = getFilterList();
        comboBox.setItems(list);
        comboBox.setOnAction(event -> {
            filter = comboBox.getValue();
        });
        comboBox.setMinWidth(75);
        comboBox.setMaxWidth(75);
        comboBox.setStyle(ORANGE_STYLE);
        firstLine.getChildren().addAll(searchText, comboBox);
        firstLine.setAlignment(Pos.CENTER);

        final Button send = new Button("Search");
        send.setOnAction(e -> {
            searchText(searchText);
        });
        send.setStyle(ORANGE_STYLE);
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {
            //printAllMessage();
            undoSearch();
            searchTextLabel.setText("");
        });
        undo.setStyle(ORANGE_STYLE);
        final Button switchMode = new Button("Mode");
        switchMode.setOnAction(e -> { //click sur button
            updateMode();
        });
        switchMode.setStyle(ORANGE_STYLE);
        secondLine.getChildren().addAll(searchTextLabel);
        secondLine.setAlignment(Pos.CENTER);

        thirdLine.getChildren().addAll(send, undo, switchMode);
        thirdLine.setAlignment(Pos.CENTER);
        thirdLine.setSpacing(20);
        thirdLine.setPadding(new Insets(20));

        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine, thirdLine);
        return input;
    }

    private void searchText(final TextField text) {

        String currentSearchText = text.getText();

//        Pattern pattern;
//        Matcher matcher;
//        pattern = Pattern.compile(currentSearchText, Pattern.CASE_INSENSITIVE);

        if (currentSearchText == null) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + currentSearchText);
        }

        if (filter != null) {
            ctrl.filterMessage(currentSearchText, filter);
        } else {
            System.out.println("Aucune méthode utilisé");
        }
        text.setText("");
    }

    /**
     * undo the actual search.
     */
    public void undoSearch() {
        ctrl.undoFilter();
    }

    private HBox createInputWidget() {
        final HBox input = new HBox();
        text = new TextField();
        text.setOnAction(e -> { // entrer sur le texte
            buttonSend(text.getText());
            text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> { //click sur button
            buttonSend(text.getText());
            text.setText("");
        });
        send.setStyle(ORANGE_STYLE);
        input.setAlignment(Pos.CENTER);
        input.getChildren().addAll(text, send);
        return input;
    }


    /**
     * Update mode from dark to light and reverse.
     */
    public void updateMode() {
        darkMode = !darkMode;
        if (darkMode) {
            root.setStyle(DARK_STYLE);
            dialog.setStyle(DARK_STYLE);
            text.setStyle(DARK_STYLE_SEARCHBAR);
            searchText.setStyle(DARK_STYLE_SEARCHBAR);
            searchTextLabel.setStyle(LIGHT_COLOR);
        } else {
            root.setStyle(LIGHT_STYLE);
            dialog.setStyle(LIGHT_STYLE);
            text.setStyle(LIGHT_STYLE_SEARCHBAR);
            searchText.setStyle(LIGHT_STYLE_SEARCHBAR);
            searchTextLabel.setStyle(DARK_COLOR);
        }
    }

    /**
     * Create a ObservableList of Filter.
     */
    public ObservableList<Filter> getFilterList() {
        ObservableList<Filter> list
                = FXCollections.observableArrayList(ctrl.getFilterList());

        return list;
    }
}
