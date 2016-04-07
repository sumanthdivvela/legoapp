package in.divvela.legoapp;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;

import in.divvela.legoapp.utils.Utility;

/**
 * Created by Sumanth Divvela on 3/31/2016.
 */
public class GridOptionsFactory {


    private GridOption[] legoOptions;
    private HashMap<String,GridOption> gridOptions = new HashMap<String, GridOption>();

    public ArrayList<GridOption>  getGridOptions(Context context,int parentWidth){

        ArrayList<GridOption> gridOptions = new ArrayList<GridOption>();
        Randomizer randomizer = Randomizer.getInstance();
        int[] randomInts = randomizer.getRandomInts(3,legoOptions.length,legoOptions.length);
        try{
            for(int i =0; i < randomInts.length; i++){
                gridOptions.add((GridOption)legoOptions[randomInts[i]].clone());
            }
            createGridOptionViews(context,gridOptions, parentWidth);
        }catch(CloneNotSupportedException e){

        }
        return gridOptions;
    }

    public GridOption getGridOption(String id){
        return gridOptions.get(id);
    }

    public void loadGridOptions(Context context){

        XmlResourceParser xrp = context.getResources().getXml(R.xml.lego_options);

        GridOption gridOption;
        String id = "";
        String colorCode = "";
        Boolean[][] gridMap = {};

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
                        id= xrp.getText();
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
                    gridOptions.put(id,gridOption);
                }
                eventType = xrp.next();
            }

        }catch (Exception e){
            Log.e("Options parse",e.getMessage());
        }finally {
            xrp.close();
        }
        legoOptions = new GridOption[gridOptions.size()];
        legoOptions = gridOptions.values().toArray(legoOptions);

    }

    private Boolean[][] getGridMap(String map){

        String[] rows = map.split("],");
        String[] cols;
        String colStr;
        Boolean[] colsInt;
        Boolean[][] gridMap = new Boolean[rows.length][];

        for(int row=0; row < rows.length; row++){
            colStr = rows[row];
            cols = colStr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            colsInt = new Boolean[cols.length];
            for(int col=0; col < cols.length; col++){
                try {
                    colsInt[col] = new Boolean(cols[col].equals("1") ? true: false);
                }catch (NumberFormatException nfe) {
                    colsInt[col] = new Boolean(false);
                };
            }
            gridMap[row] = colsInt;
        }
        return gridMap;
    }

    public void createGridOptionViews(Context mContext,  ArrayList<GridOption>  gridOptions, int parentWidth) {
        int optionCount = gridOptions.size();
        for(int i =0; i< optionCount; i++){
            GridOption gridOption = gridOptions.get(i);
            gridOption.setView(createGridOptionView(mContext, parentWidth, gridOption));
        }
        return;
    }

    public RelativeLayout createGridOptionView(Context mContext, int parentWidth, GridOption gridOption) {
        RelativeLayout relLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(
                R.layout.grid_option, null);

        TextView tView;
        int noOfCols = gridOption.getNoOfCols();
        int noOfRows = gridOption.getNoOfRows();
        Boolean[][] gridMap = gridOption.getGridMap();
        Boolean[] rows;
        int cellWidth = parentWidth / 5;
        Utility util = MainActivity.util;
        int margin = util.getDevicePixels(2);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(cellWidth-margin, cellWidth-margin);

        //layoutParams.setMargins(margin,margin,margin,margin);

        for (int row = 0; row < noOfRows; row++) {
            rows = gridMap[row];
            for (int col = 0; col < noOfCols; col++) {
                tView = new TextView(mContext);
                tView.setLayoutParams(layoutParams);
                tView.setBackgroundResource(R.drawable.cells_rounded_corners);
                tView.setX(cellWidth * col);
                tView.setY(cellWidth * row);

                GradientDrawable drawable = (GradientDrawable) tView.getBackground();
                if (rows[col]) {
                    drawable.setColor(Color.parseColor(gridOption.getColorCode()));
                } else {
                    drawable.setColor(Color.TRANSPARENT);
                }
                relLayout.setX((parentWidth- cellWidth * gridOption.getNoOfCols())/2 );
                relLayout.addView(tView);
            }
        }
        return  relLayout;
    }

}
