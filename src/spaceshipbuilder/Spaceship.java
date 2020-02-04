/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import spaceshipbuilder.parts.*;

/**
 *
 * @author Gomez_866923
 */
public class Spaceship {
    private ShipPart[][] shipParts;
    private Vector2D position;
    //Movement force
    private Vector2D movement;
    private double rotationalVelocity;
    private double rotation;
    private String name;

    public Spaceship(int size, String name) {
        shipParts = new ShipPart[size][size];
        position = new Vector2D(0, 0);
        rotation = 0;
        this.name = name;
        movement = new Vector2D(0, 0);
    }
    
    public Spaceship() {
        shipParts = new ShipPart[4][4];
        position = new Vector2D(0, 0);
        rotation = 0;
        name = "";
        movement = new Vector2D(0, 0);
    }
    
    public void addPart(int r, int c, ShipPart p) {
        p.setX((c - (double)(shipParts[r].length - 1) / 2) * p.SIZE);
        p.setY((r - (double)(shipParts.length - 1) / 2) * p.SIZE);
        shipParts[r][c] = p;
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
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }
    
    public void setX(double x) {
        position = new Vector2D(x, position.getY());
    }
    
    public void setY(double y) {
        position = new Vector2D(position.getX(), y);
    }
    
    public void transform(Vector2D transform) {
        position.add(transform);
    }

    public double getRotation() {
        return rotation;
    }

    public String getName() {
        return name;
    }
    
    public void updateShip(double delta) {
        double torque = 1;
        int r = 0;
        Vector2D force = new Vector2D(0, -getMass() * 1);
        for(ShipPart[] row: shipParts) {
            int c = 0;
            for(ShipPart part: row) {
                if(part != null && part instanceof Engine) {
                    force = force.add(((Engine)part).getThrust());
                    Vector2D cmPosition = new Vector2D(getCenterMassX() - part.getX(), getCenterMassY() - part.getY());
                    //IDK why the vector2D cross product requires 2 vectors, so this might be wrong
                    torque += cmPosition.crossProduct(new Vector2D(0, 0), ((Engine)part).getThrust());
                }
                c++;
        }
            r++;
        }
        double alpha = torque / getMomentOfInertia();
        rotationalVelocity += alpha * delta;
        rotation += rotationalVelocity * delta;
        rotation %= 2 * Math.PI;
        Vector2D direction = new Vector2D(Math.cos(rotation), Math.sin(rotation));
        movement.add(direction.scalarMultiply(force.getNorm()));
        position = position.add(movement.scalarMultiply(delta/getMass()));
    }
}
