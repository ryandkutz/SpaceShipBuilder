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
import java.util.TreeSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gomez_866923
 */
public class ScoreBoardController implements Initializable {

    private TreeSet<Spaceship> scores;
    @FXML private TableColumn names;
    @FXML private TableColumn heights;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        open();
    }    
    
    public void open() {
    
        try {
            FileInputStream in = new FileInputStream("scores");
            ObjectInputStream obj = new ObjectInputStream(in);
            scores = (TreeSet<Spaceship>)obj.readObject();
            for(Spaceship s : scores) {
                names.setCellValueFactory(new PropertyValueFactory<>(s.getName()));
                heights.setCellValueFactory(new PropertyValueFactory<>(Float.toString(s.getRecordHeight())));
            }
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
    }
    
}
