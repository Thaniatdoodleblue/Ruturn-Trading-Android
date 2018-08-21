package com.returntrader.view.widget;

/**
 * Created by elcot on 8/23/2017.
 */

public interface DrawableClickListener {

    public enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}

    public void onClick(DrawablePosition target);

}
