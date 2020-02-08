/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Nurivan
 */
public class Units {
    public static final float GRAVITY = 10.0f;
    //25 pixels is one meter, converts pixels to meters
    static final float METER_MULT = 1/25f;
    //converts nanoseconds to seconds
    static final float SECOND_MULT = 1/1e9f;
    
    public static float meters(float pixels) {
        return pixels * METER_MULT;
    }
    
    public static float pixels(float meters) {
        return meters / METER_MULT;
    }
    
    public static float seconds(float nanoseconds) {
        return nanoseconds * SECOND_MULT;
    }
    
    public static Vector2 acceleration(float mass, Vector2 newtons) {
        Vector2 a = new Vector2(newtons.x, newtons.y);
        a.x /= mass;
        a.y /= mass;
        return a;
    }
}
