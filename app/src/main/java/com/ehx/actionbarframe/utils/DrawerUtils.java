package com.ehx.actionbarframe.utils;

import com.ehx.actionbarframe.R;

/**
 * Created by ehanxuan on 15/4/16.
 */
public class DrawerUtils {
    public static final int NAVDRAWER_ITEM_1 = 0;
    public static final int NAVDRAWER_ITEM_2 = 1;
    public static final int NAVDRAWER_ITEM_3 = 2;
    public static final int NAVDRAWER_ITEM_4 = 3;
    public static final int NAVDRAWER_ITEM_5 = 4;
    public static final int NAVDRAWER_ITEM_6 = 5;
    public static final int NAVDRAWER_ITEM_7 = 6;
    public static final int NAVDRAWER_ITEM_INVALID = -1;
//    private static final int NAVDRAWER_ITEM_SEPARATOR = -2;
//    private static final int NAVDRAWER_ITEM_SEPARATOR_SPECIAL = -3;

    // titles for navdrawer items (indices must correspond to the above)
    public static final int[] NAVDRAWER_TITLE_RES_ID = new int[] {
            R.string.navdrawer_item_title_1,
            R.string.navdrawer_item_title_2,
            R.string.navdrawer_item_title_3,
            R.string.navdrawer_item_title_4,
            R.string.navdrawer_item_title_5,
            R.string.navdrawer_item_title_6,
            R.string.navdrawer_item_title_7
    };

    // icons for navdrawer items (indices must correspond to above array)
    public static final int[] NAVDRAWER_ICON_RES_ID = new int[] {
            R.drawable.ic_drawer_my_schedule,
            R.drawable.ic_drawer_my_schedule,
            R.drawable.ic_drawer_my_schedule,
            0,
            0,
            0,
            0
    };
}
