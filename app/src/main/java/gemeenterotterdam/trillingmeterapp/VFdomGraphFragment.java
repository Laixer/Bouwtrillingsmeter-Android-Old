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

public class VFdomGraphFragment extends Fragment {
    GraphView graphView;
    LinearLayout layout;
    ArrayList<DataPoint> xSerie = new ArrayList<DataPoint>();
    ArrayList<DataPoint> ySerie = new ArrayList<DataPoint>();
    ArrayList<DataPoint> zSerie = new ArrayList<DataPoint>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_vfdomgraph, container, false);
        graphView = new GraphView(this.getActivity());
        PointsGraphSeries<DataPoint> seriesX = new PointsGraphSeries<>(new DataPoint[] {});
        PointsGraphSeries<DataPoint> seriesY = new PointsGraphSeries<>(new DataPoint[] {});
        PointsGraphSeries<DataPoint> seriesZ = new PointsGraphSeries<>(new DataPoint[] {});
        seriesX.setColor(Color.RED);
        seriesY.setColor(Color.YELLOW);
        seriesZ.setColor(Color.BLUE);
        graphView.addSeries(seriesX);
        graphView.addSeries(seriesY);
        graphView.addSeries(seriesZ);

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


        //arraylist to array
        DataPoint[] xPoints = xSerie.toArray(new DataPoint[xSerie.size()]);
        DataPoint[] yPoints = ySerie.toArray(new DataPoint[ySerie.size()]);
        DataPoint[] zPoints = zSerie.toArray(new DataPoint[zSerie.size()]);

        serieX.resetData(xPoints);
        serieY.resetData(yPoints);
        serieZ.resetData(zPoints);
        graphView.addSeries(serieX);
        graphView.addSeries(serieY);
        graphView.addSeries(serieZ);
    }
}