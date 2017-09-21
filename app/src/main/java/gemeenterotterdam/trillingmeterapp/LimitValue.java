package gemeenterotterdam.trillingmeterapp;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 16-9-2017.
 * LimitValue (velocity) for each frequency for category 1.
 * Other categories can be added later easily
 */

public class LimitValue{
    public int frequency;
    public float cat1;


    protected LimitValue(int frequency, float cat1){
        this.frequency = frequency;
        this.cat1 = cat1;
    }
}
