package com.ehx.actionbarframe.model;

/**
 * Created by ehanxuan on 15/4/20.
 */
public class DrawerItem {
    private int titleId;
    private int iconId;

    public DrawerItem(int titleId, int iconId) {
        this.titleId = titleId;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public int getTitleId() {
        return titleId;
    }
}
