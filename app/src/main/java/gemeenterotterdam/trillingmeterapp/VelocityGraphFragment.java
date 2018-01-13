package gemeenterotterdam.trillingmeterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import gemeenterotterdam.trillingmeterapp.R;

/**
 * Created by Marijn Otte on 9-10-2017.
 * Fragment for velocity graph
 */

public class VelocityGraphFragment extends GraphFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_velocitygraph, container, false);
        setMainSettings(GraphType.LineGraphSeries);
        layout = (LinearLayout) rootView.findViewById(R.id.Velocitygraph);
        layout.addView(graphView);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle(getResources().getString(R.string.velocitygraphxaxis));
        graphView.getGridLabelRenderer().setVerticalAxisTitle(getResources().getString(R.string.velocitygraphyaxis));
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(4);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity(), new SimpleDateFormat("HH:mm:ss")));
        graphView.getGridLabelRenderer().setTextSize(20f);
        graphView.getGridLabelRenderer().reloadStyles();
        return rootView;
    }

    /**
     * Update Acceleration graph
     * @param fdom velocity corresponding to dominant frequency for every second in X, Y, Z direction
     */
    public void update(Fdom fdom){
        LineGraphSeries<DataPoint> serieX = (LineGraphSeries<DataPoint>)graphView.getSeries().get(0);
        LineGraphSeries<DataPoint> serieY = (LineGraphSeries<DataPoint>)graphView.getSeries().get(1);
        LineGraphSeries<DataPoint> serieZ = (LineGraphSeries<DataPoint>)graphView.getSeries().get(2);
        xSerie.add(new DataPoint(time, fdom.velocities[0]));
        ySerie.add(new DataPoint(time, fdom.velocities[1]));
        zSerie.add(new DataPoint(time, fdom.velocities[2]));
        graphView.getViewport().setMaxY(Math.max(4, graphView.getViewport().getMaxY(true)));
        cleanupSeries(serieX, serieY, serieZ);
    }
}
