/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import com.badlogic.gdx.math.Vector2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import spaceshipbuilder.parts.Engine;
import spaceshipbuilder.parts.FuelTank;
import spaceshipbuilder.parts.ShipPart;

/**
 *
 * @author Nurivan
 */
public class Game {
    
    private Canvas canvas;
    private GraphicsContext ctx;
    private Scene scene;
    private Spaceship ship;
    private double x, y = 0;
    private HashMap<String, Image> sprites;
    

    public Game(Canvas canvas) {
        sprites = new HashMap<>();
        sprites.put("dirt", new Image("Assets/Dirt.png"));
        sprites.put("grass", new Image("Assets/Grass.png"));
        sprites.put("clouds", new Image("Assets/Clouds.png"));
        
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
        scene = canvas.getScene();
        
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Space Ships", "*.ship");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(scene.getWindow());
        FileInputStream in;
        if(file != null) {
            try {
                in = new FileInputStream(file);
                ObjectInputStream obj = new ObjectInputStream(in);
                ship = (Spaceship)obj.readObject();
                for(ShipPart[] parts : ship.getShipParts()) {
                    for(ShipPart p : parts) {
                        if(p != null)
                            sprites.put(p.sprite(), new Image(p.sprite()));
                    }
                }
            } catch (Exception ex) {
                ship = new Spaceship();
            }
        } else ship = new Spaceship();
        
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue());
        });
        
        
    }
    
    public void drawBackground() {
        ctx.save();
        ctx.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        for(double i = -Math.ceil(canvas.getWidth() / 800) * 400 - x % 400 - 400; i < canvas.getWidth(); i += 400) {
            for(double j = -Math.ceil(canvas.getHeight() / 800) * 400 + y % 400 - 400; j < canvas.getHeight(); j += 400) {
                if (y - j > 0) ctx.drawImage(sprites.get("clouds"), i, j);
                else if(y - j > -400) ctx.drawImage(sprites.get("grass"), i, j);
                else ctx.drawImage(sprites.get("dirt"), i, j);
            }
        }
        ctx.restore();
    }
    
    public void drawShip() {
        ShipPart[][] parts = ship.getShipParts();
        ctx.save();
        double centerX = ctx.getCanvas().getWidth() / 2;
        double centerY = ctx.getCanvas().getHeight() / 2;
        double SIZE = 50;
        ctx.translate(centerX, centerY);
        ctx.rotate(ship.getRotation());
        for(ShipPart[] col : parts) {
            for(ShipPart p : col) {
                if(p != null) {
                    ctx.drawImage(sprites.get(p.sprite()), p.getX() - SIZE / 2, p.getY() - SIZE / 2);
                }
            }
        }
        ctx.restore();
    }
    
    public void drawFuel() {
        TreeMap<String, Float> fuel = ship.getFuel();
        TreeMap<String, Float> consumed = ship.getConsumedFuel();
        float height = 10;//px
        float maxWidth = 140;
        Font f = new Font(10);
        int num = 1;
        for(String s : fuel.keySet()) {
            float percent = (fuel.get(s) - consumed.get(s)) / fuel.get(s);
            ctx.setFill(Color.BLACK);
            ctx.fillText(s.substring(0, 3) + ": " + (int)(percent * 100) + "%", 0, canvas.getHeight() - (num - 1) * height);
            ctx.setFill(Color.GREEN);
            ctx.fillRect(55, canvas.getHeight() - num * height, maxWidth * percent, height);
            num++;
        }
    }
    
    public void loop() {
        Engine e = new Engine(new Vector2(0, 1000), 0.1f, "nuclear");
        e.setMass(5);
        Engine e2 = new Engine(new Vector2(0, 1000), 0.1f, "nuclear");
        e2.setMass(5);
        Engine e3 = new Engine(new Vector2(0, 1000), 0.1f, "nuclear");
        e3.setMass(5);
        //This is the main game loop
        AnimationTimer h = new AnimationTimer() {
            long last = System.nanoTime();
            @Override
            public void handle(long now) {
                //Time passed since last call
                float delta = (float)(now - last);
                float fps = 1000000000 / delta;

                last = now;
                ship.updateShip(delta);
                x = ship.getX();
                y = ship.getY();
                ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                drawBackground();
                drawShip();
                drawFuel();
                ctx.setFill(Color.BLACK);
                ctx.fillText(Double.toString(fps), 10, 10);
            }
            
        };
        h.start();
    }
}
