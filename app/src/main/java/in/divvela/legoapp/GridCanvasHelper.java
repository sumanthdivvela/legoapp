package in.divvela.legoapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.divvela.legoapp.utils.Utility;

/**
 * Created by KH121 on 4/6/2016.
 */
public class GridCanvasHelper {

    public static RelativeLayout gridCanvasLayout;
    private static int gridCanvasCellWidth;
    private static int[] snapedCellXY = new int[2];
    private static float minLeft = 0;
    private static float maxLeft = 0;
    private static float minTop = 0;
    private static float maxTop = 0;

    private static ArrayList<Integer[]> mappedCells;

    public static Integer[][] gridCanvasItems =
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};


    public static  RelativeLayout createGridCanvasView(Context mContext, int parentWidth) {
        gridCanvasLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(
                R.layout.grid_option, null);

        gridCanvasLayout.setId(R.id.gridCanvas);

        TextView tView;
        int noOfCols = 10;
        int noOfRows = 10;
        gridCanvasCellWidth = parentWidth/noOfCols;
        Utility util = MainActivity.util;
        int margin = util.getDevicePixels(2);
        RelativeLayout.LayoutParams canvasLayoutParams = new RelativeLayout.LayoutParams(parentWidth, parentWidth);
        gridCanvasLayout.setLayoutParams(canvasLayoutParams);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(gridCanvasCellWidth - margin, gridCanvasCellWidth - margin);
        for (int row = 0; row < noOfRows; row++) {
            for (int col = 0; col < noOfCols; col++) {
                tView = new TextView(mContext);
                tView.setLayoutParams(layoutParams);
                tView.setBackgroundResource(R.drawable.cells_rounded_corners);
                tView.setX(gridCanvasCellWidth * col);
                tView.setY(gridCanvasCellWidth * row);
                GradientDrawable drawable = (GradientDrawable) tView.getBackground();
                drawable.setColor(Color.GRAY);
                gridCanvasLayout.addView(tView);
            }
        }
        gridCanvasLayout.setX(0);
        return gridCanvasLayout;
    }

    public static void updateGridCanvasView(GridOptionsFactory gridOptionsFactory ) {
        View tView;
        int noOfCols = 10;
        int noOfRows = 10;
        GridOption gridOption;

        Utility util = MainActivity.util;
        int cellId;

        for (int row = 0; row < noOfRows; row++) {
            for (int col = 0; col < noOfCols; col++) {
                tView = gridCanvasLayout.getChildAt((row * 10) + col);
                GradientDrawable drawable = (GradientDrawable) tView.getBackground();
                cellId = gridCanvasItems[row][col];
                if(cellId == 0){
                    drawable.setColor(Color.GRAY);
                }else{
                    gridOption = gridOptionsFactory.getGridOption(""+cellId);
                    drawable.setColor(Color.parseColor(gridOption.getColorCode()));
                }
            }
        }
    }

    public static void updateGridCanvasCell(int row, int col, GridOption gridOption){
        int noOfCols = gridOption.getNoOfCols();
        int noOfRows = gridOption.getNoOfRows() ;
        Integer gridOptionId = new Integer(gridOption.getId());
        View tView;
        for(int rowIndex = 0; rowIndex < noOfRows; rowIndex++ ){
            for(int colIndex = 0; colIndex < noOfCols; colIndex++ ){
                gridCanvasItems[rowIndex + row][colIndex + col] = gridOptionId;
                tView = gridCanvasLayout.getChildAt(((rowIndex + row) * 10) + (colIndex + col));
                GradientDrawable drawable = (GradientDrawable) tView.getBackground();
                drawable.setColor(Color.parseColor(gridOption.getColorCode()));
            }
        }
    }

    public static boolean identifyBoundingRect(float left, float top, GridOption gridOption){
        View view = gridOption.getView();
        if(view == null)
            return false;

        float viewLeft = ((RelativeLayout)view.getParent()).getX();

        if (left > minLeft-viewLeft && left < maxLeft - viewLeft - view.getMeasuredWidth()  && top > -maxTop - 5 && top < 0) {

            int colIndex = Math.round((viewLeft + left) / gridCanvasCellWidth);
            int rowIndex = Math.round((maxTop+top)/ gridCanvasCellWidth);

            if (colIndex < 10 && rowIndex < 10 && (snapedCellXY[0] != rowIndex || snapedCellXY[1] != colIndex)) {

                view.setX(left);
                view.setY(top);

                if(colIndex + gridOption.getNoOfCols() <= 10 && rowIndex + gridOption.getNoOfRows() <= 10
                        && isOptionSpaceAvailable(rowIndex, colIndex, gridOption)){

                    view.setX(colIndex * gridCanvasCellWidth - viewLeft );
                    view.setY(rowIndex * gridCanvasCellWidth - maxTop - 2);

                    snapedCellXY[0] = rowIndex;
                    snapedCellXY[1] = colIndex;

                    return true;
                }else{
                    snapedCellXY[0] = -1;
                    snapedCellXY[1] = -1;
                }


            }else if( snapedCellXY[0] != rowIndex || snapedCellXY[1] != colIndex){
                view.setX(left);
                view.setY(top);
            }

        }
        return false;
    }

    private  static boolean isOptionSpaceAvailable(int rowIndex, int colIndex, GridOption option){
        mappedCells = new ArrayList<Integer[]>();
        int noOfRows =  option.getNoOfRows();
        int noOfCols =  option.getNoOfCols();
        int rows = rowIndex + noOfRows;
        int cols = colIndex + noOfCols;
        try{
            for (int row = rowIndex; row < rows; row++) {
                for (int col = colIndex; col < cols; col++) {
                    if (gridCanvasItems[row][col].intValue() != 0
                            && option.isNonEmptyCell(row,col)) {
                        return false;
                    } else {
                        if (option.isNonEmptyCell(row - rowIndex ,col-colIndex)) {
                            mappedCells.add(new Integer[]{row, col});
                        }
                    }
                }
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public static void snapGridOption(GridOption gridOption){

        if (mappedCells != null && mappedCells.size() > 0) {
            Integer optionId =  new Integer(gridOption.getId());
            int rowIndex;
            int colIndex;
            View tView;
            Integer[] mappedCell;
            int colorCode = Color.parseColor(gridOption.getColorCode());
            for (int i = 0; i < mappedCells.size(); i++)
            {
                mappedCell = mappedCells.get(i);
                rowIndex = mappedCell[0];
                colIndex = mappedCell[1];
                gridCanvasItems[rowIndex][colIndex] = optionId;
                tView = gridCanvasLayout.getChildAt((rowIndex*10) + colIndex);
                GradientDrawable drawable = (GradientDrawable) tView.getBackground();
                drawable.setColor(colorCode);
            }
        }
    }

   // private static

    public static void setBoundingRect(float x, float v, float y, float v1) {
        minLeft = x;
        maxLeft = v;
        minTop = y;
        maxTop = v1;
    }
}
