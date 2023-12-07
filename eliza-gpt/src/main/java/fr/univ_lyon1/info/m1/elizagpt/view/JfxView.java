package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageList;
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
    private final VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private MessageList messageList = null;
    private Controller ctrl = null;

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

        final VBox root = new VBox(10);

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

        ctrl = newCtrl;
        messageList = newMessageList;
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
        final Label label = new Label(message.getMessage());
        hBox.getChildren().add(label);
        if (message.getIsFromEliza()) {
            label.setStyle(USER_STYLE);
            hBox.setAlignment(Pos.BASELINE_LEFT);
        } else {
            label.setStyle(ELIZA_STYLE);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
        }
        dialog.getChildren().add(hBox);
        hBox.setOnMouseClicked(e -> {
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
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);
        searchText = new TextField();
        searchText.setOnAction(e -> {
            searchText(searchText);
        });
        firstLine.getChildren().add(searchText);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Option 1", "Option 2");
        firstLine.getChildren().add(comboBox);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            searchText(searchText);
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {
            //printAllMessage();
            undoSearch();
            searchTextLabel.setText("");
        });
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
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
        ctrl.filterMessage(currentSearchText);
//        List<HBox> toDelete = new ArrayList<>();
//        for (Node hBox : dialog.getChildren()) {
//            for (Node label : ((HBox) hBox).getChildren()) {
//                String t = ((Label) label).getText();
//                matcher = pattern.matcher(t);
//                if (!matcher.matches()) {
//                    // Can delete it right now, we're iterating over the list.
//                    toDelete.add((HBox) hBox);
//                }
//            }
//        }
//        dialog.getChildren().removeAll();
        text.setText("");
    }

    /**
     * undo the actual search.
     *
     */
    public void undoSearch() {
        ctrl.undoFilter();
    }

    private Pane createInputWidget() {
        final Pane input = new HBox();
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
        input.getChildren().addAll(text, send);
        return input;
    }
}
