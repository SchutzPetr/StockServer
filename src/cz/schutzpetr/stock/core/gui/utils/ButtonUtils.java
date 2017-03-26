package cz.schutzpetr.stock.core.gui.utils;

import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * Created by Petr Schutz on 06.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ButtonUtils {

    private static String style_IMG = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5; ";
    private static String styleOnPressed_IMG = "-fx-background-color: rgba(20, 198, 220, 0.20); -fx-padding: 6 4 4 6;";
    private static String styleOnReleased_IMG = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private static String styleOnEntered_IMG = "-fx-background-color: rgba(20, 198, 220, 0.20); -fx-padding: 5, 5, 5, 5;";
    private static String styleOnExpired_IMG = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";

    private static String style = "-fx-background-color: #07A0C3;";
    private static String styleOnPressed = "-fx-background-color: rgba(20, 198, 240, 0.50); -fx-padding: 6 4 4 6;";
    private static String styleOnReleased = "-fx-background-color: #07A0C3; -fx-padding: 5, 5, 5, 5;";
    private static String styleOnEntered = "-fx-background-color: rgba(20, 198, 240, 0.50); -fx-padding: 5, 5, 5, 5;";
    private static String styleOnExpired = "-fx-background-color: #07A0C3; -fx-padding: 5, 5, 5, 5;";

    private ButtonUtils() {
    }

    /**
     * This method sets button as image button.
     *
     * @param button  instance of {@code Button} class
     * @param imgPath path to image
     */
    @SuppressWarnings("CssUnknownTarget")
    public static void setupImageButton(Button button, String imgPath) {
        setupImageButton(button, imgPath, style_IMG, styleOnPressed_IMG, styleOnReleased_IMG, styleOnEntered_IMG, styleOnExpired_IMG);
    }

    /**
     * This method sets button as image button.
     *
     * @param button          instance of {@code Button} class
     * @param imgPath         path to image
     * @param style           default style
     * @param styleOnPressed  style - on mouse pressed
     * @param styleOnReleased style - on mouse released
     * @param styleOnEntered  style - on mouse entered
     * @param styleOnExpired  style - on mouse exited
     */
    @SuppressWarnings("CssUnknownTarget")
    public static void setupImageButton(Button button, String imgPath, String style, String styleOnPressed, String styleOnReleased, String styleOnEntered, String styleOnExpired) {
        String url = "-fx-graphic: url(" + imgPath + ");";
        Platform.runLater(() -> {
            button.setStyle(url + style);

            button.setOnMousePressed(arg0 -> button.setStyle(url + styleOnPressed));

            button.setOnMouseReleased(event -> button.setStyle(url + styleOnReleased));

            button.setOnMouseEntered(arg0 -> button.setStyle(url + styleOnEntered));

            button.setOnMouseExited(event -> button.setStyle(url + styleOnExpired));
        });
    }

    /**
     * This method sets button as image button.
     *
     * @param button          instance of {@code Button} class
     * @param style           default style
     * @param styleOnPressed  style - on mouse pressed
     * @param styleOnReleased style - on mouse released
     * @param styleOnEntered  style - on mouse entered
     * @param styleOnExpired  style - on mouse exited
     */
    public static void setupButton(Button button, String style, String styleOnPressed, String styleOnReleased, String styleOnEntered, String styleOnExpired) {
        Platform.runLater(() -> {
            button.setStyle(style);

            button.setOnMousePressed(arg0 -> button.setStyle(styleOnPressed));

            button.setOnMouseReleased(event -> button.setStyle(styleOnReleased));

            button.setOnMouseEntered(arg0 -> button.setStyle(styleOnEntered));

            button.setOnMouseExited(event -> button.setStyle(styleOnExpired));
        });
    }

    /**
     * This method sets button as image button.
     *
     * @param button instance of {@code Button} class
     */
    @SuppressWarnings("CssUnknownTarget")
    public static void setupButton(Button button) {
        setupButton(button, style, styleOnPressed, styleOnReleased, styleOnEntered, styleOnExpired);
    }
}
