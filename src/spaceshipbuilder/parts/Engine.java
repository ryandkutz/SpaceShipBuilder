/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder.parts;

import com.badlogic.gdx.math.Vector2;
import javafx.scene.image.Image;


/**
 *
 * @author kutz_865792
 */



public class Engine extends ShipPart {
    
    private Vector2 thrust;
    private float fuelUsage;

    public Engine(Vector2 thrust, float fuelUsage) {
        super();
        this.thrust = thrust;
        this.fuelUsage = fuelUsage;
        super.setSprite(new Image("Assets/Engine.png"));
    }

    public Engine(float x, float y, Vector2 thrust, float fuelUsage, float mass) {
        super(x, y, mass);
        this.thrust = thrust;
        this.fuelUsage = fuelUsage;
        super.setSprite(new Image("Assets/Engine.png"));
    }
    
    public Vector2 getThrust() {
        return thrust;
    }
    
}
