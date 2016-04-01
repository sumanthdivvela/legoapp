package in.divvela.legoapp;

/**
 * Created by KH121 on 3/21/2016.
 */
public class GridOption {

    private final String colorCode;
    private final Integer[][] gridMap;
    private final Integer noOfRows;
    private final Integer noOfcols;
    private final Integer id;

    public GridOption(Integer id,String colorCode, Integer[][] gridMap)throws  Exception{
        this.id = id;
        this.colorCode = colorCode;
        if(gridMap == null)
        {
            throw new Exception();
        }else{
            this.gridMap = gridMap;
            this.noOfRows = gridMap.length;
            //Grid Map is expected to proper rectangle, so length of all rows will be same.
            this.noOfcols = gridMap[0].length;
        }
    };



    public Integer getId() { return id;    }

    public String getColorCode() {
        return colorCode;
    }

    public Integer[][] getGridMap() {
        return gridMap;
    }

    public Integer getNoOfRows() {
        return noOfRows;
    }

    public Integer getNoOfcols() {
        return noOfcols;
    }
}
