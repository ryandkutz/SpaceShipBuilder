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
        //This is the main game loop, ideally needs a delta
        Image i = new Image("Assets/Clouds.png");
        AnimationTimer h = new AnimationTimer() {
            long last = 0;
            @Override
            public void handle(long now) {
                //Delta should be 60ths of secodns past
                double delta = 60 * (double)(now - last) / (1e9);
                double fps = 1000000000 / ((double)now - (double)last);
                last = now;
                if(east) x += 5 * delta;
                if(west) x -= 5 * delta;
                if(north) y += 5 * delta;
                if(south) y -= 5 * delta;
                ctx.setFill(Color.WHITE);
                ctx.fillRect(0, 0, 400, 400);
                drawBackground();
                ctx.setFill(Color.BLACK);
                ctx.fillText(Double.toString(fps), 10, 10);
                ctx.setFill(Color.BLUE);
                ctx.fillRect(190,190,20,20);
            }
            
        };
        h.start();
    }
}
