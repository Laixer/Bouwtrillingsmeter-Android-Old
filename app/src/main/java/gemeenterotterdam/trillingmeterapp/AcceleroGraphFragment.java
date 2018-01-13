package gemeenterotterdam.trillingmeterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import gemeenterotterdam.trillingmeterapp.R;

/**
 * Created by Marijn Otte on 28-9-2017.
 * Fragment for accelerometer graph
 */

public class AcceleroGraphFragment extends GraphFragment {

    /**
     *
     * layout settings of accelerometer graph
     * @return view of acclerometer graph
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_accelerograph, container, false);
        setMainSettings(GraphType.LineGraphSeries);
        layout = (LinearLayout) rootView.findViewById(R.id.Accelerograph);
        layout.addView(graphView);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle(getResources().getString(R.string.accelerationgraphxaxis));
        graphView.getGridLabelRenderer().setVerticalAxisTitle(Html.fromHtml(getResources().getString(R.string.accelerationgraphyaxis)).toString());
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(10);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity(), new SimpleDateFormat("HH:mm:ss")));
        graphView.getGridLabelRenderer().setTextSize(20f);
        graphView.getGridLabelRenderer().reloadStyles();
        return rootView;
    }

    /**
     * Update Acceleration graph
     * @param acceleration max frequency for every second in X, Y, Z direction
     */
    public void update(float[] acceleration){
        LineGraphSeries<DataPoint> serieX = (LineGraphSeries<DataPoint>)graphView.getSeries().get(0);
        LineGraphSeries<DataPoint> serieY = (LineGraphSeries<DataPoint>)graphView.getSeries().get(1);
        LineGraphSeries<DataPoint> serieZ = (LineGraphSeries<DataPoint>)graphView.getSeries().get(2);
        xSerie.add(new DataPoint(time, acceleration[0]));
        ySerie.add(new DataPoint(time, acceleration[1]));
        zSerie.add(new DataPoint(time, acceleration[2]));
        graphView.getViewport().setMaxY(Math.max(10, graphView.getViewport().getMaxY(true)));
        cleanupSeries(serieX, serieY, serieZ);

    }
}
