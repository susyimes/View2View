package com.susyimes.free.view2view;

import android.content.Context;

/**
 * 2016/5/25.
 */
public class Dp2px {
    public static int UseDp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
