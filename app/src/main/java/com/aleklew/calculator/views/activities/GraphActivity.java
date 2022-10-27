package com.aleklew.calculator.views.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.aleklew.calculator.R;
import com.aleklew.calculator.modules.models.ImaginaryNumber;

public class GraphActivity extends AppCompatActivity {

    private GraphView graphView;
    private ImaginaryNumber result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_graph);

        if (savedInstanceState != null) {
            result = savedInstanceState.getParcelable("number");
        } else {
            result = getIntent().getParcelableExtra("number");
        }

        this.graphView = (GraphView) findViewById(R.id.graph);

        // enable scaling and scrolling
        this.graphView.getViewport().setScalable(true);
        this.graphView.getViewport().setScalableY(true);
        this.graphView.getViewport().setScrollable(true);
        this.graphView.getViewport().setScrollableY(true);

        this.graphView.getViewport().setYAxisBoundsManual(true);
        this.graphView.getViewport().setMinY(-150);
        this.graphView.getViewport().setMaxY(150);

        this.graphView.getViewport().setXAxisBoundsManual(true);
        this.graphView.getViewport().setMinX(-150);
        this.graphView.getViewport().setMaxX(150);

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(result.getNumber(), result.getImaginaryPart())
        });
        this.graphView.addSeries(series);
        series.setShape(PointsGraphSeries.Shape.POINT);

        findViewById(R.id.back_button).setOnClickListener((v) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("number", result);
    }
}
