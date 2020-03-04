/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
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
    private String type;
    @FXML private BorderPane bp;
    @FXML private GridPane gp;
    @FXML private Slider otherSlider;
    @FXML private Label partPrice;
    @FXML private Label partMass;
    @FXML private Label shipCost;
    @FXML private Label otherLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ship = new Spaceship(5, 5, "");
        part = new ShipPart();
        type = "default";
        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {
                ship.addPart(r, c, new ShipPart());
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
        } else {
            view.setImage(new Image(part.sprite()));
        }
        if(part instanceof Engine) {
            ship.addPart(row, col, new Engine());
        } else if(part instanceof FuelTank) {
            ship.addPart(row, col, new FuelTank("default", 1, 1));
        } else if(part != null) {
            ship.addPart(row, col, new ShipPart());
        } else {
            ship.addPart(row, col, null);
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
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Space Ships", "*.ship");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(stage);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ship);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void open() {
        Stage stage = (Stage) bp.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Space Ships", "*.ship");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(stage);
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream obj = new ObjectInputStream(in);
            ship = (Spaceship)obj.readObject();
            int row = 0;
            for(ShipPart[] parts : ship.getShipParts()) {
                int col = 0;
                for(ShipPart p : parts) {
                    for(Node n : gp.getChildren()) {
                        if(GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) == col) {
                            ImageView view = (ImageView)(((StackPane)n).getChildren().get(0));
                            if(p != null)
                                view.setImage(new Image(p.sprite()));
                            else view.setImage(null);
                        }
                    }
                    col++;
                }
                row++;
            }
        } catch (Exception ex) {
            Logger.getLogger(BuilderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sliderChange() {
        double val = otherSlider.getValue();
    }
}
