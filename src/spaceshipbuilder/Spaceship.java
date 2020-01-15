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
    private ShipPiece[][] shipParts;
    private double x;
    private double y;
    private double rotation;
    private String name;

    public Spaceship(int size, String name) {
        shipParts = new ShipPiece[size][size];
        x = 0;
        y = 0;
        rotation = 0;
        this.name = name;
    }
    
    public double getMass() {
        //Returns the mass at the start of a launch
        double mass = 0;
        for(ShipPiece[] row: shipParts) {
            for(ShipPiece part: row) {
                mass += part.getMass();
            }
        }
        return mass;
    }
    
    public double getCenterMassX() {
        //X position of center of mass relative to the x of the ship
        double x = 0;
        for(ShipPiece[] row: shipParts) {
            for(ShipPiece part: row) {
                x += part.getX() * part.getMass();
            }
        }
        x /= getMass();
        return x;
    }
    
    public double getCenterMassY() {
        //Y position of center of mass relative to the y of the ship
        double y = 0;
        for(ShipPiece[] row: shipParts) {
            for(ShipPiece part: row) {
                y += part.getY() * part.getMass();
            }
        }
        y /= getMass();
        return y;
    }

    public ShipPiece[][] getShipParts() {
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
