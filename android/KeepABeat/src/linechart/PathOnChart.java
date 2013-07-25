package linechart;

import java.util.ArrayList;

import chart.PointOnChart;

public class PathOnChart {

    PathAttributes attributes;

    ArrayList<PointOnChart> points;

    public PathOnChart(ArrayList<PointOnChart> points, PathAttributes pathAttributes) {

        this.attributes = pathAttributes;
        this.points = points;
    }
}