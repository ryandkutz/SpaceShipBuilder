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
public abstract class ShipPart {
    
    public final int SIZE = 50;
    //x and y should be relative to the position of the entire ship
    private double x;
    private double y;
    private double mass;
    
    public ShipPart() {
        x = 0;
        y = 0;
        mass = 0;
    }

    public ShipPart(double x, double y, double mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
    }
    
    public double getMomentOfInertia(double massX, double massY) {
        //Uses moment of inertia euation for a plane and parallel axis theorem.
        double center = 1/6 * mass * Math.pow(SIZE, 2);
        double distanceSq = Math.pow(x - massX, 2) + Math.pow(y - massY, 2);
        return center + distanceSq * mass;
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
