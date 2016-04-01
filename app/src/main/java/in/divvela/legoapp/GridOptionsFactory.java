package in.divvela.legoapp;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Sumanth Divvela on 3/31/2016.
 */
public class GridOptionsFactory {

    private GridOption[] legoOptions;

    public GridOption[] getGridOptions(){

        GridOption[] gridOptions = new GridOption[3];
        Randomizer randomizer = Randomizer.getInstance();
        int[] randomInts = randomizer.getRandomInts(3,legoOptions.length,legoOptions.length);

        for(int i =0; i < randomInts.length; i++){
            gridOptions[i] = legoOptions[randomInts[i]];
        }
        return gridOptions;
    }

    public void loadGridOptions(Context context){

        XmlResourceParser xrp = context.getResources().getXml(R.xml.lego_options);
        ArrayList <GridOption> gridOptions = new ArrayList<GridOption>();
        GridOption gridOption;
        Integer id = 0;
        String colorCode = "";
        Integer[][] gridMap = {};

        try{
            xrp.next();
            int eventType = xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG
                        && xrp.getName().equalsIgnoreCase("option")) {
                    gridOption = null;
                }
                if (eventType == XmlPullParser.START_TAG
                        && xrp.getName().equalsIgnoreCase("id")) {
                    eventType = xrp.next();
                    if(eventType == XmlPullParser.TEXT)
                    {
                        id= new Integer(xrp.getText());
                    }
                }
                if (eventType == XmlPullParser.START_TAG
                        && xrp.getName().equalsIgnoreCase("color")) {
                    eventType = xrp.next();
                    if(eventType == XmlPullParser.TEXT)
                    {
                        colorCode= xrp.getText();
                    }
                }
                if (eventType == XmlPullParser.START_TAG
                        && xrp.getName().equalsIgnoreCase("map")) {
                    eventType = xrp.next();
                    if(eventType == XmlPullParser.TEXT)
                    {
                        gridMap= getGridMap(xrp.getText());
                    }
                }
                if(eventType == XmlPullParser.END_TAG && xrp.getName().equalsIgnoreCase("option"))
                {
                    gridOption = new GridOption(id,colorCode,gridMap);
                    gridOptions.add(gridOption);
                }
                eventType = xrp.next();
            }

        }catch (Exception e){
            Log.e("Options parse",e.getMessage());
        }finally {
            xrp.close();
        }
        legoOptions = new GridOption[gridOptions.size()];
        legoOptions = gridOptions.toArray(legoOptions);
    }

    private Integer[][] getGridMap(String map){

        String[] rows = map.split("],");
        String[] cols;
        String colStr;
        Integer[] colsInt;
        Integer[][] gridMap = new Integer[rows.length][];

        for(int row=0; row < rows.length; row++){
            colStr = rows[row];
            cols = colStr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            colsInt = new Integer[cols.length];
            for(int col=0; col < cols.length; col++){
                try {
                    colsInt[col] = new Integer(cols[col]);
                }catch (NumberFormatException nfe) {
                    colsInt[col] = new Integer(0);
                };
            }
            gridMap[row] = colsInt;
        }
        return gridMap;
    }
}
