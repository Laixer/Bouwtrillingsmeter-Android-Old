package gemeenterotterdam.trillingmeterapp;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 16-9-2017.
 * Class containing limitValues (velocity) for each frequency
 */

public class LimitValueTable {

    public static int category = 0;

    public static float getLimitValue(int freq) {
        switch(category){
            case 1:
                if(freq < 15){
                    return 20;
                }
                else if(freq < 20){
                    return 22.5f;
                }
                else if(freq < 25){
                    return 25.0f;
                }
                else if(freq < 30){
                    return 27.5f;
                }
                else if(freq < 35){
                    return 30f;
                }
                else if(freq < 40){
                    return 32.5f;
                }
                else if(freq < 45){
                    return 35f;
                }
                else if(freq < 50){
                    return 37.5f;
                }
                else if(freq < 55){
                    return 40f;
                }
                else if(freq < 60){
                    return 41f;
                }
                else if(freq < 65){
                    return 42f;
                }
                else if(freq < 70){
                    return 43f;
                }
                else if(freq < 75){
                    return 44f;
                }
                else if(freq < 80){
                    return 45f;
                }
                else if(freq < 85){
                    return 46f;
                }
                else if(freq < 90) {
                    return 47f;
                }
                else if(freq < 95) {
                    return 48f;
                }
                else if(freq < 100){
                    return 49f;
                }
                else{
                    return 50f;
                }


            case 2:
                if(freq < 15){
                    return 5f;
                }
                else if(freq < 20){
                    return 6.25f;
                }
                else if(freq < 25){
                    return 7.5f;
                }
                else if(freq < 30){
                    return 8.75f;
                }
                else if(freq < 35){
                    return 10f;
                }
                else if(freq < 40){
                    return 11.25f;
                }
                else if(freq < 45){
                    return 12.5f;
                }
                else if(freq < 50){
                    return 13.75f;
                }
                else if(freq < 55){
                    return 15f;
                }
                else if(freq < 60){
                    return 15.5f;
                }
                else if(freq < 65){
                    return 16f;
                }
                else if(freq < 70){
                    return 16.5f;
                }
                else if(freq < 75){
                    return 17f;
                }
                else if(freq < 80){
                    return 17.5f;
                }
                else if(freq < 85){
                    return 18f;
                }
                else if(freq < 90) {
                    return 18.5f;
                }
                else if(freq < 95) {
                    return 19f;
                }
                else if(freq < 100){
                    return 19.5f;
                }
                else{
                    return 20f;
                }

            case 3:
                if(freq < 15){
                    return 3f;
                }
                else if(freq < 20){
                    return 3.63f;
                }
                else if(freq < 25){
                    return 4.25f;
                }
                else if(freq < 30){
                    return 4.88f;
                }
                else if(freq < 35){
                    return 5.5f;
                }
                else if(freq < 40){
                    return 6.13f;
                }
                else if(freq < 45){
                    return 6.75f;
                }
                else if(freq < 50){
                    return 7.38f;
                }
                else if(freq < 55){
                    return 8f;
                }
                else if(freq < 60){
                    return 8.2f;
                }
                else if(freq < 65){
                    return 8.4f;
                }
                else if(freq < 70){
                    return 8.6f;
                }
                else if(freq < 75){
                    return 8.8f;
                }
                else if(freq < 80){
                    return 9f;
                }
                else if(freq < 85){
                    return 9.2f;
                }
                else if(freq < 90) {
                    return 9.4f;
                }
                else if(freq < 95) {
                    return 9.6f;
                }
                else if(freq < 100){
                    return 9.8f;
                }
                else{
                    return 10f;
                }
        }
        return 0.0f;
    }
}
