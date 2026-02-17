package omega;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final Omega omega = new Omega();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setOmega(omega);
            fxmlLoader.<MainWindow>getController().setStage(stage);

            stage.setOnCloseRequest(omega::shutdown);
            stage.setTitle("Omega");
            stage.getIcons().add(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))
            ));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
