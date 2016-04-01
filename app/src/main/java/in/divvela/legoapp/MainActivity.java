package in.divvela.legoapp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;

import in.divvela.legoapp.utils.Utility;

/**
 * Created by Sumanth on 3/16/2016.
 */
public class  MainActivity extends Activity {

    private Utility util;
    private GridOption[] gridOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        util = new Utility(this);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int heightPx = metrics.heightPixels;
        int weightPx = metrics.widthPixels;

        RelativeLayout headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);
        RelativeLayout canvasLayout = (RelativeLayout) findViewById(R.id.gridCanvasLayout);
        RelativeLayout gridOptionsLayout = (RelativeLayout) findViewById(R.id.gridOptions);
        int headerHeight = heightPx * 10/100;
        int canvasHeight = weightPx * 60/100;

        headerLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, weightPx );
        canvasLayout.setLayoutParams(layoutParams);
        canvasLayout.setY(headerHeight);

        int optionsHeight = heightPx - headerHeight - weightPx;
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx - headerHeight - weightPx );
        gridOptionsLayout.setLayoutParams(layoutParams);
        gridOptionsLayout.setY(headerHeight + weightPx);

        int columnWidth = optionsHeight/5;
        createGridCanvas();
        createGridOptions(gridOptionsLayout,metrics,columnWidth);
        addTouchListeners();
    }



    private int createGridCanvas(){

        GridView gridView = (GridView) findViewById(R.id.gridCanvas);
        gridView.setAdapter(new CellAdapter(this));

        //Set column width and set height to width of grid.
        int gridWidth = gridView.getWidth();
        int columnWidth = gridWidth/10;
        columnWidth = gridView.getColumnWidth();
        gridView.setMinimumHeight(gridWidth);
        return columnWidth;
    }

    private void createGridOptions(RelativeLayout gridOptionsLayout, DisplayMetrics metrics, int mainGridColumnWidth ){
        GridOptionsFactory gridOptionsFactory = new GridOptionsFactory();
        gridOptionsFactory.loadGridOptions(this);

        try{
            gridOptions = gridOptionsFactory.getGridOptions();
            GridOption gridOption;
            RelativeLayout gridOptionLayout;
            GridView gridView;
            gridOptionsLayout.bringToFront();

            int widthPixels  = metrics.widthPixels;
            int heightPx = metrics.heightPixels;
            int cellSize = Math.min(widthPixels/15, mainGridColumnWidth);
            int gridSize = widthPixels / 3;

            for(int i = 0; i < gridOptions.length; i++){
                gridOption = gridOptions[i];
                gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(i);

                RelativeLayout.LayoutParams parentLayoutParams = new RelativeLayout.LayoutParams(gridSize, ViewGroup.LayoutParams.MATCH_PARENT);
                gridOptionLayout.setLayoutParams(parentLayoutParams);
                gridOptionLayout.setX(i * gridSize);

                gridView = (GridView)gridOptionLayout.getChildAt(0);
                gridView.setAdapter(new GridOptionAdapter(this, gridOption));
                gridView.setNumColumns(gridOption.getNoOfcols());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(cellSize * gridOption.getNoOfcols(), cellSize * gridOption.getNoOfRows());
                gridView.setLayoutParams(layoutParams);

                gridView.setX( (gridSize -cellSize * gridOption.getNoOfcols())/2 );


            }
        }catch (Exception e){
            Log.e("Lego Options: ", e.getMessage());
        }
    }

    private void addTouchListeners(){
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.headerLayout);

        mainLayout.post(new Runnable() {
            // Post in the parent's message queue to make sure the parent
            // lays out its children before you call getHitRect()
            @Override
            public void run() {
                RelativeLayout gridOptionsLayout = (RelativeLayout) findViewById(R.id.gridOptions);
                RelativeLayout gridOptionLayout;
                GridOption gridOption;
                GridView gridView;

                GridView gridCanvasView = (GridView) findViewById(R.id.gridCanvas);

                for(int i = 0; i < gridOptions.length; i++) {
                    gridOption = gridOptions[i];
                    gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(i);
                    gridView = (GridView)gridOptionLayout.getChildAt(0);
                    gridOptionLayout.setOnTouchListener(new GridOptionTouchListener(gridOption,gridView,gridCanvasView.getMeasuredWidth()/10, gridView.getMeasuredWidth()/gridOption.getNoOfcols()));
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        /* TODO */
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         /* TODO */
    }
}
