package gemeenterotterdam.trillingmeterapp;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 16-9-2017.
 * Class containing limitValues (velocity) for each frequency
 */

public class LimitValueTable {
    private static LimitValueTable limitValueTable;
    public ArrayList<DataPoint <Integer>> table = null;

    public static LimitValueTable getInstance() {
        if(limitValueTable == null){
            limitValueTable = new LimitValueTable();
        }
        return limitValueTable;
    }

    private LimitValueTable() {
        table = new ArrayList<DataPoint <Integer>>();
        table.add(new DataPoint<Integer>(5, new float[]{0.20f}));
        table.add(new DataPoint<Integer>(10, new float[]{0.20f}));
        table.add(new DataPoint<Integer>(15, new float[]{0.205f}));
        table.add(new DataPoint<Integer>(20, new float[]{0.25f}));
        table.add(new DataPoint<Integer>(25, new float[]{0.275f}));
        table.add(new DataPoint<Integer>(30, new float[]{0.30f}));
        table.add(new DataPoint<Integer>(35, new float[]{0.325f}));
        table.add(new DataPoint<Integer>(40, new float[]{0.35f}));
        table.add(new DataPoint<Integer>(45, new float[]{0.375f}));
        table.add(new DataPoint<Integer>(50, new float[]{0.40f}));
    }
}
