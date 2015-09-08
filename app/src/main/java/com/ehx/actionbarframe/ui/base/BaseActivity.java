package com.ehx.actionbarframe.ui.base;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ehx.actionbarframe.ui.MainActivity;
import com.ehx.actionbarframe.R;
import com.ehx.actionbarframe.ui.Page1Activity;
import com.ehx.actionbarframe.utils.DrawerUtils;

public class BaseActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    protected Toolbar mActionBarToolbar;
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        this.setupActionBar();
        this.setupNavDrawer();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        this.findViews();
        this.setupActionBar();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment != null && mNavigationDrawerFragment.isNavDrawerOpen()) {
            mNavigationDrawerFragment.closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return false;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
//        LogUtils.outputLog(Log.DEBUG, "selected position " + position);
        this.goToNavDrawerItem(position);
    }

    public int getSelfNavDrawerItem() {
        return DrawerUtils.NAVDRAWER_ITEM_INVALID;
    }

    protected void findViews() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
    }

    /**
     * drawer
     */
    protected void setupNavDrawer() {
        if (mNavigationDrawerFragment == null) {
            return;
        }
        mNavigationDrawerFragment.setUp(
                R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                mActionBarToolbar);
    }

    /**
     * action bar
     */
    protected void setupActionBar() {
        mActionBarToolbar.setTitleTextAppearance(this, R.style.action_bar_title);
        mActionBarToolbar.setSubtitleTextAppearance(this, R.style.action_bar_subtitle);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_up);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected Toolbar getActionBarToolbar() {
        return mActionBarToolbar;
    }

    private void goToNavDrawerItem(int item) {
        Intent intent;
        switch (item) {
            case DrawerUtils.NAVDRAWER_ITEM_1:
                intent = new Intent(this, Page1Activity.class);
                startActivity(intent);
                finish();
                break;
            case DrawerUtils.NAVDRAWER_ITEM_2:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
