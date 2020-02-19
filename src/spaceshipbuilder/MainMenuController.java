/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nurivan
 */
public class MainMenuController implements Initializable {
    @FXML
    CheckBox FullScreen;
    
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
        Scene scene = new Scene(root, 800 , 600);
        stage.setTitle("Have a Good Launch!");
        stage.setScene(scene);
        if(FullScreen.isSelected()) {
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        }
        stage.show();
        // Hide this current window (if this is what you want)
        //((Node)(event.getSource())).getScene().getWindow().hide();
        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);
        Game g = new Game(canvas);
        g.loop();
    }
    
    public void startBuilder(ActionEvent event) {
        
    }
    
}
