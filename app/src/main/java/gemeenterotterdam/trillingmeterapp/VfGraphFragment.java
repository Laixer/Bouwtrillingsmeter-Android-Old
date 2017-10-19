package gemeenterotterdam.trillingmeterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
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
 * Created by Marijn Otte on 9-10-2017.
 * Fragment for velocity graph
 */

public class VfGraphFragment extends Fragment {
    GraphView graphView;
    LinearLayout layout;
    int i = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_vfgraph, container, false);
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

        layout = (LinearLayout) rootView.findViewById(R.id.VFgraph);
        layout.addView(graphView);
        return rootView;
    }

    /**
     * Update Acceleration graph
     * @param fdom velocity corresponding to dominant frequency for every second in X, Y, Z direction
     */
    public void update(Fdom fdom){
        graphView.removeAllSeries();
        LineGraphSeries<DataPoint> serieX = new LineGraphSeries<>(new DataPoint[] {});
        LineGraphSeries<DataPoint> serieY = new LineGraphSeries<>(new DataPoint[] {});
        LineGraphSeries<DataPoint> serieZ = new LineGraphSeries<>(new DataPoint[] {});
        serieX.appendData(new DataPoint(fdom.frequencies[0], fdom.velocities[0]), true, 40);
        serieY.appendData(new DataPoint(fdom.frequencies[1], fdom.velocities[1]), true, 40);
        serieZ.appendData(new DataPoint(fdom.frequencies[2], fdom.velocities[2]), true, 40);
        i++;
        graphView.addSeries(serieX);
        graphView.addSeries(serieY);
        graphView.addSeries(serieZ);
    }
}
