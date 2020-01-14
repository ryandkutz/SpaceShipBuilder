/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Nurivan
 */
public class Game {
    
    private Canvas canvas;
    private GraphicsContext ctx;
    

    public Game(Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
    }
    
    
    public void loop() {
        
        Image i = new Image("Assets/Clouds.png");
        ctx.setFill(Color.BLUE);
        ctx.fillRect(0,0,20,20);
        
        AnimationTimer h = new AnimationTimer() {
            double x = 0;
            double y = 0;
            @Override
            public void handle(long now) {
                x++;
                y--;
                ctx.setFill(Color.WHITE);
                ctx.fillRect(0, 0, 400, 400);
                ctx.drawImage(i, (y%400)-400, 0);
                ctx.drawImage(i, y%400, 0);
                ctx.drawImage(i, (y%400) + 400, 0);
                ctx.setFill(Color.BLUE);
                ctx.fillRect(x%400,100,20,20);
            }
            
        };
        h.start();
    }
}
