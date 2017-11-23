package gemeenterotterdam.trillingmeterapp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static gemeenterotterdam.trillingmeterapp.GraphFragment.GraphType.PointGraphSeries;

/**
 * Created by Marijn Otte on 26-10-2017.
 * Abstract class for different Graph Fragments
 */

public abstract class GraphFragment extends Fragment {
    //maxSize: amount of datapoints stored in memory of graph
    protected final int maxSize = 20;
    protected ArrayList<DataPoint> xSerie = new ArrayList<com.jjoe64.graphview.series.DataPoint>();
    protected ArrayList<DataPoint> ySerie = new ArrayList<com.jjoe64.graphview.series.DataPoint>();
    protected ArrayList<DataPoint> zSerie = new ArrayList<com.jjoe64.graphview.series.DataPoint>();
    protected GraphView graphView;
    protected LinearLayout layout;
    protected Date time;

    public enum GraphType{
        LineGraphSeries, PointGraphSeries
    }

    //set layout of graph
    protected void setMainSettings(GraphType gt){
        graphView = new GraphView(this.getActivity());
        time = Calendar.getInstance().getTime();
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
                graphView.getLegendRenderer().setVisible(true);
                seriesLineX.setTitle("X");
                seriesLineY.setTitle("Y");
                seriesLineZ.setTitle("Z");
                break;
            case PointGraphSeries:
                PointsGraphSeries<DataPoint> seriesPointsX = new PointsGraphSeries<>(new DataPoint[] {});
                PointsGraphSeries<DataPoint> seriesPointsY = new PointsGraphSeries<>(new DataPoint[] {});
                PointsGraphSeries<DataPoint> seriesPointsZ = new PointsGraphSeries<>(new DataPoint[] {});
                seriesPointsX.setColor(Color.RED);
                seriesPointsY.setColor(Color.YELLOW);
                seriesPointsZ.setColor(Color.BLUE);
                seriesPointsX.setSize(10);
                seriesPointsY.setSize(10);
                seriesPointsZ.setSize(10);

                graphView.addSeries(seriesPointsX);
                graphView.addSeries(seriesPointsY);
                graphView.addSeries(seriesPointsZ);
                graphView.getLegendRenderer().setVisible(true);
                seriesPointsX.setTitle("X");
                seriesPointsY.setTitle("Y");
                seriesPointsZ.setTitle("Z");
                break;
        }
    }

    protected void cleanupSeries(LineGraphSeries<DataPoint> serieX, LineGraphSeries<DataPoint> serieY, LineGraphSeries<DataPoint> serieZ ){
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
        time = Calendar.getInstance().getTime();
    }

    protected void cleanupSeries(PointsGraphSeries<DataPoint> serieX, PointsGraphSeries<DataPoint> serieY, PointsGraphSeries<DataPoint> serieZ ){
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
        time = Calendar.getInstance().getTime();
    }
}
