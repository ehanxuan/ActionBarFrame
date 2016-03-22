package com.ehx.actionbarframe.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ehx.actionbarframe.R;
import com.ehx.actionbarframe.utils.DrawerUtils;
import com.ehx.actionbarframe.utils.SDLogger;

public class BaseActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    protected enum ActivityModelType {
        ROOT, NORMAL, MODEL
    }

    private Toolbar mActionBarToolbar;
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    // モデル表示用
    private static String KEY_PARAM_PRESENTED_ACTIVITY = "presentedActivity";
    private static String KEY_PARAM_IS_MODEL = "isModel";
    private static String KEY_PARAM_ANIMATED = "animated";
    private boolean mIsModelActivity = false;
    protected Class<BaseActivity> mPresentedActivityClass;
//    protected GnaviBaseActivity mPresentingActivity;

    // ------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------

    // implements constructors here

    // ------------------------------------------------------------------
    // Override methods
    // ------------------------------------------------------------------

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) !=  0 ) {
            // TODO:

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.beforeOpenModelActivity();

        this.startWithAnimation(getIntent().getBooleanExtra(KEY_PARAM_ANIMATED, true));

//        if (Build.VERSION.SDK_INT < 16) {
//            ((GnaviApplication)getApplication()).addActivity(this);
//        }
    }

    @Override
    public void finish() {
        super.finish();
        this.finishWithAnimation(getIntent().getBooleanExtra(KEY_PARAM_ANIMATED, true));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.setupNavDrawer();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        this.findViews();
        this.setupActionBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (Build.VERSION.SDK_INT < 16) {
//            ((GnaviApplication)getApplication()).removeActivity(this);
//        }
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
        if (id == R.id.menu_close) {
            finishModel(true);
        }

        return false;
    }

    /**
     *
     * 端末のメニューキーを押下して、空白のメニューを表示する。チケット#63118
     * */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            SDLogger.d("*******KEYCODE_MENU*******");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        SDLogger.d("selected position " + position);
        this.goToNavDrawerItem(position);
    }

    // ------------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------------

    public void pushActivity(Intent intent, boolean animated) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable(KEY_PARAM_PRESENTED_ACTIVITY, this.getPresentedActivityClass());
        bundle.putBoolean(KEY_PARAM_IS_MODEL, false);
        bundle.putBoolean(KEY_PARAM_ANIMATED, animated);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void popupActivity(Intent intent, boolean animated) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable(KEY_PARAM_PRESENTED_ACTIVITY, this.getClass());
        bundle.putBoolean(KEY_PARAM_IS_MODEL, true);
        bundle.putBoolean(KEY_PARAM_ANIMATED, animated);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void popupActivityForResult(Intent intent, int requestCode, boolean animated) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable(KEY_PARAM_PRESENTED_ACTIVITY, this.getClass());
        bundle.putBoolean(KEY_PARAM_IS_MODEL, true);
        bundle.putBoolean(KEY_PARAM_ANIMATED, animated);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 検索系中で、複数画面がジャンプする時に、画面がきらきらする問題を解決
     *
     **/
    public void pushActivityForJump(Intent intent, int requestCode, boolean animated) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable(KEY_PARAM_PRESENTED_ACTIVITY, this.getPresentedActivityClass());
        bundle.putBoolean(KEY_PARAM_IS_MODEL, false);
        bundle.putBoolean(KEY_PARAM_ANIMATED, animated);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void finish(boolean animated) {
        super.finish();
        this.finishWithAnimation(animated);
    }

    public void finishModel(boolean animated) {
        Intent intent = new Intent(this, this.getPresentedActivityClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        if (animated) {
            overridePendingTransition(0, R.anim.gn_slide_out_bottom);
        } else {
            overridePendingTransition(0, 0);
        }
    }
//
//    @Override
//    public void onApiSuccess(GnaviApiBase api, Object data) {
//
//    }
//
//    @Override
//    public void onApiError(GnaviApiBase api, Error error) {
//
//    }

    // ------------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------------

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
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getSupportActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.color.theme_primary));
        }

        if (getActivityModelType() == ActivityModelType.MODEL) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void beforeOpenModelActivity() {
        Intent intent = getIntent();
        this.mIsModelActivity = intent.getBooleanExtra(KEY_PARAM_IS_MODEL, false);
        if (intent.hasExtra(KEY_PARAM_PRESENTED_ACTIVITY)) {
            this.setPresentedActivityClass((Class<BaseActivity>) intent.getSerializableExtra(KEY_PARAM_PRESENTED_ACTIVITY));
        }
//        if (this.mIsModelActivity) {
//            this.getPresentedActivity().setPresentingActivity(this);
//        }
    }

    public Toolbar getActionBarToolbar() {
        return mActionBarToolbar;
    }

    protected ActivityModelType getActivityModelType() {
        if (mIsModelActivity) {
            return ActivityModelType.MODEL;
        }
        return ActivityModelType.NORMAL;
    }

    protected int getSelfNavDrawerItem() {
        return DrawerUtils.NAVDRAWER_ITEM_INVALID;
    }

    protected void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()) {
            View view = getCurrentFocus();
            if (view != null) {
                IBinder iBinder = view.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(iBinder, 0);
            }
        }
    }

//    protected void exitApplication() {
//        if (Build.VERSION.SDK_INT >= 16) {
//            finishAffinity();
//        } else {
//            ((GnaviApplication)getApplication()).exitApplication();
//        }
//    }

    // ------------------------------------------------------------------
    // getters and setters methods
    // ------------------------------------------------------------------

    public String getTag() {
        return getClass().getSimpleName();
    }

    public Class<BaseActivity> getPresentedActivityClass() {
        return mPresentedActivityClass;
    }

    public void setPresentedActivityClass(Class<BaseActivity> presentedActivityClass) {
        this.mPresentedActivityClass = presentedActivityClass;
    }

//    public GnaviBaseActivity getPresentingActivity() {
//        return mPresentingActivity;
//    }
//
//    public void setPresentingActivity(GnaviBaseActivity presentingActivity) {
//        this.mPresentingActivity = presentingActivity;
//    }

    // ------------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------------

    private void goToNavDrawerItem(int item) {
        if (DrawerUtils.goToNavDrawerItem(this, item)) {
            finish();
        }
    }

    private void startWithAnimation(boolean animated) {
        if (!animated) {
            overridePendingTransition(0, 0);
            return;
        }

        switch (getActivityModelType()) {
            case NORMAL:
                break;
            case MODEL:
                overridePendingTransition(R.anim.gn_slide_in_bottom, R.anim.gn_slide_out_top);
                break;
            default:
                overridePendingTransition(0, 0);
                break;
        }
    }

    private void finishWithAnimation(boolean animated) {
        if (!animated) {
            overridePendingTransition(0, 0);
            return;
        }

        switch (getActivityModelType()) {
            case NORMAL:
                break;
            case MODEL:
                overridePendingTransition(0, R.anim.gn_slide_out_bottom);
                break;
            default:
                overridePendingTransition(0, 0);
                break;
        }
    }

    // ------------------------------------------------------------------
    // inner classes or interfaces
    // ------------------------------------------------------------------

    // declare inner classes or interfaces here




}
