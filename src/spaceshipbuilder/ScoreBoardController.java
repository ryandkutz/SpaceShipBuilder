/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gomez_866923
 */
public class ScoreBoardController implements Initializable {

    private TreeSet<Spaceship> scores;
    @FXML private TableView table;
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
        names.setCellValueFactory(new PropertyValueFactory<>("name"));
        heights.setCellValueFactory(new PropertyValueFactory<>("recordHeight"));
        try {
            FileInputStream in = new FileInputStream(new File("./scores/scores.sc"));
            ObjectInputStream obj = new ObjectInputStream(in);
            scores = (TreeSet<Spaceship>)obj.readObject();
            for(Spaceship s : scores) {
                table.getItems().add(s);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getStackTrace());
            System.out.println("Opps");
        } catch(IOException e) {
            System.out.println(e.getCause());
        } catch(ClassNotFoundException ex) {
            System.out.println(ex.getCause());
        }
    }
    
}
