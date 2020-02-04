/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder.parts;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author kutz_865792
 */



public class Engine extends ShipPart {
    
    private Vector2D thrust;
    private double fuelUsage;

    public Engine(Vector2D thrust, double fuelUsage) {
        super();
        this.thrust = thrust;
        this.fuelUsage = fuelUsage;
    }

    public Engine(double x, double y, Vector2D thrust, double fuelUsage, double mass) {
        super(x, y, mass);
        this.thrust = thrust;
        this.fuelUsage = fuelUsage;
    }
    
    public Vector2D getThrust() {
        return thrust;
    }
    
}
