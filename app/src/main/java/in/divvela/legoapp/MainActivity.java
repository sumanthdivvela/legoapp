package in.divvela.legoapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

    public static Utility util;
    static Context gContext;


    int legoOptionSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gContext = this.getApplicationContext();

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

        headerLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, weightPx );
        canvasLayout.setLayoutParams(layoutParams);
        canvasLayout.setY(headerHeight + util.getDevicePixels(3));

        canvasLayout.addView(GridCanvasHelper.createGridCanvasView(gContext, weightPx));

        int optionsHeight = heightPx - headerHeight - weightPx - util.getDevicePixels(5);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, optionsHeight );
        gridOptionsLayout.setLayoutParams(layoutParams);
        gridOptionsLayout.setY(headerHeight + weightPx + util.getDevicePixels(5));

        int columnWidth = Math.min(optionsHeight, weightPx/3)/5;
        //createGridCanvas();

        updateGridOptionsLayout(gridOptionsLayout, metrics);
        postApplicationLaunch();
    }


    private void updateGridOptionsLayout(RelativeLayout gridOptionsLayout, DisplayMetrics metrics){
        try{
            int widthPixels  = metrics.widthPixels;
            legoOptionSize = widthPixels/3;
            RelativeLayout gridOptionLayout;
            gridOptionsLayout.bringToFront();
            int count = gridOptionsLayout.getChildCount();
            for(int i = 0; i < count; i++){
                gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(i);
                gridOptionLayout.setLayoutParams(new RelativeLayout.LayoutParams(legoOptionSize, ViewGroup.LayoutParams.MATCH_PARENT));
                gridOptionLayout.setX(legoOptionSize*i);
            }

        }catch (Exception e){
            Log.e("Lego Options: ", e.getMessage());
        }
    }



    private void postApplicationLaunch(){

        RelativeLayout gridOptionsLayout = (RelativeLayout) findViewById(R.id.gridOptions);
        gridOptionsLayout.post(new Runnable() {
            // Post in the parent's message queue to make sure the parent
            // lays out its children before you call getHitRect()
            @Override
            public void run() {
                RelativeLayout gridOptionsLayout = (RelativeLayout) findViewById(R.id.gridOptions);
                RelativeLayout gridView = (RelativeLayout) findViewById(R.id.gridCanvas);
                GameManager manager = new GameManager(gridView, gridOptionsLayout, MainActivity.gContext);
                manager.startGame();
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
