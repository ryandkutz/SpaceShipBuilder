/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder.parts;

/**
 *
 * @author Gomez_866923
 */
public abstract class ShipPiece {
    
    private double x;
    private double y;
    private double mass;
    
    public ShipPiece() {
        x = 0;
        y = 0;
        mass = 0;
    }

    public ShipPiece(double x, double y, double mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getMass() {
        return mass;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    

    
}
