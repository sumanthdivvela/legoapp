package in.divvela.legoapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import in.divvela.legoapp.utils.Utility;

/**
 * Created by Sumanth on 3/17/2016.
 */
public class CellAdapter extends BaseAdapter {

    private Context myContext;
    private Integer[] gridCanvasItems =
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};

    public CellAdapter(Context context) {
        myContext = context;
    }

    @Override
    public int getCount() {
        return gridCanvasItems.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tView;
        GridView gridView = (GridView) parent;
        if (convertView == null) {
            tView = new TextView(myContext);
            Utility util = new Utility(myContext);
            GridView.LayoutParams layoutParams = new GridView.LayoutParams(gridView.getColumnWidth(), gridView.getColumnWidth());
            tView.setLayoutParams(layoutParams);
            tView.setBackgroundResource(R.drawable.cells_rounded_corners);

        } else {
            tView = (TextView) convertView;
        }
        GradientDrawable drawable = (GradientDrawable) tView.getBackground();
        drawable.setColor(Color.GRAY);
        return tView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return gridCanvasItems[position];
    }

    public Integer[] getGridCanvasItems() {
        return gridCanvasItems;
    }

    public void setGridCanvasItems(Integer[] gridCanvasItems) {
        this.gridCanvasItems = gridCanvasItems;
    }
}

