/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder.parts;
/**
 *
 * @author kutz_865792
 */



public class Engine extends ShipPart {
    
    private double thrust;
    private double fuelUsage;

    public Engine(double thrust, double fuelUsage) {
        this.thrust = thrust;
        this.fuelUsage = fuelUsage;
    }

    public Engine(double thrust, double fuelUsage, double x, double y, double mass) {
        super(x, y, mass);
        this.thrust = thrust;
        this.fuelUsage = fuelUsage;
    }

    
    
    
    
    
    
    
    
}
