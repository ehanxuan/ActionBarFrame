package com.ehx.actionbarframe.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ehx.actionbarframe.R;
import com.ehx.actionbarframe.adapter.DrawerAdapter;
import com.ehx.actionbarframe.model.DrawerItem;
import com.ehx.actionbarframe.utils.DrawerUtils;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment {

    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private NavigationDrawerCallbacks mCallbacks;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
//    private ActionBarDrawerToggle mDrawerToggle;


    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_main_drawer, container, false);
        return mDrawerListView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    protected void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        BaseActivity activity = (BaseActivity) getActivity();

        mFragmentContainerView = activity.findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.top_open, R.string.top_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                BaseActivity tmpActivity = (BaseActivity) getActivity();
                tmpActivity.getSupportActionBar().setTitle(tmpActivity.getResources().getString(R.string.app_name));
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        } ;
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

//        int selfItem = activity.getSelfNavDrawerItem();

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.theme_primary_dark));
//        ListView navDrawer = (ListView) mDrawerLayout.findViewById(R.id.left_menu);

        List<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
        for (int i = 0; i < DrawerUtils.NAVDRAWER_TITLE_RES_ID.length; i++) {
            drawerItems.add(new DrawerItem(DrawerUtils.NAVDRAWER_TITLE_RES_ID[i], DrawerUtils.NAVDRAWER_ICON_RES_ID[i]));
        }

        mDrawerListView.setAdapter(new DrawerAdapter(activity.getSupportActionBar().getThemedContext(), drawerItems));
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
//                LogUtils.outputLog(Log.DEBUG, getActivity().getClass().getSimpleName() + ":selected position " + position);
                selectItem(position);
//                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
        });

//        if (selfItem == DrawerUtils.NAVDRAWER_ITEM_INVALID) {
//            // do not show a nav drawer
//            if (mDrawerListView != null) {
//                ((ViewGroup) mDrawerListView.getParent()).removeView(mDrawerListView);
//            }
//            mDrawerLayout = null;
//            return;
//        }

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        // populate the nav drawer with the correct items
//        mCurrentSelectedPosition = activity.getSelfNavDrawerItem();
        this.selectItem(mCurrentSelectedPosition);

    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    private void selectItem(int position) {
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null && this.isNavDrawerOpen()) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCurrentSelectedPosition != position && mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        mCurrentSelectedPosition = position;
    }
}
