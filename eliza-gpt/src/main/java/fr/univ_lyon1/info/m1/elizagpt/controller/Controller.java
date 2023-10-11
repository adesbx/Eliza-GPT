package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;

/**
 * Main class of the Controller (GUI) of the application.
 */
public class Controller {

    JfxView viewPtr;

    /**
     * constructeur du controleur avec l'instance de la vue
     * @param view
     */
    public Controller(JfxView view){
        viewPtr=view;
    }

    /**
     * detection de l'action utilisateur
     */
    private final void actionButton(){

    }
}
