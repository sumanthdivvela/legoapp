package in.divvela.legoapp.utils;

import android.content.Context;
import android.util.TypedValue;

import java.lang.ref.SoftReference;

/**
 * Created by KH121 on 3/16/2016.
 */
public class Utility {

    SoftReference<Context> contextSoftReference;

    public Utility(Context context){
        contextSoftReference = new SoftReference<Context>(context);
    }

    public int getDevicePixels(int dps){
       // return (int) (dps * scale + 0.5f);
        Context context = contextSoftReference.get();
        return (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, context.getResources().getDisplayMetrics());
    }
}
