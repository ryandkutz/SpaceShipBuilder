/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

/**
 *
 * @author Gomez_866923
 */
public class ShipPiece {
    
    private double x;
    private double y;
    public ShipPiece() {
        x = 0;
        y = 0;
    }
    
    public String getSprite();
    public double getMass();
    public double getMinX();
    public double getMinY();
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    
}
