package com.ehx.actionbarframe.utils;

import android.content.Intent;
import android.text.TextUtils;

import com.ehx.actionbarframe.R;
import com.ehx.actionbarframe.ui.MainActivity;
import com.ehx.actionbarframe.ui.Page1Activity;
import com.ehx.actionbarframe.ui.base.BaseActivity;

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

    public static boolean goToNavDrawerItem(BaseActivity activity, int item) {

        boolean needFinishCurrentPage = true;

        Intent intent = null;
        switch (item) {
            case NAVDRAWER_ITEM_1:
                intent = new Intent(activity, MainActivity.class);
                break;
            case NAVDRAWER_ITEM_2:
                intent = new Intent(activity, Page1Activity.class);
                break;
        }

        if (intent != null) {
//            if (intent.getComponent().getClassName().equals(activity.getClass().getCanonicalName())) {
//                Log.w(activity.getTag(), "Abort migrating to self.");
//                return null;
//            }
            activity.pushActivity(intent, false);
        }

        return needFinishCurrentPage;
    }
}
