package omega;

import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private final Image omegaImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/sebastian.png")));
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private Stage stage;
    private Omega omega;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Duke instance
     */
    public void setOmega(Omega o) {
        omega = o;
    }

    /**
     * Injects the wrapping Stage
     */
    public void setStage(Stage s) {
        stage = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = omega.getResponse(input).trim();
        boolean isError = omega.checkError();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, null),
                DialogBox.getOmegaDialog(response, omegaImage, isError)
        );
        userInput.clear();

        // AI: write transition delay for exit request
        if (omega.isExitRequested()) {
            PauseTransition delay = new PauseTransition(Duration.millis(350));
            delay.setOnFinished(e -> stage.close());
            delay.play();
        }
    }
}
