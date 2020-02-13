/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Gomez_866923
 */
public class FuelTypes {
    public static float density(String type) {
        //kg/m^2
        if(type.equals("nuclear")) return 2;
        return 0;
    }
}
