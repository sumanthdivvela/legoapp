package in.divvela.legoapp;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.GridView;
import android.widget.RelativeLayout;

/**
 * Created by Sumanth Divvela on 3/29/2016.
 */
public class GridOptionTouchListener implements View.OnTouchListener {

    private float preX = 0;
    private float preY = 0;
    private int touchSlop = 1;
    private GridOption gridOption;
    private View view;
    private int mainGridColumnWidth;
    private int cellSize;

    public GridOptionTouchListener(GridOption gridOption, View view,int mainGridColumnWidth,int cellSize){
        super();
        this.gridOption = gridOption;
        this.view = view;
        this.mainGridColumnWidth = mainGridColumnWidth;
        this.cellSize = cellSize;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                preX = event.getX();
                preY = event.getY();
                selectGridOption();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float deltaX = event.getX() - preX;
                float deltaY = event.getY() - preY;
                preX = event.getX();
                preY = event.getY();
                if (Math.abs(deltaX) > touchSlop || Math.abs(deltaY) > touchSlop) {
                    updateViewOffset( deltaX, deltaY);
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                preX = 0;
                preY = 0;
                reSetViewOffset();
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
        RelativeLayout layout = (RelativeLayout)view.getParent();
        GridView gridView = (GridView)view;
        gridView.animate().setDuration(2).scaleX(1).scaleY(1).translationY(0).translationX((layout.getMeasuredWidth() - cellSize * gridOption.getNoOfcols()) / 2);
    }

    private void selectGridOption(){
        GridView gridView = (GridView)view;
        double ratio = (double)mainGridColumnWidth/cellSize;
        gridView.animate().setDuration(2).scaleX((float) ratio).scaleY((float) ratio).translationY(-100);
    }



}
