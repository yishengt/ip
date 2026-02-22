package tyrone;

import java.awt.*;
import java.io.IOException;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tyrone.MainWindow;
import tyrone.Tyrone;
import javafx.scene.image.Image;
import tyrone.exception.TyroneException;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Tyrone tyrone;
    private Image userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaUser.jpeg")));
    private Image tyroneImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaTyrone.jpeg")));



    @FXML
    private TextField userInput;

    @FXML
    private VBox dialogContainer; // or ListView<String> for output


    public void setTyrone(Tyrone tyrone) {
        this.tyrone = tyrone;
    }



    @Override
    public void start(Stage stage) {
        try {
            tyrone = new Tyrone("data/tyrone.txt");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTyrone(tyrone);  // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws TyroneException {
        Application.launch(args);
    }

    @FXML
    private void handleUserInput() throws TyroneException{
        String input = userInput.getText();
        if (input.isBlank()) return;

        String response = tyrone.processCommand(input); // you need this method in Tyrone

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, tyroneImage)
        );

    }


}
