package com.returntrader.database;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;

import java.util.List;

/**
 * Created by moorthy on 8/22/2017.
 */

public class DatabaseManager {

    private String TAG = getClass().getSimpleName();
    private Context mContext;

    public DatabaseManager(Context context) {
        this.mContext = context;
    }

    public List<CompanyItemInfo> loadDay() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .orderDesc(CompanyItemInfoDao.Properties.LastKnownDelayPrice)
                .build()
                .list();
    }


    public List<CompanyItemInfo> loadAll() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao().loadAll();
    }


    public List<CompanyItemInfo> loadFavourites() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.Favourite.eq(1))
                .build()
                .list();

    }

    public List<CompanyItemInfo> loadTopForty() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.IsTopFort.eq(1))
                .build()
                .list();

    }

    public void doFavourite(CompanyItemInfo itemInfo) {
        TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao().updateInTx(itemInfo);
    }


    public void updateCompanyInfo(CompanyItemInfo itemInfo) {
        TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao().updateInTx(itemInfo);
    }

    public List<CompanyItemInfo> searchCompany(String query) {
        query = "%" + query + "%";
        Log.d(TAG, "search query :" + query);
        return TraderApplication
                .getInstance()
                .getDaoSession()
                .getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .whereOr(
                        CompanyItemInfoDao.Properties.InstrumentName.like(query),
                        CompanyItemInfoDao.Properties.ISINCode.like(query),
                        CompanyItemInfoDao.Properties.ContractCode.like(query),
                        CompanyItemInfoDao.Properties.CompanyInfo.like(query),
                        CompanyItemInfoDao.Properties.SearchCriteria.like(query))
                .build()
                .list();
        /*  CompanyItemInfoDao.Properties.CompanyInfo.like(query),
                        CompanyItemInfoDao.Properties.SearchCriteria.like(query))*/
    }


    public CompanyItemInfo getCompanyItem(String isinCode) {
        return TraderApplication.
                getInstance()
                .getDaoSession()
                .getCompanyItemInfoDao()
                .queryBuilder()
//                .where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.ISINCode.eq(isinCode))
                .build().unique();

    }

    public void insertNotificationData(NotificationMeta notificationMeta) {
        TraderApplication.getInstance()
                .getDaoSession().getNotificationMetaDao()
                .insertOrReplaceInTx(notificationMeta);
    }

    public List<NotificationMeta> getNotificationList() {
        return TraderApplication.getInstance().getDaoSession().getNotificationMetaDao().loadAll();
    }

    public List<Long> getFavouriteCompany() {
        List<CompanyItemInfo> companyList = TraderApplication.getInstance()
                .getDaoSession()
                .getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.Favourite.eq(1))
                .build().list();
        if (companyList != null) {
            return Stream.of(companyList)
                    .map(CompanyItemInfo::getId)
                    .collect(Collectors.toList());
        }
        return null;
    }


    public List<CompanyItemInfo> loadAltx() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.IsAltx.eq(1))
                .build()
                .list();
    }

    public List<CompanyItemInfo> loadReit() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.IsReit.eq(1))
                .build()
                .list();
    }

    public List<CompanyItemInfo> loadBigBrands() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.IsBigBrand.eq(1))
                .build()
                .list();
    }

    public List<CompanyItemInfo> loadMostPopular() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .where(CompanyItemInfoDao.Properties.IsMostPopular.eq(1))
                .build()
                .list();
    }

    public List<CompanyItemInfo> loadDescendingOrder() {
        return TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao()
                .queryBuilder()
                //.where(CompanyItemInfoDao.Properties.CompanyAvailabilityStatus.notEq(Constants.Common.ENABLED_COMPANIES_STATUS))
                .orderAsc(CompanyItemInfoDao.Properties.InstrumentName)
                .build()
                .list();
    }

    public void setFavouriteList(String favourites) {
        if (!(TextUtils.isEmpty(favourites))) {
            String[] favListId = favourites.split(",");
            if (favListId.length > 0) {
                for (String uniqueId : favListId) {
                    Long unique = Long.parseLong(uniqueId.trim());
                    CompanyItemInfo info = TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao().load(unique);
                    if (info != null) {
                        info.setFavourite(1);
                        TraderApplication.getInstance().getDaoSession().getCompanyItemInfoDao().update(info);
                    }
                }
            }
        }
    }
}
