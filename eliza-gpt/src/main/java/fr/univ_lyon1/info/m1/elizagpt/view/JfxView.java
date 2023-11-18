package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    private final Random random = new Random();

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
        messageList.addObserver(this);

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
    }

    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;


    @Override
    public void update() {
        replyToUser();
        //System.out.println("update from observer");
    }

    /**
     * La réponse de eliza.
     */
    private void replyToUser() {
        Message message = messageList.pullLastResponse();
        HBox hBox = new HBox();
        final Label label = new Label(message.getMessage());
        hBox.getChildren().add(label);
        label.setStyle(USER_STYLE);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        dialog.getChildren().add(hBox);
        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
            ctrl.removeMessage(message.getId());
        });
    }

    /**
     * Construction de la phrase utilisateur.
     *
     * @param text
     */
    private void buttonSend(final Message text) {
        //création de la phrase utilisateur
        HBox hBox = new HBox();
        final Label label = new Label(text.getMessage());
        hBox.getChildren().add(label);
        label.setStyle(ELIZA_STYLE);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        dialog.getChildren().add(hBox);

        //fonctionnalité pour supprimer le message
        hBox.setOnMouseClicked(e -> {
            dialog.getChildren().remove(hBox);
            ctrl.removeMessage(text.getId());
        });
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
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            searchText(searchText);
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {

        });
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }

    private void undoSearchText(final TextField text) {
        //parcourir la list des messages

        //si c'est un user faire:
        // String style = "USER_STYLE";
        // Pos position = Pos.BASELINE_LEFT;
        //sinon c'est le robot:
        // String style = "ELIZA_STYLE";
        // Pos position = Pos.BASELINE_RIGHT;

        //faire :

//        HBox hBox = new HBox();
//        final Label label = new Label(text);
//        hBox.getChildren().add(label);
//        label.setStyle(ELIZA_STYLE);
//        hBox.setAlignment(Pos.BASELINE_RIGHT);
//        dialog.getChildren().add(hBox);
//
//        for (int i = 0; i < messageList.getSize(); i++) {
//        }
    }

    private void searchText(final TextField text) {

        String currentSearchText = text.getText();

        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(currentSearchText, Pattern.CASE_INSENSITIVE);

        if (currentSearchText == null) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + currentSearchText);
        }
        List<HBox> toDelete = new ArrayList<>();
        for (Node hBox : dialog.getChildren()) {
            for (Node label : ((HBox) hBox).getChildren()) {
                String t = ((Label) label).getText();
                matcher = pattern.matcher(t);
                if (!matcher.matches()) {
                    // Can delete it right now, we're iterating over the list.
                    toDelete.add((HBox) hBox);
                }
            }
        }
        dialog.getChildren().removeAll(toDelete);
        text.setText("");
    }

    private Pane createInputWidget() {
        final Pane input = new HBox();
        text = new TextField();
        text.setOnAction(e -> {
            buttonSend(new Message(text.getText(), true, messageList.getSize() + 1));
            text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> {
            buttonSend(new Message(text.getText(), false, messageList.getSize() + 1));
            text.setText("");
        });
        input.getChildren().addAll(text, send);
        return input;
    }
}
