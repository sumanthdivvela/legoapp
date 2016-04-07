package in.divvela.legoapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Sumath Divvela on 4/4/2016.
 */
public class GridOptionHelper {

    public static View createGridOptionView(GridOption gridOption, Context mContext, int parentWidth) {
        RelativeLayout relLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(
                R.layout.grid_option, null);

        TextView tView;
        int noOfCols = gridOption.getNoOfCols();
        int noOfRows = gridOption.getNoOfRows();
        Boolean[][] gridMap = gridOption.getGridMap();
        Boolean[] rows;
        int cellWidth = parentWidth / 5;


        for (int row = 0; row < noOfRows; row++) {
            rows = gridMap[row];
            for (int col = 0; col < noOfCols; col++) {
                tView = new TextView(mContext);
                tView.setLayoutParams(new RelativeLayout.LayoutParams(cellWidth, cellWidth));
                tView.setBackgroundResource(R.drawable.cells_rounded_corners);
                GradientDrawable drawable = (GradientDrawable) tView.getBackground();
                if (rows[col]) {
                    drawable.setColor(Color.parseColor(gridOption.getColorCode()));
                } else {
                    drawable.setColor(Color.TRANSPARENT);
                }
                relLayout.addView(tView);
            }
        }
        return relLayout;
    }
}
