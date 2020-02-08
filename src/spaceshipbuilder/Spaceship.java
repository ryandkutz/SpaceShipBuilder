/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import com.badlogic.gdx.math.Vector2;
import spaceshipbuilder.parts.*;
import utils.Units;
import static utils.Units.acceleration;
import static utils.Units.seconds;

/**
 *
 * @author Gomez_866923
 */
public class Spaceship {
    private ShipPart[][] shipParts;
    private Vector2 position;
    private Vector2 velocity;
    private float rotationalVelocity;
    private float rotation;
    private String name;

    public Spaceship(int size, String name) {
        shipParts = new ShipPart[size][size];
        position = new Vector2(0, 0);
        rotation = 0;
        this.name = name;
        velocity = new Vector2(0, 0);
    }
    
    public Spaceship() {
        shipParts = new ShipPart[4][4];
        position = new Vector2(0, 0);
        rotation = 0;
        name = "";
        velocity = new Vector2(0, 0);
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
                if(part != null) {
                    i += part.getMomentOfInertia(getCenterMassX(), getCenterMassY());
                }
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
    
    public void setRotation(float degrees) {
        rotation = degrees;
    }
    
    public float getRotation() {
        return rotation;
    }

    public String getName() {
        return name;
    }
    
    public void updateShip(float delta) {
        delta = seconds(delta);
        float torque = 0;
        Vector2 force = new Vector2(0, 0);
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null && part instanceof Engine) {
                    force.add(((Engine)part).getThrust());
                    Vector2 cmPosition = new Vector2(getCenterMassX() - part.getX(), getCenterMassY() - part.getY());
                    torque += cmPosition.crs(((Engine)part).getThrust());
                }
            }
        }
        float alpha = torque / getMomentOfInertia();
        rotationalVelocity += alpha * delta;
        rotation += rotationalVelocity * delta;
        rotation %= 360;
        force.rotate(-rotation);
        force.add(new Vector2(0, -getMass() * Units.GRAVITY));
        velocity.add(acceleration(getMass(), force).scl(delta));
        position.add(velocity.x * delta, velocity.y * delta);
    }
}
