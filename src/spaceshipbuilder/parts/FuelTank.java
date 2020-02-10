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
public class FuelTank extends ShipPart {
    private String type;
    
    //In kg/meter^2
    private float density;

    public FuelTank(String type, float density, float containerMass) {
        super(density + containerMass);
        this.type = type;
        this.density = density;
    }

    public String getType() {
        return type;
    }

    public float getDensity() {
        return density;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDensity(float density) {
        this.density = density;
    }
    
    
    
}
