package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 21-9-2017.
 * Saves for each dominant frequencies whether velocity exeeds limit value
 */

public class Fdom {
    public int[] frequencies;
    public boolean[] exceed;

    /**
     * Constructor
     */
    public Fdom(int[] frequencies, boolean[] exceed){
        this.frequencies = frequencies;
        this.exceed = exceed;
    }
}
