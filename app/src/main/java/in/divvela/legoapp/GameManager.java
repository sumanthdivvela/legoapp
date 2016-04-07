package in.divvela.legoapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Sumanth Divvela on 4/6/2016.
 */
public class GameManager {

    public GridOptionsFactory gridOptionsFactory;
    private RelativeLayout gridCanvas;
    private  RelativeLayout gridOptionsLayout;
    private Context context;
    private float zoomScale;
    private ArrayList<GridOption> currentLegoOptions;
    private GridOptionTouchListener[] touchListeners;

    public GameManager(RelativeLayout gridCanvas, RelativeLayout gridOptionsLayout,Context mContext){
        this.gridOptionsLayout = gridOptionsLayout;
        this.gridCanvas = gridCanvas;
        this.context = mContext;
        this.zoomScale = (float)(gridCanvas.getMeasuredWidth()/10)/(float)(gridOptionsLayout.getMeasuredWidth() / 15);
    }


    public void initializeGame(){
        touchListeners = new GridOptionTouchListener[3];
        for(int i = 0;i < 3; i++ ){
            touchListeners[i] = new GridOptionTouchListener(i,this);
            touchListeners[i].setRatio((float) zoomScale);
        }
        GridCanvasHelper.setBoundingRect(gridCanvas.getX(), gridCanvas.getX() + gridCanvas.getMeasuredWidth(), ((RelativeLayout)gridCanvas.getParent()).getY(),  gridCanvas.getMeasuredHeight());


    }

    public void restoreGameState(){

    }

    public void saveGameState(){

    }

    public void updateGridCanvas(){
        GridCanvasHelper.updateGridCanvasView(gridOptionsFactory);
    }

    public void startGame(){
        gridOptionsFactory = new GridOptionsFactory();
        gridOptionsFactory.loadGridOptions(context);

        removeTouchEventsForOption();
        initializeGame();
        restoreGameState();
        updateGridCanvas();
        createGridOptions();


    }

    private void createGridOptions( ){
        try{
            GridOption gridOption;
            RelativeLayout gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(0);
            currentLegoOptions = gridOptionsFactory.getGridOptions(context,gridOptionLayout.getMeasuredWidth());
            RelativeLayout gridOptionView;
            gridOptionsLayout.bringToFront();
            for(int i = 0; i < currentLegoOptions.size(); i++){
                gridOption = currentLegoOptions.get(i);
                gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(i);
                gridOptionView = gridOption.getView();
                gridOptionLayout.addView(gridOptionView);
                touchListeners[i].setGridOption(gridOption);
            }
            addTouchEventsForOption();
        }catch (Exception e){
            Log.e("Lego Options: ", e.getMessage());
        }
    }

    public void addTouchEventsForOption(){
        RelativeLayout gridOptionLayout;
        for(int i = 0; i < gridOptionsLayout.getChildCount(); i++) {
            gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(i);
            gridOptionLayout.setOnTouchListener(touchListeners[i]);
        }
    }

    public void removeTouchEventsForOption(){
        RelativeLayout gridOptionLayout;
        for(int i = 0; i < gridOptionsLayout.getChildCount(); i++) {
            gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(i);
            gridOptionLayout.setOnTouchListener(null);
        }
    }

    public void handleOptionMatch(GridOption option, int listenerId){
        RelativeLayout gridOptionLayout = (RelativeLayout)gridOptionsLayout.getChildAt(listenerId);
        gridOptionLayout.setLayoutTransition(null);
        gridOptionLayout.removeView(option.getView());
        option.setView(null);
        currentLegoOptions.remove(option);
        if(currentLegoOptions.size() == 0){
            createGridOptions();
        }
    }

}
