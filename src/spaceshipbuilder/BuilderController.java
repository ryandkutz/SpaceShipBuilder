/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import com.badlogic.gdx.math.Vector2;
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
import javafx.scene.control.TextField;
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
    @FXML private BorderPane bp;
    @FXML private GridPane gp;
    @FXML private Slider otherSlider;
    @FXML private TextField rotationField;
    @FXML private Label partPrice;
    @FXML private Label partMass;
    @FXML private Label shipCost;
    @FXML private Label amountLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ship = new Spaceship(5, 5, "");
        part = new ShipPart();
        otherSlider.valueProperty().addListener((obs, old, newV) -> {sliderChange();});
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
            view.setRotate(part.getRotation());
        }
        if(part instanceof Engine) {
            Engine eng = new Engine(((Engine)part).getThrust(), ((Engine)part).getFuelUsage(), ((Engine)part).getFuelType(), part.getMass());
            eng.setRotation(part.getRotation());
            ship.addPart(row, col, eng);
        } else if(part instanceof FuelTank) {
            FuelTank f = new FuelTank(((FuelTank)part).getType(), ((FuelTank)part).getAmount(), ((FuelTank)part).getContainerMass());
            ship.addPart(row, col, f);
            f.setRotation(part.getRotation());
        } else if(part != null) {
            ShipPart p = new ShipPart();
            p.setRotation(part.getRotation());
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
        double max = otherSlider.getMax();
        double val = otherSlider.getValue();
        double amount = (val / max) * 1000;
        amountLabel.setText("Thrust: " + String.format("%.2f", amount) + "N");
        ((Engine)part).setThrust(new Vector2(0, (float)amount));
        ((Engine)part).setFuelUsage((float)amount / 1000);
    }
    
    public void setNuclear() {
        if(part instanceof Engine) {
            ((Engine)part).setType("nuclear");
        }
        if(part instanceof FuelTank) {
            ((FuelTank)part).setType("nuclear");
        }
    }
    
    public void setCoal() {
        if(part instanceof Engine) {
            ((Engine)part).setType("coal");
        }
        if(part instanceof FuelTank) {
            ((FuelTank)part).setType("coal");
        }
    }
    
    public void setSid() {
        if(part instanceof Engine) {
            ((Engine)part).setType("sidium");
        }
        if(part instanceof FuelTank) {
            ((FuelTank)part).setType("sidium");
        }
    }
    
    public void setTank() {
        part = new FuelTank("default", 0, 1);
        double max = otherSlider.getMax();
        double val = otherSlider.getValue();
        double amount = (val / max) * 4;
        amountLabel.setText("Fuel: " + String.format("%.2f", amount) + "m^2");
        ((FuelTank)part).setAmount((float)amount);
    }
    public void setDelete() {
        part = null;
    }
    
    public void saveAs() {
        Stage stage = (Stage) bp.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("./ships"));
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
        chooser.setInitialDirectory(new File("./ships"));
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
        double max = otherSlider.getMax();
        double val = otherSlider.getValue();
        amountLabel.setText("test");
        if(part instanceof Engine) {
            double amount = (val / max) * 1000;
            amountLabel.setText("Thrust: " + String.format("%.2f", amount) + "N");
            ((Engine)part).setThrust(new Vector2(0, (float)amount));
            ((Engine)part).setFuelUsage((float)amount / 1000);
        }
        if(part instanceof FuelTank) {
            double amount = (val / max) * 4;
            amountLabel.setText("Fuel: " + String.format("%.2f", amount) + "m^2");
            ((FuelTank)part).setAmount((float)amount);
        }
    }
    public void setRotation() {
        part.setRotation(Float.parseFloat(rotationField.getText()));
    }
}
