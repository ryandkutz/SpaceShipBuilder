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
        switch(type) {
            case "default" : return 1;
            case "nuclear" : return 5;
            case "coal" : return 1;
            case "sidium" : return 10;
            default : return -1;
        }
    }
}
