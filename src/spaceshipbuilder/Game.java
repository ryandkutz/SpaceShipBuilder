/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import com.badlogic.gdx.math.Vector2;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
//    private boolean north, south, east, west = false;
    private double x, y = 0;
    

    public Game(Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
        scene = canvas.getScene();
        
    }
    
    public void drawBackground() {
        Image clouds = new Image("Assets/Clouds.png");
        for(double i = -x % 400 - 400; i < canvas.getWidth(); i += 400) {
            for(double j = y % 400 - 400; j < canvas.getHeight(); j += 400) {
                ctx.drawImage(clouds, i, j);
            }
        }
//        ctx.drawImage(clouds, -x % 400 + (x > 0 ? 400 : -400), y % 400);
//        ctx.drawImage(clouds, -x % 400, y % 400);
//        ctx.drawImage(clouds, -x % 400 + (x > 0 ? 400 : -400), y % 400 + (y > 0 ? -400 : 400));
//        ctx.drawImage(clouds, -x % 400, y % 400 + (y > 0 ? -400 : 400));
    }
    
    public void drawShip(Spaceship ship) {
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
                    ctx.drawImage(p.sprite(), p.getX() - SIZE / 2, p.getY() - SIZE / 2);
                }
            }
        }
        ctx.restore();
    }
    
    public void loop() {
        Spaceship s = new Spaceship(4, 3, "TEST");
        Engine e = new Engine(new Vector2(0, 1000), 1, "nuclear");
        e.setMass(5);
        Engine e2 = new Engine(new Vector2(0, 1000), 1, "nuclear");
        e2.setMass(5);
        Engine e3 = new Engine(new Vector2(0, 1000), 1, "nuclear");
        e3.setMass(5);
        s.addPart(3, 0, e2);
        s.addPart(3,2,e);
        s.addPart(3,1,e3);
        s.addPart(0, 0, new FuelTank("nuclear", 4, 1));
        s.addPart(0, 1, new ShipPart());
        s.addPart(0, 2, new ShipPart());
        s.addPart(1, 0, new ShipPart());
        s.addPart(1, 1, new ShipPart());
        s.addPart(1, 2, new ShipPart());
        s.addPart(2, 0, new ShipPart());
        s.addPart(2, 1, new ShipPart());
        s.addPart(2, 2, new ShipPart());
        //This is the main game loop
        AnimationTimer h = new AnimationTimer() {
            long last = System.nanoTime();
            @Override
            public void handle(long now) {
                //Time passed since last call
                float delta = (float)(now - last);
                float fps = 1000000000 / delta;
                last = now;
                s.updateShip(delta);
                x = s.getX();
                y = s.getY();
                drawBackground();
                drawShip(s);
                ctx.setFill(Color.BLACK);
                ctx.fillText(Double.toString(fps), 10, 10);
            }
            
        };
        h.start();
    }
}
