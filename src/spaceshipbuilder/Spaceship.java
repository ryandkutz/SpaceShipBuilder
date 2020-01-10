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

    public Spaceship(ShipPiece[][] shipParts) {
        this.shipParts = shipParts;
    }
    
    public double getMass() {
        double mass = 0;
        for(ShipPiece[] row: shipParts) {
            for(ShipPiece part: row) {
                mass += part.getMass();
            }
        }
        return mass;
    }
    
}
