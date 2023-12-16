package fr.univ_lyon1.info.m1.elizagpt;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        MessageList msgList = new MessageList();
        MessageProcessor msgProc = new MessageProcessor(msgList);
        Controller ctrl = new Controller(msgProc, msgList);
        JfxView view = new JfxView(stage, 600, 600, msgList, ctrl);
        // Second view (uncomment to activate)
        new JfxView(new Stage(), 400, 400, msgList, ctrl);
    }
}
