package in.divvela.legoapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Sumanth on 3/17/2016.
 */
public class GridOptionAdapter extends BaseAdapter {

    private Context myContext;
    private GridOption gridOption;
    private int gridOptionLength;

    public GridOptionAdapter(Context context, GridOption option) {
        myContext = context;
        gridOption = option;
        gridOptionLength = gridOption.getNoOfcols() * gridOption.getNoOfRows();
    }

    @Override
    public int getCount() {
        return gridOptionLength;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tView;
        GridView gridView = (GridView) parent;
        if (convertView == null) {
            tView = new TextView(myContext);
            tView.setLayoutParams(new GridView.LayoutParams( gridView.getColumnWidth(), gridView.getColumnWidth()));
            tView.setBackgroundResource(R.drawable.cells_rounded_corners);
            GradientDrawable drawable = (GradientDrawable) tView.getBackground();

            if( ((Integer)getItem(position)).intValue() == 1 ){
                drawable.setColor(Color.parseColor(gridOption.getColorCode()));
            }else{
                drawable.setColor(Color.TRANSPARENT);
            }



        } else {
            tView = (TextView) convertView;
        }
        return tView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        Integer[][] gridMap = gridOption.getGridMap();
        int r = (int)Math.floor(position/gridOption.getNoOfcols());
        int c = position%gridOption.getNoOfcols();
        return gridMap[r][c];
    }
}
