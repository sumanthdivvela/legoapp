package in.divvela.legoapp;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Sumanth Divvela on 3/29/2016.
 */
public class GridOptionTouchListener implements View.OnTouchListener {


    private float preX = 0;
    private float preY = 0;
    private float startX = 0;
    private int touchSlop = 5;
    private GridOption gridOption;
    private View view;
    private float ratio;

    public GridOptionTouchListener(GridOption gridOption, View view,int mainGridColumnWidth,int cellSize){
        super();
        this.gridOption = gridOption;
        this.view = view;
        this.ratio = (float)(mainGridColumnWidth-2)/cellSize;
        startX = view.getX();
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                preX = event.getX();
                preY = event.getY();
                selectGridOption();
               // Log.d("Touch", "touch start." );
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float deltaX = event.getX() - preX;
                float deltaY = event.getY() - preY;
                if (Math.abs(deltaX) > touchSlop || Math.abs(deltaY) > touchSlop) {
                    preX = event.getX();
                    preY = event.getY();
                    updateViewOffset( deltaX, deltaY);
                    //Log.d("Touch", "touch move. x : " + deltaX + " y: " + deltaY);
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                preX = 0;
                preY = 0;
                reSetViewOffset();
                //Log.d("Touch", "touch end.");
                break;
            }
        }

        return true;
    }

    private void updateViewOffset( float deltaX, float deltaY) {
        view.setX(view.getX() + deltaX);
        view.setY(view.getY() + deltaY);
    }

    private void reSetViewOffset() {
        view.animate().setDuration(2).scaleX(1).scaleY(1).translationY(0).translationX(startX);
    }

    private void selectGridOption(){
        view.animate().setDuration(2).scaleX(ratio).scaleY(ratio).translationY(-100);
    }



}
