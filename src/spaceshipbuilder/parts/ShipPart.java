/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder.parts;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Gomez_866923
 */
public abstract class ShipPart {
    
    public final int SIZE = 50;
    //Position is relative to the entire ship
    //Measured from center of the ship to center of the part in pixels, where each part is 50 pixels.
    Vector2D position;
    private double mass;
    
    public ShipPart() {
        position = new Vector2D(0, 0);
        mass = 0;
    }

    public ShipPart(double x, double y, double mass) {
        position = new Vector2D(x, y);
        this.mass = mass;
    }
    
    public double getMomentOfInertia(double massX, double massY) {
        //Uses moment of inertia euation for a plane and parallel axis theorem.
        double center = 1/6 * mass * Math.pow(SIZE, 2);
        double distanceSq = Math.pow(position.getX() - massX, 2) + Math.pow(position.getY() - massY, 2);
        return center + distanceSq * mass;
    }
    
    public double getX() {
        return position.getX();
    }
    
    public double getY() {
        return position.getY();
    }
    
    public void setX(double x) {
        position = new Vector2D(x, position.getY());
    }
    
    public void setY(double y) {
        position = new Vector2D(position.getX(), y);
    }
    
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    

    
}
