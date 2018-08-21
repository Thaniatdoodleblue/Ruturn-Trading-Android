package com.returntrader.presenter;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.iid.FirebaseInstanceId;
import com.returntrader.R;
import com.returntrader.adapter.BannerPagerAdapter;
import com.returntrader.adapter.SearchHomeAdapter;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.common.RetroFitBase;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.model.common.BannerItem;
import com.returntrader.model.dto.request.FcmTokenRequest;
import com.returntrader.model.dto.request.SearchHomeRequest;
import com.returntrader.model.webservice.ApiClient;
import com.returntrader.model.webservice.ApiInterface;
import com.returntrader.model.webservice.FcmTokenModel;
import com.returntrader.model.webservice.executor.IGeneralResponseListener;
import com.returntrader.presenter.ipresenter.ISearchPresenter;
import com.returntrader.view.activity.AddMoneyActivity;
import com.returntrader.view.activity.BankDetailsActivity;
import com.returntrader.view.activity.FicaDocumentActivity;
import com.returntrader.view.activity.ShakeMakeInstructionInstructionActivity;
import com.returntrader.view.activity.UpdateAddressActivity;
import com.returntrader.view.activity.WebViewActivity;
import com.returntrader.view.iview.ISearchView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

import static com.returntrader.common.Constants.Common.ENABLED_COMPANIES_STATUS;
import static com.returntrader.common.Constants.RequestCodes.NAVIGATE_TO_ADDBANKDETAILS;


public class SearchHomePresenter extends BasePresenter implements ISearchPresenter {
    private String TAG = getClass().getSimpleName();
    private SearchHomeAdapter mSearchHomeAdapter;
    private List<CompanyItemInfo> mHomeCompanyList = new ArrayList<>();
    private ISearchView iSearchView;
    private int mCurrentFilterItem = Constants.LoadCompanyList.LOAD_A_Z;
    private DatabaseManager mDatabaseManger;
    private AppConfigurationManager mAppConfigurationManager = null;
    private List<BannerItem> mBannerItems;
    private Handler mSliderHandler;
    private Runnable mSliderRunnable;


