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
import gemeenterotterdam.trillingmeterapp.R;

/**
 * Created by Marijn Otte on 28-9-2017.
 * Fragment for accelerometer graph
 */

public class AcceleroGraphFragment extends Fragment {
    GraphView graphView;
    LinearLayout layout;
    int i = 0;
    final int maxSize = 20;
    ArrayList<DataPoint> xSerie = new ArrayList<DataPoint>();
    ArrayList<DataPoint> ySerie = new ArrayList<DataPoint>();
    ArrayList<DataPoint> zSerie = new ArrayList<DataPoint>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_accelerograph, container, false);
        graphView = new GraphView(this.getActivity());
        LineGraphSeries<DataPoint> seriesX = new LineGraphSeries<>(new DataPoint[] {});
        LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<>(new DataPoint[] {});
        LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<>(new DataPoint[] {});
        seriesX.setColor(Color.RED);
        seriesY.setColor(Color.YELLOW);
        seriesZ.setColor(Color.BLUE);
        graphView.addSeries(seriesX);
        graphView.addSeries(seriesY);
        graphView.addSeries(seriesZ);

        layout = (LinearLayout) rootView.findViewById(R.id.Accelerograph);
        layout.addView(graphView);
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


        xSerie.add(new DataPoint(i, acceleration[0]));
        ySerie.add(new DataPoint(i, acceleration[1]));
        zSerie.add(new DataPoint(i, acceleration[2]));

        if(xSerie.size() > maxSize){
            xSerie.remove(0);
            ySerie.remove(0);
            zSerie.remove(0);
        }

        DataPoint[] xPoints = xSerie.toArray(new DataPoint[xSerie.size()]);
        DataPoint[] yPoints = ySerie.toArray(new DataPoint[ySerie.size()]);
        DataPoint[] zPoints = zSerie.toArray(new DataPoint[zSerie.size()]);

        serieX.resetData(xPoints);
        serieY.resetData(yPoints);
        serieZ.resetData(zPoints);

        i++;
        graphView.addSeries(serieX);
        graphView.addSeries(serieY);
        graphView.addSeries(serieZ);
    }
}
