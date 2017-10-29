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
}