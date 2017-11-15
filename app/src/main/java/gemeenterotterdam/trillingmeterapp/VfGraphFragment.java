package gemeenterotterdam.trillingmeterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;

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
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import gemeenterotterdam.trillingmeterapp.R;

/**
 * Created by Marijn Otte on 9-10-2017.
 * Fragment for velocity graph
 */

public class VfGraphFragment extends GraphFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_vfgraph, container, false);
        setMainSettings(GraphType.LineGraphSeries);
        layout = (LinearLayout) rootView.findViewById(R.id.VFgraph);
        layout.addView(graphView);
        graphView.setTitle(getResources().getString(R.string.vfgraph));
        graphView.getGridLabelRenderer().setHorizontalAxisTitle(getResources().getString(R.string.vfxaxis));
        graphView.getGridLabelRenderer().setVerticalAxisTitle(getResources().getString(R.string.vfyaxis));
        return rootView;
    }

    /**
     * Update Velocity / Frequency graph
     * @param velocityFrequency velocity corresponding to every possible frequency in X, Y, Z direction
     *                          Graph renewed every second
     */
    public void update(ArrayList<gemeenterotterdam.trillingmeterapp.DataPoint<int[]>> velocityFrequency){
        graphView.removeAllSeries();
        LineGraphSeries<DataPoint> serieX = new LineGraphSeries<>(new DataPoint[] {});
        LineGraphSeries<DataPoint> serieY = new LineGraphSeries<>(new DataPoint[] {});
        LineGraphSeries<DataPoint> serieZ = new LineGraphSeries<>(new DataPoint[] {});
        serieX.setColor(Color.RED);
        serieY.setColor(Color.YELLOW);
        serieZ.setColor(Color.BLUE);

        for (gemeenterotterdam.trillingmeterapp.DataPoint<int[]> dp : velocityFrequency){
            serieX.appendData(new DataPoint(dp.domain[0], dp.values[0]), true, 50);
            serieY.appendData(new DataPoint(dp.domain[1], dp.values[1]), true, 50);
            serieZ.appendData(new DataPoint(dp.domain[2], dp.values[2]), true, 50);
        }

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(50);
        serieX.setTitle("X");
        serieY.setTitle("Y");
        serieZ.setTitle("Z");
        graphView.addSeries(serieX);
        graphView.addSeries(serieY);
        graphView.addSeries(serieZ);
    }


}
