package in.divvela.legoapp;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * Created by Sumanth Divvela on 3/29/2016.
 */
public class GridOptionTouchListener implements View.OnTouchListener {


    private float x1 = 0;
    private float y1 = 0;
    private float x2 = 0;
    private float y2 = 0;
    private float startX = 0;
    private float startY = 0;
    private int touchSlop = 5;
    private int id;

    private float ratio;
    private GridOption gridOption;
    private Boolean gridOptionMatched = false;

    GameManager gameManager;
    public GridOptionTouchListener(int id, GameManager gameManager){
        super();
        this.id = id;
        this.gameManager = gameManager;
    }

    public void setGridOption(GridOption gridOption) {
        this.gridOption = gridOption;
        this.setView(gridOption.getView());
    }

    public GridOption getGridOption() {
        return gridOption;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setView(View view) {
        startX = view.getX();
        startY = view.getY();
        view.setPivotX(0);
        view.setPivotY(0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                x2=x1 = event.getX();
                y2=y1 = event.getY();
                onTouchStart(v,gridOption);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float deltaX = event.getX() - x2;
                float deltaY = event.getY() - y2;
                if (Math.abs(deltaX) > touchSlop || Math.abs(deltaY) > touchSlop) {
                    x2 = event.getX();
                    y2 = event.getY();
                    updateViewOffset(v,x2 - x1, y2 - y1);

                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                x1 = y2 = x2 = y2 = 0;
                onTouchEnd(v,gridOption);
                break;
            }
        }
        return true;
    }

    private void updateViewOffset( View v,float deltaX, float deltaY) {
        onTouchMove(v,gridOption, deltaX, deltaY);
    }


    public void onTouchStart(View v, GridOption gridOption){
        View view = gridOption.getView();
        v.bringToFront();
        view.bringToFront();
        view.animate().setDuration(2).scaleX(ratio).scaleY(ratio).translationY(-100).translationX(startX);
    }

    public void onTouchMove(View v,GridOption gridOption, float deltaX, float deltaY){

        float left = startX + deltaX;
        float top = startY + deltaY - 100;

        gridOptionMatched= GridCanvasHelper.identifyBoundingRect(left, top, gridOption);

    }



    public void onTouchEnd(View v,GridOption gridOption){
        if(gridOptionMatched) {
            v.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            GridCanvasHelper.snapGridOption(gridOption);
            gameManager.handleOptionMatch(gridOption, id);
        }else{
            View view = gridOption.getView();
            if(view != null){
                view.animate().setDuration(2).scaleX(1).scaleY(1).translationY(0).translationX(startX);
            }
        }
    }

}
