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
    
    public void loop() {
        Image i = new Image("Assets/Clouds.png");
        AnimationTimer h = new AnimationTimer() {
            long last = 0;
            @Override
            public void handle(long now) {
                long fps = 1000000000 / (now - last);
                last = now;
                if(east) x += 5;
                if(west) x -= 5;
                if(north) y += 5;
                if(south) y -= 5;
                ctx.setFill(Color.WHITE);
                ctx.fillRect(0, 0, 400, 400);
                drawBackground();
                ctx.setFill(Color.BLACK);
                ctx.fillText(Long.toString(fps), 10, 10);
                ctx.setFill(Color.BLUE);
                ctx.fillRect(190,190,20,20);
            }
            
        };
        h.start();
    }
}
