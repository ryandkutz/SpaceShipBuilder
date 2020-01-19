/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import spaceshipbuilder.parts.*;

/**
 *
 * @author Gomez_866923
 */
public class Spaceship {
    private ShipPart[][] shipParts;
    private double x;
    private double y;
    private double rotation;
    private String name;

    public Spaceship(int size, String name) {
        shipParts = new ShipPart[size][size];
        x = 0;
        y = 0;
        rotation = 0;
        this.name = name;
    }
    
    public Spaceship() {
        shipParts = new ShipPart[4][4];
        x = 0;
        y = 0;
        rotation = 0;
        name = "";
    }
    
    public double getMass() {
        //Returns the mass at the start of a launch
        double mass = 0;
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null)
                    mass += part.getMass();
            }
        }
        return mass;
    }
    
    public double getCenterMassX() {
        //X position of center of mass relative to the x of the ship
        double x = 0;
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null)
                    x += part.getX() * part.getMass();
            }
        }
        double mass = getMass();
        if(mass > 0)
            x /= mass;
        return x;
    }
    
    public double getCenterMassY() {
        //Y position of center of mass relative to the y of the ship
        double y = 0;
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null)
                    y += part.getY() * part.getMass();
            }
        }
        double mass = getMass();
        if(mass > 0)
            y /= getMass();
        return y;
    }
    
    public double getMomentOfInertia() {
        //Adds moments of inertias for all ship parts
        double i = 0;
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null)
                    i += part.getMomentOfInertia(getCenterMassX(), getCenterMassY());
            }
        }
        return i;
    }

    public ShipPart[][] getShipParts() {
        return shipParts;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRotation() {
        return rotation;
    }

    public String getName() {
        return name;
    }
    
    public void updateShip() {
        
    }
}
