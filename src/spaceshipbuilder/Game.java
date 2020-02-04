/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import spaceshipbuilder.parts.ShipPart;

/**
 *
 * @author Nurivan
 */
public class Game {
    
    private Canvas canvas;
    private GraphicsContext ctx;
    private Scene scene;
    private boolean north, south, east, west = false;
    private double x, y = 0;
    

    public Game(Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
        scene = canvas.getScene();
        
        //Gettign keyboard presses and releases to controll direction
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()) {
                    case W: north = true; break;
                    case S: south = true; break;
                    case A: west = true; break;
                    case D: east = true; break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()) {
                    case W: north = false; break;
                    case S: south = false; break;
                    case A: west = false; break;
                    case D: east = false; break;
                }
            }
        });
        
    }
    
    public void drawBackground() {
        Image clouds = new Image("Assets/Clouds.png");
        ctx.drawImage(clouds, -x % 400 + (x > 0 ? 400 : -400), y % 400);
        ctx.drawImage(clouds, -x % 400, y % 400);
        ctx.drawImage(clouds, -x % 400 + (x > 0 ? 400 : -400), y % 400 + (y > 0 ? -400 : 400));
        ctx.drawImage(clouds, -x % 400, y % 400 + (y > 0 ? -400 : 400));
    }
    
    public void drawShip(Spaceship ship) {
        ShipPart[][] parts = ship.getShipParts();
        for(ShipPart[] col : parts) {
            for(ShipPart p : col) {
                //Not finished
            }
        }
    }
    
    public void updateParts(double delta) {
        delta /= 1e7;
        if(east) x += 2 * delta;
        if(west) x -= 2 * delta;
        if(north) y += 2 * delta;
        if(south) y -= 2 * delta;
    }
    
    public void loop() {
        //This is the main game loop
        AnimationTimer h = new AnimationTimer() {
            long last = 0;
            @Override
            public void handle(long now) {
                //Time passed since last call
                double delta = (now - last);
                double fps = 1000000000 / ((double)now - (double)last);
                last = now;
                updateParts(delta);
                drawBackground();
                drawShip(new Spaceship());
                ctx.setFill(Color.BLACK);
                ctx.fillText(Double.toString(fps), 10, 10);
            }
            
        };
        h.start();
    }
}
