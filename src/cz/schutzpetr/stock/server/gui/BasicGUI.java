package cz.schutzpetr.stock.server.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Petr Schutz on 26.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class BasicGUI<T> {
    /**
     * Instance of {@code Stage}
     */
    private final Stage stage;
    /**
     * Instance of {@code Stage}
     */
    private final T controller;


    /**
     * Creates instance of basic GUI with given param.
     *
     * @param title        pane title
     * @param width        width
     * @param height       height
     * @param resizable    is resizable
     * @param fxmlLocation path to fxml file
     * @param imgLocation  path to pane icon
     * @param modality     is modality
     */
    @SuppressWarnings("unchecked")
    BasicGUI(String title, int width, int height, boolean resizable, String fxmlLocation, String imgLocation, Modality modality) {

        this.stage = new Stage();

        Parent root;
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlLocation));
            root = loader.load();

            stage.setTitle(title);
            stage.getIcons().add(new Image(imgLocation));
            stage.setScene(new Scene(root, width, height));
            stage.setResizable(resizable);
            stage.initModality(modality);

            controller = loader.getController();

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.controller = (T) controller;
    }

    /**
     * This method closes this scene
     */
    void closeStage() {
        this.stage.close();
    }

    /**
     * @return param controller
     */
    public T getController() {
        return controller;
    }
}
