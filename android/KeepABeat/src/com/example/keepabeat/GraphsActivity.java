package com.example.keepabeat;

import java.util.ArrayList;

import chart.PointOnChart;

import linechart.LineChartAttributes;
import linechart.lineChartView;
import linechart.PathAttributes;
import linechart.PathOnChart;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GraphsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int[] time_array = extras.getIntArray("numbers");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ArrayList<PointOnChart> points1 = new ArrayList<PointOnChart>();

        for(int i=0; i<=100; i++){

            float X = i;
            float Y = (float) (Math.sin(2*X)*Math.cos(2*X));

            points1.add(new PointOnChart(X,Y));
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");

        }
            //System.out.println("( X : "+ X + ", Y : "+ Y+" )");


       

        PathAttributes pathAttributes1 = new PathAttributes();
        pathAttributes1.setPointColor("#00AAAAAA");
        pathAttributes1.setPathColor("#FFAF00");
        PathOnChart path1 = new PathOnChart(points1, pathAttributes1);

        ArrayList<PathOnChart> paths = new ArrayList<PathOnChart>();
        paths.add(path1);
 

        LineChartAttributes lineChartAttributes = new LineChartAttributes();
        lineChartAttributes.setBackgroundColor("#aaabbb");
        //lineChartAttributes.setGridVisible(false);
        setContentView(new lineChartView(this, paths, lineChartAttributes));

    }
}