    public SearchHomePresenter(ISearchView ISearchView) {
        super(ISearchView);
        this.iSearchView = ISearchView;
        this.mDatabaseManger = new DatabaseManager(ISearchView.getActivity());
        this.mAppConfigurationManager = new AppConfigurationManager(iSearchView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        mBannerItems = new ArrayList<>();
        mBannerItems.addAll(getBannerList());
        onClickFilter(Constants.LoadCompanyList.LOAD_TOP_FORTY);
        setBannerAdapter(mBannerItems);
        startTimer();
        registerFcmToken();
//        updateCurrentList(mCurrentFilterItem);
    }


    /***/
    private void startTimer() {
        mSliderHandler = new Handler();
        mSliderRunnable = () -> {
            iSearchView.startDelayPriceUpdateService();
            mSliderHandler.postDelayed(mSliderRunnable, Constants.Common.REFRESH_TIME_DELAY);
        };
        mSliderHandler.postDelayed(mSliderRunnable, Constants.Common.REFRESH_TIME_DELAY);
    }

    /***/
    private void registerFcmToken() {
        if (TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
            return;
        }

        if (TextUtils.isEmpty(FirebaseInstanceId.getInstance().getToken())) {
            return;
        }

        FcmTokenRequest fcmTokenRequest = new FcmTokenRequest(mAppConfigurationManager.getUserId());
        fcmTokenRequest.setToken(FirebaseInstanceId.getInstance().getToken());
        fcmTokenRequest.setDeviceId(iSearchView.getCodeSnippet().getDeviceId());
        fcmTokenRequest.setDeviceType(Constants.Common.FCM_DEVICE_TYPE);
        FcmTokenModel fcmTokenModel = new FcmTokenModel();
        if (iSearchView.getCodeSnippet().hasNetworkConnection()) {
            fcmTokenModel.registerFcmToken(fcmTokenRequest);
        }
    }


    /***/
    private void updateCurrentList(int clickedPosition) {
        new GetCompanyDetails().execute(clickedPosition);
        /*switch (clickedPosition) {
            case Constants.LoadCompanyList.LOAD_DAY:
                new GetCompanyDetails().execute(Constants.LoadCompanyList.LOAD_DAY);
                break;
            case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                new GetCompanyDetails().execute(Constants.LoadCompanyList.LOAD_TOP_FORTY);
                break;
            case Constants.LoadCompanyList.LOAD_FAVOURITE:
                new GetCompanyDetails().execute(Constants.LoadCompanyList.LOAD_FAVOURITE);
                break;
        }*/
    }


    /***/
    private void setAdapter(List<CompanyItemInfo> companyList) {


        if (companyList != null) {
            if (mSearchHomeAdapter == null) {
                mSearchHomeAdapter = new SearchHomeAdapter(companyList, mMailDataAdapterListener);
                iSearchView.setSearchHomeAdapter(mSearchHomeAdapter);
            } else {
                mSearchHomeAdapter.resetItems(companyList);
            }
        }
    }

    @Override
    public void onClickSearch(String searchQuery) {
        if (!(TextUtils.isEmpty(searchQuery)) && searchQuery.length() > 0) {
            clearAdapter();
            List<CompanyItemInfo> searchList = mDatabaseManger.searchCompany(searchQuery);
            iSearchView.setEmptyText(getString(R.string.txt_error_search_no_result), searchList.size());
            setAdapter(searchList);
        } else {
            clearAdapter();
        }
    }

    /***/
    private List<BannerItem> getBannerList() {
        List<BannerItem> bannerFragments = new ArrayList<>();

        if (TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
            bannerFragments.add(new BannerItem(Constants.BannerType.FULL_REGISTRATION));
        }

        if (!(mAppConfigurationManager.isFicaDetailCompleted())) {
            bannerFragments.add(new BannerItem(Constants.BannerType.FICA));
        }

        if (!(mAppConfigurationManager.isBankDetailCompleted())) {
            bannerFragments.add(new BannerItem(Constants.BannerType.BANK_DETAILS));
        }

        if (!(mAppConfigurationManager.isDepositDetails())) {
            if (Double.parseDouble(mAppConfigurationManager.getLastKnownBalance()) < 150) {
                bannerFragments.add(new BannerItem(Constants.BannerType.ADD_DEPOSIT));
            }
        }

        /*if (!(TextUtils.isEmpty(mAppConfigurationManager.getUserId()))) {
            if (!(mAppConfigurationManager.isAddressCompleted())) {
                bannerFragments.add(new BannerItem(Constants.BannerType.ADDRESS));
            }
        }*/

        bannerFragments.add(new BannerItem(Constants.BannerType.FAQ));

        if ((mAppConfigurationManager.isFicaVerified())) {
            bannerFragments.add(new BannerItem(Constants.BannerType.SHAKE_MAKE));
        }

        return bannerFragments;
    }

    /***/
    private void setBannerAdapter(List<BannerItem> listBanner) {
        if (listBanner == null) {
            iSearchView.setBannerVisibility(View.GONE);
            return;
        }

        if (listBanner.size() > 0) {
            BannerPagerAdapter mBannerPagerAdapter = new BannerPagerAdapter(iSearchView.getChildViewFragment(), listBanner);
            iSearchView.setBannerViewPagerAdapter(mBannerPagerAdapter);
        } else {
            iSearchView.setBannerVisibility(View.GONE);
        }
    }

    @Override
    public void onClickFilter(int clickedPosition) {
        this.mCurrentFilterItem = clickedPosition;
        /* clickedPosition 0 indicate loadForDayDataSet day data.
         * 1 indicate loadForDayDataSet top 40
         * 2 indicate loadForDayDataSet favourite */

        /*switch (clickedPosition) {
            case Constants.LoadCompanyList.LOAD_DAY:
                clearAdapter();
                new GetCompanyDetails().execute(Constants.LoadCompanyList.LOAD_DAY);
                break;
            case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                clearAdapter();
                new GetCompanyDetails().execute(Constants.LoadCompanyList.LOAD_TOP_FORTY);
                break;
            case Constants.LoadCompanyList.LOAD_FAVOURITE:
                clearAdapter();
                new GetCompanyDetails().execute(Constants.LoadCompanyList.LOAD_FAVOURITE);
                break;
        }*/
        clearAdapter();
        new GetCompanyDetails().execute(clickedPosition);
    }

    @Override
    public int getFilterItemPosition() {
        return mCurrentFilterItem;
    }

    /***/
    private String getString(int resourceId) {
        if (iSearchView.getActivity() == null) {
            return "";
        }
        return iSearchView.getActivity().getString(resourceId);
    }

    /***/
    private String getCurrentPageName(int currentType) {
        switch (currentType) {
            case Constants.LoadCompanyList.LOAD_A_Z:
                return getString(R.string.txt_az);

            case Constants.LoadCompanyList.LOAD_DAY:
                return getString(R.string.txt_day);

            case Constants.LoadCompanyList.LOAD_REIT:
                return getString(R.string.txt_reit);

            case Constants.LoadCompanyList.LOAD_BIG_BRANDS:
                return getString(R.string.txt_big_brands);

            case Constants.LoadCompanyList.LOAD_ALTX:
                return getString(R.string.txt_altx);

            case Constants.LoadCompanyList.LOAD_MOST_POPULAR:
                return getString(R.string.txt_popular);

            case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                return getString(R.string.txt_top_forty);

            case Constants.LoadCompanyList.LOAD_FAVOURITE:
                return getString(R.string.txt_fav);
        }
        return null;
    }

    @Override
    public void onClickRemoveBanner(int itemType) {
        Log.d(TAG, "itemType : " + itemType);
        mBannerItems = Stream.of(mBannerItems).filter(item -> item.getBannerType() != itemType).collect(Collectors.toList());
        if (mBannerItems != null && mBannerItems.size() > 0) {
            setBannerAdapter(mBannerItems);
        } else {
            iSearchView.setBannerVisibility(View.GONE);
        }
    }

    /***/
    private boolean isUserIdEmpty() {
        return TextUtils.isEmpty(mAppConfigurationManager.getUserId());
    }

    /*to getting current day company details */

    @Override
    public void onClickBanner(int itemType) {
        Bundle bundle;
        Log.e("Running onClickBanner", "" + mAppConfigurationManager.isFicaVerified());
        switch (itemType) {
            case Constants.BannerType.ADD_DEPOSIT:
                if (isUserIdEmpty()) {
                    iSearchView.showMessage(iSearchView.getActivity().getString(R.string.alert_full_registration_required));
                    return;
                }

                if (mAppConfigurationManager.isFicaVerified()) {
                    iSearchView.navigateToBanner(AddMoneyActivity.class, null, -1);
                } else {
                    iSearchView.showMessage(iSearchView.getActivity().getString(R.string.alert_fica_required));
                }
                break;
            case Constants.BannerType.FAQ:
                bundle = new Bundle();
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, iSearchView.getActivity().getString(R.string.url_help));
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, iSearchView.getActivity().getString(R.string.txt_menu_help_qa));
                iSearchView.navigateToBanner(WebViewActivity.class, bundle, -1);
                break;
            case Constants.BannerType.BANK_DETAILS:
                if (isUserIdEmpty()) {
                    iSearchView.showMessage(iSearchView.getActivity().getString(R.string.alert_full_registration_required));
                    return;
                }
                if (mAppConfigurationManager.isFicaVerified()) {
                    iSearchView.navigateToBanner(BankDetailsActivity.class, null, NAVIGATE_TO_ADDBANKDETAILS);
                } else {
                    iSearchView.showMessage(iSearchView.getActivity().getString(R.string.alert_fica_required));
                }
                break;
            case Constants.BannerType.FICA:
                if (isUserIdEmpty()) {
                    iSearchView.showMessage(iSearchView.getActivity().getString(R.string.alert_full_registration_required));
                    return;
                }
                iSearchView.navigateToBanner(FicaDocumentActivity.class, null, Constants.RequestCodes.NAVIGATE_FICA_TO_UPLOAD);
                break;
            case Constants.BannerType.FULL_REGISTRATION:
                iSearchView.navigateToFullRegistration(null);
                break;
            case Constants.BannerType.ADDRESS:
                iSearchView.navigateToBanner(UpdateAddressActivity.class, null, Constants.RequestCodes.NAVIGATE_TO_ADDRESS);
                break;
            case Constants.BannerType.SHAKE_MAKE:
                iSearchView.navigateToBanner(ShakeMakeInstructionInstructionActivity.class, null, -1);
                break;
        }

    }

    @Override
    public void resetBannerAdapter() {
        mBannerItems.clear();
        mBannerItems.addAll(getBannerList());
        setBannerAdapter(mBannerItems);
    }

    @Override
    public void doDelayPriceUpdate() {
        updateCurrentList(mCurrentFilterItem);
    }


    /***/
    private void clearAdapter() {
        if (mSearchHomeAdapter != null) {
            mSearchHomeAdapter.clear();
        }
    }

    @Override
    public void doFetchApiDataForDay(int a) {
        if (iSearchView.getCodeSnippet().hasNetworkConnection()) {
            SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
            searchHomeRequest.setPage(a);
            if (a == 0) {
                iSearchView.showProgressbar();
                iSearchView.setProgressBarVisibility(View.GONE);
            }
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CompanyItemInfo>> call = apiService.callInstrument(searchHomeRequest);
            RetroFitBase.getInstance().retrofitEnqueue(call, mResponseListener, Constants.LoadCompanyList.LOAD_DAY);
        } else {
            iSearchView.getCodeSnippet().hasNetworkConnection();
        }


    }

    @Override
    public void onClickClose() {
        mSearchHomeAdapter.resetItems(mHomeCompanyList);
        mSearchHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void doFetchTop40Data(int a) {
        if (iSearchView.getCodeSnippet().hasNetworkConnection()) {
            SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
            searchHomeRequest.setPage(a);
            if (a == 0) {
                iSearchView.showProgressbar();
            }
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CompanyItemInfo>> call = apiService.callInstrumentTop40(searchHomeRequest);
            RetroFitBase.getInstance().retrofitEnqueue(call, mResponseListener, Constants.LoadCompanyList.LOAD_TOP_FORTY);
        } else {
            iSearchView.getCodeSnippet().showNetworkSettings();
        }

    }

    /***/
    private void updateListItem(int position, CompanyItemInfo homeSearchData) {
        if (mSearchHomeAdapter != null) {
            mSearchHomeAdapter.updateItem(homeSearchData, position);
        }
    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResultPresenter :" + "requestCode : " + requestCode + "resultCode : " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCodes.KEY_REQUEST_CODE_COMPANY_DETAILS:
                    if (data != null) {
                        CompanyItemInfo homeSearchData = data.getParcelableExtra(Constants.BundleKey.BUNDLE_COMPANY_ITEM);
                        int position = data.getIntExtra(Constants.BundleKey.BUNDLE_COMPANY_ITEM_POSITION, -1);
                        if (homeSearchData != null) {
                            updateListItem(position, homeSearchData);
                        }
                    }
                    break;
                case Constants.RequestCodes.NAVIGATE_REGISTRATION_PHASE_ONE:
                    if (!(TextUtils.isEmpty(mAppConfigurationManager.getUserId()))) {
                        onClickRemoveBanner(Constants.BannerType.FULL_REGISTRATION);
                    }
                    break;
                case Constants.RequestCodes.NAVIGATE_FICA_TO_UPLOAD:
                    if (mAppConfigurationManager.isFicaDetailCompleted()) {
                        onClickRemoveBanner(Constants.BannerType.FICA);
                    }
                    break;
                case Constants.RequestCodes.NAVIGATE_TO_ADDRESS:
                    if (mAppConfigurationManager.isAddressCompleted()) {
                        onClickRemoveBanner(Constants.BannerType.ADDRESS);
                    }
                    break;
                case Constants.RequestCodes.NAVIGATE_TO_ADDBANKDETAILS:
                    if (mAppConfigurationManager.isBankDetailCompleted()) {
                        onClickRemoveBanner(Constants.BannerType.BANK_DETAILS);
                    }
                    break;
            }
        }
    }


    /***/
    private class GetCompanyDetails extends AsyncTask<Integer, Void, List<CompanyItemInfo>> {

        int tempCurrentFilter = -1;
        List<CompanyItemInfo> mCompanysOpenInfo = new ArrayList<>();
        List<CompanyItemInfo> mCompanysSuspendInfo = new ArrayList<>();
        List<CompanyItemInfo> mCompanysNoInfo = new ArrayList<>();

        @Override
        protected List<CompanyItemInfo> doInBackground(Integer... integers) {
            List<CompanyItemInfo> mCompanysInfo = new ArrayList<>();
            this.tempCurrentFilter = integers[0];
            switch (integers[0]) {
                case Constants.LoadCompanyList.LOAD_FAVOURITE:
                    mCompanysInfo = mDatabaseManger.loadFavourites();
                    break;

                case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                    mCompanysInfo = mDatabaseManger.loadTopForty();
                    break;

                case Constants.LoadCompanyList.LOAD_DAY:
                    mCompanysInfo = mDatabaseManger.loadDay();
                    break;

                case Constants.LoadCompanyList.LOAD_ALTX:
                    mCompanysInfo = mDatabaseManger.loadAltx();
                    break;

                case Constants.LoadCompanyList.LOAD_REIT:
                    mCompanysInfo = mDatabaseManger.loadReit();
                    break;

                case Constants.LoadCompanyList.LOAD_BIG_BRANDS:
                    mCompanysInfo = mDatabaseManger.loadBigBrands();
                    break;

                case Constants.LoadCompanyList.LOAD_MOST_POPULAR:
                    mCompanysInfo = mDatabaseManger.loadMostPopular();
                    break;

                case Constants.LoadCompanyList.LOAD_A_Z:
                    mCompanysInfo = mDatabaseManger.loadDescendingOrder();
                    break;
            }

            for (int i = 0; i < mCompanysInfo.size(); i++) {
                Log.d(TAG, "doInBackground: " + mCompanysInfo.get(i).getInstrumentName());
            }

            if (mCompanysInfo != null) {
                for (int i = 0; i < mCompanysInfo.size(); i++) {
                    if (TextUtils.isEmpty(mCompanysInfo.get(i).getCompanyAvailabilityStatus())) {
                        mCompanysNoInfo.add(mCompanysInfo.get(i));
                    } else {
                        if (mCompanysInfo.get(i).getCompanyAvailabilityStatus().equalsIgnoreCase(ENABLED_COMPANIES_STATUS)) {
                            mCompanysSuspendInfo.add(mCompanysInfo.get(i));
                        } else {
                            mCompanysOpenInfo.add(mCompanysInfo.get(i));
                        }
                    }
                }
            }
            return mCompanysInfo;
        }

        @Override
        protected void onPostExecute(List<CompanyItemInfo> companyItemInfos) {
            super.onPostExecute(companyItemInfos);
            if (companyItemInfos == null) {
                iSearchView.setEmptyText(getString(R.string.no_result_found), 0);
                return;
            }

            companyItemInfos.clear();
            companyItemInfos.addAll(mCompanysOpenInfo);
            companyItemInfos.addAll(mCompanysSuspendInfo);
            companyItemInfos.addAll(mCompanysNoInfo);

            mHomeCompanyList = companyItemInfos;


            if (tempCurrentFilter == Constants.LoadCompanyList.LOAD_FAVOURITE) {
                iSearchView.setEmptyText(getString(R.string.txt_no_fav_found), companyItemInfos.size());
            } else {
                iSearchView.setEmptyText(getString(R.string.txt_error_search_no_result), companyItemInfos.size());
            }


            /*as per client requirement list is get sorted by venkat */
            if (tempCurrentFilter == Constants.LoadCompanyList.LOAD_A_Z ||
                    tempCurrentFilter == Constants.LoadCompanyList.LOAD_DAY ||
                    tempCurrentFilter == Constants.LoadCompanyList.LOAD_TOP_FORTY ||
                    tempCurrentFilter == Constants.LoadCompanyList.LOAD_ALTX ||
                    tempCurrentFilter == Constants.LoadCompanyList.LOAD_MOST_POPULAR ||
                    tempCurrentFilter == Constants.LoadCompanyList.LOAD_REIT ||
                    tempCurrentFilter == Constants.LoadCompanyList.LOAD_BIG_BRANDS) {
                Collections.sort(companyItemInfos, (obj1, obj2) -> {
                    // ## Ascending order
                    return obj1.getInstrumentName().compareToIgnoreCase(obj2.getInstrumentName());

                });
            }

            setAdapter(companyItemInfos);
        }
    }

    /***/
    private BaseRecyclerAdapterListener<CompanyItemInfo> mMailDataAdapterListener = new BaseRecyclerAdapterListener<CompanyItemInfo>() {
        @Override
        public void onClickItem(CompanyItemInfo data, int position) {
            Bundle bundle = new Bundle();
            Log.d(TAG, "onClickItem : " + data.getId());
            bundle.putParcelable(Constants.BundleKey.BUNDLE_COMPANY_ITEM, data);
            bundle.putLong(Constants.BundleKey.BUNDLE_COMPANY_ITEM_ID, data.getId());
            bundle.putInt(Constants.BundleKey.BUNDLE_COMPANY_ITEM_POSITION, position);
            bundle.putString(Constants.BundleKey.BUNDLE_COMPANY_FROM, getCurrentPageName(mCurrentFilterItem));
            iSearchView.navigateToActivity(bundle);
        }
    };
    private IGeneralResponseListener<List<CompanyItemInfo>> mResponseListener = new IGeneralResponseListener<List<CompanyItemInfo>>() {
        @Override
        public void onSuccess(List<CompanyItemInfo> companyList, int flag) {
            /*flag = 0 To getting day company list
             * flag =1 search api results
             * flag =2 To get top40 */
            iSearchView.dismissProgressbar();
            iSearchView.setProgressBarVisibility(View.GONE);

            if (companyList == null || companyList.size() <= 0) {
                iSearchView.showMessage("Data Not Found!,Try Again!");
                return;
            }

            switch (flag) {
                case Constants.LoadCompanyList.LOAD_DAY:
                    //mCurrentDayList.addAll(companyList);
                    setAdapter(companyList);
                    break;
                case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                    //mCurrentTopForty.addAll(companyList);
                    setAdapter(companyList);
                    break;
                case Constants.LoadCompanyList.LOAD_SEARCH:
                    setAdapter(companyList);
                    break;
                case Constants.LoadCompanyList.LOAD_FAVOURITE:
                    setAdapter(companyList);
                    break;
            }
        }

        @Override
        public void onFailure(String response, int flag) {
            switch (flag) {
                case Constants.LoadCompanyList.LOAD_SEARCH:
                    iSearchView.showMessage("No results found for the recommended search");
                    break;
            }
            iSearchView.dismissProgressbar();
            iSearchView.setProgressBarVisibility(View.GONE);
        }

        @Override
        public void onFailure(Throwable throwable, int flag) {
            iSearchView.showMessage(throwable.getMessage());
            iSearchView.dismissProgressbar();
            iSearchView.setProgressBarVisibility(View.GONE);
        }

        @Override
        public void showDialog(String response, int flag) {
            iSearchView.showMessage(response);
            iSearchView.dismissProgressbar();
            iSearchView.setProgressBarVisibility(View.GONE);

        }

        @Override
        public void showErrorDialog(String errorResponse, int flag) {
            iSearchView.showMessage(errorResponse);
            iSearchView.dismissProgressbar();
            iSearchView.setProgressBarVisibility(View.GONE);

        }

        @Override
        public void showInternalServerErrorDialog(String errorResponse, int flag) {
            iSearchView.showMessage(errorResponse);
            iSearchView.dismissProgressbar();
            iSearchView.setProgressBarVisibility(View.GONE);
        }

        @Override
        public void logOut(int flag) {

        }
    };

}
