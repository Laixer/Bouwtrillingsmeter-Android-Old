package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 21-9-2017.
 * Saves for each dominant frequencies whether velocity exeeds limit value
 */

public class Fdom {
    public int[] frequencies;
    public float[] velocities;
    public boolean[] exceed;

    /**
     * Constructor
     */
    public Fdom(int[] frequencies, float[] velocities, boolean[] exceed){
        this.frequencies = frequencies;
        this.velocities = velocities;
        this.exceed = exceed;
    }
}
