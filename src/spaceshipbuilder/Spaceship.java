/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipbuilder;

import com.badlogic.gdx.math.Vector2;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeMap;
import spaceshipbuilder.parts.*;
import static utils.FuelTypes.density;
import utils.Units;
import static utils.Units.acceleration;
import static utils.Units.seconds;

/**
 *
 * @author Gomez_866923
 */
public class Spaceship implements Serializable, Comparable<Spaceship>{
    private ShipPart[][] shipParts;
    private Vector2 position;
    private Vector2 velocity;
    private TreeMap<String, Float> fuel;
    private TreeMap<String, Float> consumedFuel;
    private float rotationalVelocity;
    private float rotation;
    private float mass;
    private float recordHeight;
    private String name;

    public Spaceship(int rows, int cols, String name) {
        shipParts = new ShipPart[rows][cols];
        position = new Vector2(0, 0);
        rotation = 0;
        this.name = name;
        velocity = new Vector2(0, 0);
        fuel = new TreeMap<>();
        consumedFuel = new TreeMap<>();
        mass = getMass();
        recordHeight = 0;
    }
    
    public Spaceship() {
        shipParts = new ShipPart[4][4];
        position = new Vector2(0, 0);
        rotation = 0;
        name = "";
        velocity = new Vector2(0, 0);
        fuel = new TreeMap<>();
        consumedFuel = new TreeMap<>();
        mass = getMass();
        recordHeight = 0;
    }
    
    public void addPart(int r, int c, ShipPart p) {
        shipParts[r][c] = p;
        if (p instanceof FuelTank) {
            FuelTank f = (FuelTank)p;
            if(fuel.containsKey(f.getType())){
                fuel.put(f.getType(), f.getAmount() + fuel.get(f.getType()));
            }
            else fuel.put(f.getType(), f.getAmount());
            consumedFuel.put(f.getType(), 0f);
        }
        if (p != null) {
            p.setX((c - (float)(shipParts[r].length - 1) / 2) * p.SIZE);
            p.setY((r - (float)(shipParts.length - 1) / 2) * p.SIZE);
            mass = getMass();
        }
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

    public void setName(String name) {
        this.name = name;
    }

    public TreeMap<String, Float> getFuel() {
        return fuel;
    }

    public TreeMap<String, Float> getConsumedFuel() {
        return consumedFuel;
    }
    
    
    public void updateShip(float delta) {
        delta = seconds(delta);
        float torque = 0;
        Vector2 force = new Vector2(0, 0);
        for(ShipPart[] row: shipParts) {
            for(ShipPart part: row) {
                if(part != null && part instanceof Engine &&
                        fuel.containsKey(((Engine)part).getFuelType()) &&
                        ((Engine)part).getFuelUsage() * delta <
                        fuel.get(((Engine)part).getFuelType()) -
                        consumedFuel.get(((Engine)part).getFuelType())) {
                    consumedFuel.put(((Engine)part).getFuelType(), consumedFuel.get(((Engine)part).getFuelType()) + delta * ((Engine)part).getFuelUsage());
                    mass -= delta * ((Engine)part).getFuelUsage() * density(((Engine)part).getFuelType());
                    force.add(((Engine)part).getThrust());
                    Vector2 cmPosition = new Vector2(getCenterMassX() - part.getX(), getCenterMassY() - part.getY());
                    torque += cmPosition.crs(((Engine)part).getThrust());
                }
            }
        }
        float alpha = torque / getMomentOfInertia() * (float)(360 / (2 * Math.PI));
        if(position.y >= 0) {
            rotationalVelocity += alpha * delta;
            rotation += rotationalVelocity * delta;
            rotation %= 360;
            force.rotate(-rotation);
            force.add(new Vector2(0, -mass * Units.GRAVITY));
            velocity.add(acceleration(mass, force).scl(delta));
        } else {
            velocity.x = 0;
            velocity.y = 0;
        }
        position.add(velocity.x * delta, velocity.y * delta);
        if(position.y > recordHeight) recordHeight = position.y;
    }

    @Override
    public int compareTo(Spaceship o) {
        if(recordHeight > o.recordHeight) return -1;
        else if(recordHeight < o.recordHeight) return 1;
        return name.compareTo(o.name);
    }

    public float getRecordHeight() {
        return recordHeight;
    }
    
    
}
