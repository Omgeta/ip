package omega;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    private static final int IMAGE_SIZE = 80;
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        applyCircularCrop();
        if (img == null) {
            displayPicture.setVisible(false);
            displayPicture.setManaged(false);
        }
    }

    /**
     * Returns DialogBox for a user input.
     *
     * @param text User input text
     * @param img  Image shown next to reply text
     * @return Styled DialogBox for User input
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setUser();
        return db;
    }

    /**
     * Returns DialogBox for Omega's response
     * If response was an error, the DialogBox is styled differently
     *
     * @param text    Omega response message
     * @param img     Image shown next to reply text
     * @param isError If response was an error message
     * @return Styled DialogBox for Omega response
     */
    public static DialogBox getOmegaDialog(String text, Image img, boolean isError) {
        var db = new DialogBox(text, img);
        if (!isError) {
            db.setReply();
        } else {
            db.setError();
        }
        return db;
    }

    /**
     * Applies styles for replies from User.
     */
    private void setUser() {
        setAlignment(Pos.BOTTOM_RIGHT);
        dialog.getStyleClass().add("user-bubble");
    }

    /**
     * Applies styles for normal replies from Omega.
     */
    private void setReply() {
        setOmega();
        dialog.getStyleClass().add("reply-bubble");
    }

    /**
     * Applies styles for error replies from Omega.
     */
    private void setError() {
        setOmega();
        dialog.getStyleClass().add("error-bubble");
    }

    /**
     * Applies styles for replies from Omega.
     */
    private void setOmega() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.BOTTOM_LEFT);
    }

    /**
     * Applies circular crop to displayPicture
     */
    private void applyCircularCrop() {
        // AI: used to generate code for crop method
        displayPicture.setFitWidth(DialogBox.IMAGE_SIZE);
        displayPicture.setFitHeight(DialogBox.IMAGE_SIZE);
        displayPicture.setPreserveRatio(true);
        displayPicture.setSmooth(true);

        Circle clip = new Circle(
                (double) DialogBox.IMAGE_SIZE / 2, (double) DialogBox.IMAGE_SIZE / 2,
                (double) DialogBox.IMAGE_SIZE / 2);
        displayPicture.setClip(clip);

        // Keep clip correct even if fit size changes later
        displayPicture.fitWidthProperty().addListener((obs, o, n) -> {
            double s = Math.min(displayPicture.getFitWidth(), displayPicture.getFitHeight());
            clip.setCenterX(s / 2);
            clip.setCenterY(s / 2);
            clip.setRadius(s / 2);
        });
        displayPicture.fitHeightProperty().addListener((obs, o, n) -> {
            double s = Math.min(displayPicture.getFitWidth(), displayPicture.getFitHeight());
            clip.setCenterX(s / 2);
            clip.setCenterY(s / 2);
            clip.setRadius(s / 2);
        });
    }
}
