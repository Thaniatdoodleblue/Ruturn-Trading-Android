package com.returntrader.view.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.HomeAdapter;
import com.returntrader.common.Constants;
import com.returntrader.helper.TopBarHelper;
import com.returntrader.library.Log;
import com.returntrader.permissions.IPermissionHandler;
import com.returntrader.permissions.PermissionProducer;
import com.returntrader.permissions.RequestPermission;
import com.returntrader.presenter.HomePresenter;
import com.returntrader.presenter.ipresenter.IHomePresenter;
import com.returntrader.view.fragment.SettingsFragment;
import com.returntrader.view.iview.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements IHomeView, PermissionProducer {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private HomeAdapter mHomeAdapter;
    private IHomePresenter iHomePresenter;
    private TopBarHelper mTopBarHelper;
    //private TextView vTvInvestedAmount;
    //private LinearLayout mLayoutCashInvest;
    //private ImageView vImageShowArrow;
    //private TextView vTvCash;
    private IPermissionHandler iPermissionHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_home);
        bindActivity();
        iHomePresenter = new HomePresenter(this);
        iHomePresenter.onCreatePresenter(getIntent().getExtras());
    }

    /***/
    private void bindActivity() {
        iPermissionHandler = RequestPermission.newInstance(this);
       /* vTvCash = findViewById(R.id.tv_header_cash);
        vImageShowArrow = findViewById(R.id.image_header_invested_indicator);
        vTvInvestedAmount = findViewById(R.id.tv_header_invested_amount);
        mLayoutCashInvest = findViewById(R.id.layout_banner_details);*/
        mTabLayout = findViewById(R.id.egg_tab_layout);
        mViewPager = findViewById(R.id.eggPager);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        mHomeAdapter = new HomeAdapter(getSupportFragmentManager());
        setAdapter();
        bindReceivers();
        setUpTopBar();
    }

    public void requestCameraPermission() {
        iPermissionHandler.callCameraPermissionHandler();
    }

    /***/
    private List<Integer> getMenuResources() {
        List<Integer> menuResources = new ArrayList<>();
        menuResources.add(R.drawable.ic_search);
        menuResources.add(R.drawable.ic_egg);
        menuResources.add(R.drawable.ic_more_vert);
        return menuResources;
    }

    @Override
    public void showFicaVerifiedDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_withdraw_notice);
        ((TextView) dialog.findViewById(R.id.tv_notice)).setText(R.string.alert_message_fica_verified);
        dialog.findViewById(R.id.tv_aler_ok).setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    /***/
    private Fragment getFragmentFromViewPager(int position) {
        return getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.eggPager + ":" + position);
    }

    @Override
    public void startDelayPriceUpdateService() {
       /* try {
            if (getActivity() != null) {
                Intent updatePriceService = new Intent(getActivity(), DelayPriceSyncService.class);
                getActivity().startService(updatePriceService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void updateHoldingsList() {
        updateViewPager(1);//To Update Holding List
    }

    /***/
    private void setAdapter() {
        mViewPager.setAdapter(mHomeAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mViewPager.setOffscreenPageLimit(2);
        setTabLayoutIcons();
    }

    /***/
    private void setTabLayoutIcons() {
        List<Integer> icons = getMenuResources();
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(icons.get(i));
        }
        setTabIconColors(0);
    }


    /***/
    private void bindReceivers() {
        IntentFilter balanceUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        balanceUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverBalanceUpdate, balanceUpdateIntentFilter);

        IntentFilter holdingUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_HOLDING_UPDATEINFO);
        holdingUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverUpdateHoldingInfo, holdingUpdateIntentFilter);

        IntentFilter delayPriceUpdatedCalcOthersIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED);
        delayPriceUpdatedCalcOthersIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverUpdateDelayPriceInfo, delayPriceUpdatedCalcOthersIntentFilter);
    }

    /***/
    private void unregisterReceivers() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverBalanceUpdate);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateHoldingInfo);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateDelayPriceInfo);
    }

    /***/
    private final BroadcastReceiver bReceiverUpdateHoldingInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.BroadCastKey.ACTION_HOLDING_UPDATEINFO)) {
                iHomePresenter.doDelayPriceUpdate();
            }
        }
    };

    /***/
    private final BroadcastReceiver bReceiverUpdateDelayPriceInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED)) {
                iHomePresenter.doDelayPriceUpdate();
            }
        }
    };


    /***/
    private final BroadcastReceiver bReceiverBalanceUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED:
                    iHomePresenter.updateBalance();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        iHomePresenter.updateBalance();
    }

    /***/
    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            setTabIconColors(tab.getPosition());

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            setTabIconColors(tab.getPosition());
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    /***/
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            updateViewPager(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /***/
    private void dismissKeyBoard(int position) {
        getCodeSnippet().hideKeyboard(HomeActivity.this);
        /*switch (position) {
            case 0:
                break;
            case 1:
                getCodeSnippet().hideKeyboard(HomeActivity.this);
                break;
            case 2:
                getCodeSnippet().hideKeyboard(HomeActivity.this);
                break;

        }*/
    }

    /***/
    private void setTabIconColors(int position) {
        switch (position) {
            case 0:
                setSelectedColor(0);
                setUnSelectedColor(1);
                setUnSelectedColor(2);
                break;
            case 1:
                setUnSelectedColor(0);
                setSelectedColor(1);
                setUnSelectedColor(2);
                break;
            case 2:
                setUnSelectedColor(0);
                setUnSelectedColor(1);
                setSelectedColor(2);
                break;
        }
    }

    /***/
    private void setUnSelectedColor(int position) {
        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorTabUnselected);
        if (mTabLayout.getTabAt(position).getIcon() != null)
            mTabLayout.getTabAt(position).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.MULTIPLY);
    }

    /***/
    private void setSelectedColor(int position) {
        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorTabSelected);
        if (mTabLayout.getTabAt(position).getIcon() != null)
            mTabLayout.getTabAt(position).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(HomeActivity.this);
        // super.onBackPressed();
        scrollListToTop();
    }

    /***/
    private void showExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(R.string.alert_title_exit);
        builder.setIcon(R.drawable.ic_view_exit);
        builder.setMessage(R.string.alert_message_exit);
        builder.setPositiveButton(R.string.txt_alert_not_now, (dialog, which) -> dialog.dismiss());
        builder.setNegativeButton(R.string.txt_alert_yes, (dialog, which) -> finish());
        AlertDialog alert = builder.create();
        alert.show();
    }

    /***/
    private void scrollListToTop() {
        if (mHomeAdapter != null) {
            if (mViewPager.getCurrentItem() == 0) {
                if (mHomeAdapter.isTopItemShown()) {
                    doubleBackToExit();
                } else {
                    mHomeAdapter.scrollToTop();
                }
            } else {
                mViewPager.setCurrentItem(0);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCodeSnippet().hideKeyboard(HomeActivity.this);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.eggPager);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void hideTopBannerCardView(int visibility) {
        //mLayoutCashInvest.setVisibility(visibility);
        getTopBarHelper().setUpView();
    }

    /***/
    private TopBarHelper getTopBarHelper() {
        if (mTopBarHelper == null)
            mTopBarHelper = new TopBarHelper(HomeActivity.this, getParentView());
        return mTopBarHelper;
    }

    @Override
    public void setUpTopBar() {
        getTopBarHelper().setUpView();
    }

    @Override
    public void moveToHomeScreen() {
        //if (mViewPager.getCurrentItem() != 0) {
        mViewPager.setCurrentItem(0);
        //}
    }


    /***/
    private void updateViewPager(int position) {
        dismissKeyBoard(position);
        doCheckForPinAuthentication();
        if (mHomeAdapter != null) {
            mHomeAdapter.updateFragment(position);
        }
    }

    /***/
    private void doCheckForPinAuthentication() {
        boolean isAuthenticationRequired = iHomePresenter.isPinAuthenticationEnabled();
        if (isAuthenticationRequired) {
            hideTopBannerCardView(View.GONE);
        } else {
            iHomePresenter.setCashAndInvestedDetails();
            hideTopBannerCardView(View.VISIBLE);
        }
    }

    @Override
    public FragmentActivity getActivity() {
        Log.d(TAG, "getActivity");
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IPermissionHandler.PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_CAMERA, true);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_CAMERA, false);
                }

            }
        }
    }

    @Override
    public void onReceivedPermissionStatus(int code, boolean isGrated) {
        switch (code) {
            case IPermissionHandler.PERMISSIONS_REQUEST_CAMERA:
                if (isGrated) {
                    SettingsFragment fragment = mHomeAdapter.getSettingsFragment();
                    if (fragment != null) {
                        fragment.iSettingsPresenter.showImagePickerDialog();
                    }
                } else {
                    iPermissionHandler.showPermissionExplainDialog(this, getString(R.string.txt_permission_request_camera_storage));
                }
                break;
        }
    }
}
