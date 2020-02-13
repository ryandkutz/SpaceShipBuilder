/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nurivan
 */
public class MainMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void startGame(ActionEvent event) {
        Group root = new Group();
        Stage stage = new Stage();
        stage.setTitle("Have a Good Launch!");
        stage.setScene(new Scene(root, 450, 450));
        stage.show();
        // Hide this current window (if this is what you want)
        ((Node)(event.getSource())).getScene().getWindow().hide();
        final Canvas canvas = new Canvas(450,450);
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue());
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue());
        });
        root.getChildren().add(canvas);
        Game g = new Game(canvas);
        g.loop();
    }
    
    public void startBuilder(ActionEvent event) {
        
    }
    
}
