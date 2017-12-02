package gemeenterotterdam.trillingmeterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import gemeenterotterdam.trillingmeterapp.R;

/**
 * Created by Marijn Otte on 28-9-2017.
 * Fragment for Fdom graph
 */

public class VFdomGraphFragment extends GraphFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_vfdomgraph, container, false);
        setMainSettings(GraphType.PointGraphSeries);
        layout = (LinearLayout) rootView.findViewById(R.id.VFdomgraph);
        layout.addView(graphView);
        graphView.setTitle(getResources().getString(R.string.vfdomgraph));
        graphView.getGridLabelRenderer().setHorizontalAxisTitle(getResources().getString(R.string.vfdomgraphxaxis));
        graphView.getGridLabelRenderer().setVerticalAxisTitle(getResources().getString(R.string.vfdomgraphyaxis));
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(50);
        drawLimitLine();
        return rootView;
    }

    /**
     * Update Velocity / Fdom graph
     * @param fdom Dominant frequency with corresponding velocity for every second in X, Y, Z direction
     */
    public void update(Fdom fdom){
        PointsGraphSeries<DataPoint> serieX = (PointsGraphSeries<DataPoint>)graphView.getSeries().get(0);
        PointsGraphSeries<DataPoint> serieY = (PointsGraphSeries<DataPoint>)graphView.getSeries().get(1);
        PointsGraphSeries<DataPoint> serieZ = (PointsGraphSeries<DataPoint>)graphView.getSeries().get(2);

        //sorting algorithm, sorts datapoints on x-value
        Comparator<DataPoint> comp = new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint dataPoint, DataPoint t1) {
                return (int) dataPoint.getX() - (int)t1.getX();
            }
        };

        //save all new datapoints in array, because they need to be sorted
        xSerie.add(new DataPoint(fdom.frequencies[0], fdom.velocities[0]));
        ySerie.add(new DataPoint(fdom.frequencies[1], fdom.velocities[1]));
        zSerie.add(new DataPoint(fdom.frequencies[2], fdom.velocities[2]));

        Collections.sort(xSerie, comp);
        Collections.sort(ySerie, comp);
        Collections.sort(zSerie, comp);

        cleanupSeries(serieX, serieY, serieZ);
    }

    /**
     * Draws line with limit values, obtained from LimitValueTable
     */
    public void drawLimitLine(){
        LineGraphSeries<DataPoint> limitLine = new LineGraphSeries();
        float maxFreq = LimitValueTable.getLimitValue(0);
        limitLine.appendData(new DataPoint(0,maxFreq),true, 100);
        maxFreq = LimitValueTable.getLimitValue(10);
        limitLine.appendData(new DataPoint(10,maxFreq),true, 100);
        maxFreq = LimitValueTable.getLimitValue(50);
        limitLine.appendData(new DataPoint(50,maxFreq),true, 100);

        limitLine.setTitle("Grenswaarde");
        graphView.addSeries(limitLine);
    }
}