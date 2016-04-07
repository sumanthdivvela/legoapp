package in.divvela.legoapp;

import android.widget.RelativeLayout;

/**
 * Created by KH121 on 3/21/2016.
 */
public class GridOption implements  Cloneable{

    private final String colorCode;
    private final Boolean[][] gridMap;
    private final Integer noOfRows;
    private final Integer noOfCols;
    private final String id;
    RelativeLayout view;

    public GridOption(String id,String colorCode, Boolean[][] gridMap) throws  Exception{
        this.id = id;
        this.colorCode = colorCode;
        if(gridMap == null)
        {
            throw new Exception();
        }else{
            this.gridMap = gridMap;
            this.noOfRows = gridMap.length;
            //Grid Map is expected to proper rectangle, so length of all rows will be same.
            this.noOfCols = gridMap[0].length;
        }
    };

    public String getId() { return id;    }

    public String getColorCode() {
        return colorCode;
    }

    public Boolean[][] getGridMap() {
        return gridMap;
    }

    public Integer getNoOfRows() {
        return noOfRows;
    }

    public Integer getNoOfCols() {
        return noOfCols;
    }

    public boolean isNonEmptyCell(int row, int col){
        return gridMap[row][col];
    }

    public void setView(RelativeLayout view) {
        this.view = view;
    }

    public RelativeLayout getView() {
        return view;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
