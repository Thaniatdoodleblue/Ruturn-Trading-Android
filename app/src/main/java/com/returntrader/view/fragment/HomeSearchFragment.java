package com.returntrader.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.BannerPagerAdapter;
import com.returntrader.adapter.SearchHomeAdapter;
import com.returntrader.common.Constants;
import com.returntrader.model.listener.IBottomSheetListener;
import com.returntrader.presenter.SearchHomePresenter;
import com.returntrader.presenter.ipresenter.ISearchPresenter;
import com.returntrader.sync.DelayPriceSyncService;
import com.returntrader.view.activity.CompanyDetailActivity;
import com.returntrader.view.activity.RegistrationPhaseOneActivity;
import com.returntrader.view.iview.ISearchView;


public class HomeSearchFragment extends BaseFragment implements ISearchView,
        View.OnClickListener, SearchView.OnQueryTextListener
        , SearchView.OnCloseListener, BannerFragment.BannerClickListener, View.OnTouchListener {

    private int mFirstVisibleItem, visibleItemCount, totalItemCount;
    // private RelativeLayout mBottomLayout;
    private int previousTotal = 0; // The total number of items in the dataset after the last loadForDayDataSet
    private boolean loading = true; // True if we are still waiting for the last set of data to loadForDayDataSet.
    private int visibleThreshold = 0; // The minimum amount of items to have below your current scroll position before loading more.
    private boolean searchStatus = false;


    private TextView mCurrentPageName, space, space1;
    private TextView vTvCategoryFilter;
    private RecyclerView mRvMailList;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchView mSearchView;
    private FilterBottomSheetFragment mBottomPopUpFragment;
    private CategoryBottomSheetFragment mCategoryBottomSheetFragment;
    public ISearchPresenter iSearchPresenter;
    private ViewPager mViewPagerBanner;
    private Handler mSliderHandler = null;
    private Runnable mSliderRunnable = null;
    private TextView mTvEmptyText;

    public HomeSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_search, container, false);
        bindActivity(view);
        iSearchPresenter = new SearchHomePresenter(this);
        iSearchPresenter.onCreatePresenter(getArguments());
        return view;
    }


    private void bindActivity(View view) {

        mSearchView = view.findViewById(R.id.simpleSearchView);
        mViewPagerBanner = view.findViewById(R.id.viewpager_banner);

        //mProgressBar =  view.findViewById(R.id.progressBar);
        mCurrentPageName = view.findViewById(R.id.day);
        vTvCategoryFilter = view.findViewById(R.id.tv_category_filter);
        space = view.findViewById(R.id.index1);
        space1 = view.findViewById(R.id.day1);
        mTvEmptyText = view.findViewById(R.id.tv_empty_text);

        // mBottomLayout = view.findViewById(R.id.loadItemsLayout_recyclerView);
        mRvMailList = view.findViewById(R.id.recycler_mail_list_primary);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvMailList.setLayoutManager(mLinearLayoutManager);
        mRvMailList.setNestedScrollingEnabled(true);
        mRvMailList.setOnTouchListener(this);

        //mCurrentPageName.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorlightBlue));


        //  mRvMailList.addOnScrollListener(mOnScrollListener);
        vTvCategoryFilter.setOnClickListener(this);
        mCurrentPageName.setOnClickListener(this);

        mSearchView.setOnCloseListener(this);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnSearchClickListener(this);

        bindReceiver();
    }

    private void bindReceiver() {
        IntentFilter balanceUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        balanceUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverBalanceUpdate, balanceUpdateIntentFilter);

        IntentFilter intentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bDelayPriceUpdateReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bDelayPriceUpdateReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverBalanceUpdate);
    }


    /***/
    private final BroadcastReceiver bDelayPriceUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            if (intent.getAction().equalsIgnoreCase(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED)) {
                iSearchPresenter.doDelayPriceUpdate();
            }
        }
    };


    /***/
    private final BroadcastReceiver bReceiverBalanceUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED:
                    Log.e("Deposit Card", "Removed");
                    iSearchPresenter.resetBannerAdapter(); //To Check whether Deposit card needs to be removed or not.
                    break;
            }
        }
    };

    @Override
    public void showProgressbar() {
        //super.showProgressbar();
        //mProgressBar.setVisibility(View.VISIBLE);

    }


    @Override
    public void dismissProgressbar() {
        //super.dismissProgressbar();
        // mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.simpleSearchView:
                space1.setVisibility(View.GONE);
                mCurrentPageName.setVisibility(View.GONE);
                space.setVisibility(View.GONE);
                vTvCategoryFilter.setVisibility(View.GONE);
                iSearchPresenter.onClickSearch(null);
                scrollToTop();
                break;
            case R.id.day:
                showFilterBottomSheet();
                break;
            case R.id.tv_category_filter:
                showCategoryFilterBottomSheet();
                break;
            default:
                break;

        }

    }

    @Override
    public void navigateToActivity(Bundle bundle) {
        Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.KEY_REQUEST_CODE_COMPANY_DETAILS);
    }


    @Override
    public void navigateToFullRegistration(Bundle bundle) {
        Intent intent = new Intent(getActivity(), RegistrationPhaseOneActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_REGISTRATION_PHASE_ONE);
    }

    @Override
    public void setEmptyText(String message, int count) {
        mTvEmptyText.setVisibility(View.GONE);
        if (count == 0) {
            if (!(TextUtils.isEmpty(message))) {
                mTvEmptyText.setVisibility(View.VISIBLE);
                mTvEmptyText.setText(message);
            }
        }
    }

    @Override
    public void startDelayPriceUpdateService() {
        try {
            if (getActivity() != null) {
                Intent updatePriceService = new Intent(getActivity(), DelayPriceSyncService.class);
                getActivity().startService(updatePriceService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void navigateToBanner(Class destination, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), destination);
        if (bundle != null)
            intent.putExtras(bundle);
        if (requestCode > 0) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }

    }

    @Override
    public void setSearchHomeAdapter(SearchHomeAdapter mailListAdapter) {
        mRvMailList.setAdapter(mailListAdapter);
        mRvMailList.setNestedScrollingEnabled(true);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        //mBottomLayout.setVisibility(visibility);
    }

    @Override
    public void setBannerViewPagerAdapter(BannerPagerAdapter bannerViewPagerAdapter) {
        mViewPagerBanner.removeAllViews();
        mViewPagerBanner.invalidate();
        mViewPagerBanner.setAdapter(bannerViewPagerAdapter);
        setTimerForBanner();
    }

    @Override
    public FragmentManager getChildViewFragment() {
        return getChildFragmentManager();
    }

    @Override
    public void setBannerVisibility(int visibility) {
        mViewPagerBanner.setVisibility(visibility);
    }

    @Override
    public boolean isListPositionAtTop() {
        return mLinearLayoutManager.getChildCount() > 0 ? mLinearLayoutManager != null && mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 : true;
    }

    @Override
    public void scrollToTop() {
        mRvMailList.smoothScrollToPosition(0);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        searchStatus = true;
        mSearchView.clearFocus();
        setEmptyText(null, 1);
        iSearchPresenter.onClickSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchStatus = true;
        setEmptyText(null, 1);
        iSearchPresenter.onClickSearch(newText);
        return false;
    }

    @Override
    public boolean onClose() {
        //setCurrentPageName(Constants.LoadCompanyList.LOAD_LAST_KNOWN_LIST);
        searchStatus = false;
        //mBottomLayout.setVisibility(View.GONE);
        visibleThreshold = 0;
        previousTotal = 0; // The total number of items in the dataset after the last loadForDayDataSet
        loading = true; // True if we are still waiting for the last set of data to loadForDayDataSet.
        space1.setVisibility(View.VISIBLE);
        mCurrentPageName.setVisibility(View.VISIBLE);
        space.setVisibility(View.VISIBLE);
        vTvCategoryFilter.setVisibility(View.VISIBLE);
        iSearchPresenter.onClickClose();
        // iSearchPresenter.onClickFilter(Constants.LoadCompanyList.LOAD_DAY);
        return false;
    }

    public void showFilterBottomSheet() {
        mBottomPopUpFragment = new FilterBottomSheetFragment();
        mBottomPopUpFragment.setCurrentPage(iSearchPresenter.getFilterItemPosition());
        mBottomPopUpFragment.setBottomSheetListener(iBottomSheetListener);
        mBottomPopUpFragment.show(getFragmentManager(), mBottomPopUpFragment.getTag());
    }

    public void showCategoryFilterBottomSheet() {
        mCategoryBottomSheetFragment = new CategoryBottomSheetFragment();
        mCategoryBottomSheetFragment.setCurrentPage(iSearchPresenter.getFilterItemPosition());
        mCategoryBottomSheetFragment.setBottomSheetListener(iBottomSheetListener);
        mCategoryBottomSheetFragment.show(getFragmentManager(), mCategoryBottomSheetFragment.getTag());
    }


    private void setFilterTitle(String title) {
        mCurrentPageName.setText(title);
    }

    private void setCurrentPageName(int position) {
        switch (position) {
            case Constants.LoadCompanyList.LOAD_A_Z:
                setFilterTitle(getString(R.string.txt_az));
                break;
            case Constants.LoadCompanyList.LOAD_DAY:
                setFilterTitle(getString(R.string.txt_day));
                break;
            case Constants.LoadCompanyList.LOAD_REIT:
                setFilterTitle(getString(R.string.txt_reit));
                break;
            case Constants.LoadCompanyList.LOAD_BIG_BRANDS:
                setFilterTitle(getString(R.string.txt_big_brands));
                break;
            case Constants.LoadCompanyList.LOAD_ALTX:
                setFilterTitle(getString(R.string.txt_altx));
                break;
            case Constants.LoadCompanyList.LOAD_MOST_POPULAR:
                setFilterTitle(getString(R.string.txt_popular));
                break;
            case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                setFilterTitle(getString(R.string.txt_top_forty));
                break;
            case Constants.LoadCompanyList.LOAD_FAVOURITE:
                setFilterTitle(getString(R.string.txt_fav));
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iSearchPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }


    private void setTimerForBanner() {
        if (mSliderHandler != null) {
            if (mSliderRunnable != null)
                mSliderHandler.removeCallbacks(mSliderRunnable);
        }
        mSliderHandler = new Handler();

        mSliderRunnable = new Runnable() {
            public void run() {
                //Log.d(TAG, "postDelayed run : ");
                int currentItem = mViewPagerBanner.getCurrentItem();
                int count = mViewPagerBanner.getAdapter().getCount();
                //Log.d(TAG, "currentItem :" + currentItem);
                //Log.d(TAG, "count :" + count);
                if (count > 1) {
                    if (currentItem == (count - 1)) {
                        currentItem = 0;
                    } else {
                        currentItem = currentItem + 1;
                    }
                    //Log.d(TAG, "nextItem :" + currentItem);

                    mViewPagerBanner.setCurrentItem(currentItem, true);
                    mSliderHandler.postDelayed(this, 10000);
                }
            }
        };
        mSliderHandler.postDelayed(mSliderRunnable, 10000);

    }

    @Override
    public void removeBanner(int itemType) {
        iSearchPresenter.onClickRemoveBanner(itemType);

    }

    @Override
    public void onClickBanner(int itemType) {
        iSearchPresenter.onClickBanner(itemType);
    }

    /***/
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = recyclerView.getLayoutManager().getItemCount();
            mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            int currentPage = iSearchPresenter.getFilterItemPosition();
            if (!loading && (totalItemCount - visibleItemCount) <= (mFirstVisibleItem + visibleThreshold) && (!searchStatus) && (currentPage != Constants.LoadCompanyList.LOAD_FAVOURITE)) {
                //mBottomLayout.setVisibility(View.VISIBLE);
                switch (currentPage) {
                    case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                        //iSearchPresenter.doFetchTop40Data(totalItemCount);
                        iSearchPresenter.onClickFilter(Constants.LoadCompanyList.LOAD_TOP_FORTY);
                        loading = true;
                        break;
                    case Constants.LoadCompanyList.LOAD_DAY:
                        iSearchPresenter.onClickFilter(Constants.LoadCompanyList.LOAD_DAY);
                        //iSearchPresenter.doFetchApiDataForDay(totalItemCount);
                        loading = true;
                        break;
                }
            }
        }
    };

    /***/
    private IBottomSheetListener iBottomSheetListener = new IBottomSheetListener() {
        @Override
        public void onClickFilter(int position) {
            searchStatus = false;
            setCurrentPageName(position);
            previousTotal = 0;
            loading = true;
            visibleThreshold = 0;
            iSearchPresenter.onClickFilter(position);
        }
    };


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        getCodeSnippet().hideKeyboard(getActivity());
        return false;
    }

    /***/
    public void updateBanner() {
        //iSearchPresenter.resetBannerAdapter();
    }
}
