/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import spaceshipbuilder.parts.Engine;
import spaceshipbuilder.parts.FuelTank;
import spaceshipbuilder.parts.ShipPart;

/**
 * FXML Controller class
 *
 * @author Nurivan
 */
public class BuilderController implements Initializable {
    
    Spaceship ship;
    ShipPart part;
    @FXML private BorderPane bp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ship = new Spaceship(5, 5, "");
        part = new ShipPart();
        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {
                ship.addPart(r, c, part);
            }
        }
    }
    
    public void addPart(MouseEvent e) {
        StackPane click = (StackPane)e.getSource();
        int row = GridPane.getRowIndex(click);
        int col = GridPane.getColumnIndex(click);
        ImageView view = ((ImageView)(click).getChildren().get(0));
        if(part == null) {
            view.setImage(null);
            ship.addPart(row, col, part);
        } else {
            view.setImage(part.sprite());
            ship.addPart(row, col, part);
        }
    }
    
    public void setBlock() {
        part = new ShipPart();
    }
    
    public void setEngine() {
        part = new Engine();
    }
    
    public void setTank() {
        part = new FuelTank("default", 0, 1);
    }
    public void setDelete() {
        part = null;
    }
    
    public void saveAs() {
        Stage stage = (Stage) bp.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(stage);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(ship);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
