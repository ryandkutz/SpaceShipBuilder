/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder.parts;

import com.badlogic.gdx.math.Vector2;
import java.io.Serializable;


/**
 *
 * @author Gomez_866923
 */
public class ShipPart implements Serializable {
    
    public final int SIZE = 50;
    //Position is relative to the entire ship
    //Measured from center of the ship to center of the part in pixels, where each part is 50 pixels.
    private Vector2 position;
    private float mass;
    private float rotation;
    private String sprite;
    
    public ShipPart() {
        rotation = 0;
        position = new Vector2(0, 0);
        mass = 10;
        sprite = "Assets/Block.png";
    }

    public ShipPart(float mass) {
        rotation = 0;
        position = new Vector2(0, 0);
        this.mass = mass;
        sprite = "Assets/Block.png";
    }

    public ShipPart(float mass, float rotation) {
        this.position = new Vector2();
        this.mass = mass;
        this.rotation = rotation;
        this.sprite = "Assets/Block.png";
    }
    
    
    
    public float getMomentOfInertia(float massX, float massY) {
        //Uses moment of inertia euation for a plane and parallel axis theorem.
        double center = mass / 6 * Math.pow(SIZE, 2);
        double distanceSq = Math.pow(position.x - massX, 2) + Math.pow(position.y - massY, 2);
        return (float)(center + distanceSq * mass);
    }
    
    public float getX() {
        return position.x;
    }
    
    public float getY() {
        return position.y;
    }
    
    public void setX(float x) {
        position = new Vector2(x, position.y);
    }
    
    public void setY(float y) {
        position = new Vector2(position.x, y);
    }
    
    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
    
    public String sprite() {
        return sprite;
    }
    
    public void setSprite(String i) {
        sprite = i;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
    
}
