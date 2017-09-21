package gemeenterotterdam.trillingmeterapp;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 16-9-2017.
 * Class containing limitValues (velocity) for each frequency
 */

public class LimitValueTable {
    private static LimitValueTable limitValueTable;
    private ArrayList<LimitValue> table = null;

    public static LimitValueTable getInstance() {
        if(limitValueTable == null){
            limitValueTable = new LimitValueTable();
        }
        return limitValueTable;
    }

    private LimitValueTable() {
        table = new ArrayList<LimitValue>();
        table.add(new LimitValue(5, 20f));
        table.add(new LimitValue(10, 20f));
        table.add(new LimitValue(15, 20.5f));
        table.add(new LimitValue(20, 25f));
        table.add(new LimitValue(25, 27.5f));
        table.add(new LimitValue(30, 30f));
        table.add(new LimitValue(35, 32.5f));
        table.add(new LimitValue(40, 35f));
        table.add(new LimitValue(45, 37.5f));
        table.add(new LimitValue(50, 40f));
    }

    public ArrayList<LimitValue> getTable(){
        return table;
    }
}
