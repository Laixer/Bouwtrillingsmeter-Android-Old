package gemeenterotterdam.trillingmeterapp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

import static gemeenterotterdam.trillingmeterapp.GraphFragment.GraphType.PointGraphSeries;

/**
 * Created by Marijn Otte on 26-10-2017.
 * Abstract class for different Graph Fragments
 */

public abstract class GraphFragment extends Fragment {
    //maxSize: amount of datapoints stored in memory of graph
    protected final int maxSize = 5;
    protected ArrayList<DataPoint> xSerie = new ArrayList<com.jjoe64.graphview.series.DataPoint>();
    protected ArrayList<DataPoint> ySerie = new ArrayList<com.jjoe64.graphview.series.DataPoint>();
    protected ArrayList<DataPoint> zSerie = new ArrayList<com.jjoe64.graphview.series.DataPoint>();
    protected GraphView graphView;
    protected LinearLayout layout;

    public enum GraphType{
        LineGraphSeries, PointGraphSeries
    }

    //set layout of graph
    protected void setMainSettings(GraphType gt){
        graphView = new GraphView(this.getActivity());
        switch(gt){
            case LineGraphSeries:
                LineGraphSeries<DataPoint> seriesLineX = new LineGraphSeries<>(new DataPoint[] {});
                LineGraphSeries<DataPoint> seriesLineY = new LineGraphSeries<>(new DataPoint[] {});
                LineGraphSeries<DataPoint> seriesLineZ = new LineGraphSeries<>(new DataPoint[] {});
                seriesLineX.setColor(Color.RED);
                seriesLineY.setColor(Color.YELLOW);
                seriesLineZ.setColor(Color.BLUE);
                graphView.addSeries(seriesLineX);
                graphView.addSeries(seriesLineY);
                graphView.addSeries(seriesLineZ);
                break;
            case PointGraphSeries:
                PointsGraphSeries<DataPoint> seriesPointsX = new PointsGraphSeries<>(new DataPoint[] {});
                PointsGraphSeries<DataPoint> seriesPointsY = new PointsGraphSeries<>(new DataPoint[] {});
                PointsGraphSeries<DataPoint> seriesPointsZ = new PointsGraphSeries<>(new DataPoint[] {});
                seriesPointsX.setColor(Color.RED);
                seriesPointsY.setColor(Color.YELLOW);
                seriesPointsZ.setColor(Color.BLUE);
                graphView.addSeries(seriesPointsX);
                graphView.addSeries(seriesPointsY);
                graphView.addSeries(seriesPointsZ);
                break;
        }
    }
}
