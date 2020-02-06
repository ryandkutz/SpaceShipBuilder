/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import com.badlogic.gdx.math.Vector2;
import spaceshipbuilder.parts.*;

/**
 *
 * @author Gomez_866923
 */
public class Spaceship {
    private ShipPart[][] shipParts;
    private Vector2 position;
    //Movement force
    private Vector2 movement;
    private float rotationalVelocity;
    private float rotation;
    private String name;

    public Spaceship(int size, String name) {
        shipParts = new ShipPart[size][size];
        position = new Vector2(0, 0);
        rotation = 0;
        this.name = name;
        movement = new Vector2(0, 0);
    }
    
    public Spaceship() {
        shipParts = new ShipPart[4][4];
        position = new Vector2(0, 0);
        rotation = 0;
        name = "";
        movement = new Vector2(0, 0);
    }
    
    public void addPart(int r, int c, ShipPart p) {
        p.setX((c - (float)(shipParts[r].length - 1) / 2) * p.SIZE);
        p.setY((r - (float)(shipParts.length - 1) / 2) * p.SIZE);
        shipParts[r][c] = p;
    }
    
    public float getMass() {
        //Returns the mass at the start of a launch
        float mass = 0;
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null)
                    mass += part.getMass();
            }
        }
        return mass;
    }
    
    public float getCenterMassX() {
        //X position of center of mass relative to the x of the ship
        float x = 0;
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null)
                    x += part.getX() * part.getMass();
            }
        }
        float mass = getMass();
        if(mass > 0)
            x /= mass;
        return x;
    }
    
    public float getCenterMassY() {
        //Y position of center of mass relative to the y of the ship
        float y = 0;
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null)
                    y += part.getY() * part.getMass();
            }
        }
        float mass = getMass();
        if(mass > 0)
            y /= getMass();
        return y;
    }
    
    public float getMomentOfInertia() {
        //Adds moments of inertias for all ship parts
        float i = 0;
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
    

    public float getRotation() {
        return rotation;
    }

    public String getName() {
        return name;
    }
    
    public void updateShip(float delta) {
        float torque = 1;
        int r = 0;
        Vector2 force = new Vector2(0, -getMass() * 1);
        for(ShipPart[] row: shipParts) {
            int c = 0;
            for(ShipPart part: row) {
                if(part != null && part instanceof Engine) {
                    force = force.add(((Engine)part).getThrust());
                    Vector2 cmPosition = new Vector2(getCenterMassX() - part.getX(), getCenterMassY() - part.getY());
                    //IDK why the vector2D cross product requires 2 vectors, so this might be wrong
                    torque += cmPosition.crs(((Engine)part).getThrust());
                }
                c++;
        }
            r++;
        }
        float alpha = torque / getMomentOfInertia();
        rotationalVelocity += alpha * delta;
        rotation += rotationalVelocity * delta;
        rotation %= 2 * Math.PI;
        Vector2 direction = new Vector2((float)Math.cos(rotation), (float)Math.sin(rotation));
        movement.add(direction.scl(force.nor()));
        position = position.add(movement.scl(delta/getMass()));
    }
}
