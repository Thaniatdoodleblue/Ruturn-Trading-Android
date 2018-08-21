package com.returntrader.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
@Entity
public class CompanyItemInfo implements Parcelable {

    @Id(autoincrement = false)
    @JsonField(name = "id")
    private Long id;

    @JsonField(name = "isin_code")
    private String ISINCode;

    @JsonField(name = "contarct_code")
    private String contractCode;

    @JsonField(name = "instrument_name")
    private String instrumentName;

    @JsonField(name = "logo_url")
    private String companyImageUrl;

    @JsonField(name = "company_info")
    private String companyInfo;

    @JsonField(name = "search_criteria")
    private String searchCriteria;

    private String companyAvailabilityStatus;

    @JsonField(name = "updated_at")
    private String updatedAt;

    @JsonField(name = "created_at")
    private String createdAt;

    @JsonField(name = "favourite")
    private Integer favourite = -1;

    @JsonField(name = "top_40")
    private Integer isTopFort = 0;

    @JsonField(name = "reit")
    private Integer isReit = 0;

    @JsonField(name = "big_brands")
    private Integer isBigBrand = 0;

    @JsonField(name = "altx")
    private Integer isAltx = 0;

    @JsonField(name = "most_popular")
    private Integer isMostPopular = 0;

    @JsonField(name = "main")
    private Integer isMain = 0;

    private Float closePrice = 0F;

    private Float bid = 0F;

    private Float offer = 0F;

    private Float lastPrice = 0F;

    private Float lastKnownDelayPrice = 0F;

    private String priceStatus;

    private String graphData = "[]";

    private String maxTradeAmount;

    public CompanyItemInfo() {

    }

    protected CompanyItemInfo(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        ISINCode = in.readString();
        contractCode = in.readString();
        instrumentName = in.readString();
        companyImageUrl = in.readString();
        companyInfo = in.readString();
        searchCriteria = in.readString();
        companyAvailabilityStatus = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        favourite = in.readByte() == 0x00 ? null : in.readInt();
        isTopFort = in.readByte() == 0x00 ? null : in.readInt();
        isReit = in.readByte() == 0x00 ? null : in.readInt();
        isBigBrand = in.readByte() == 0x00 ? null : in.readInt();
        isAltx = in.readByte() == 0x00 ? null : in.readInt();
        isMostPopular = in.readByte() == 0x00 ? null : in.readInt();
        isMain = in.readByte() == 0x00 ? null : in.readInt();
        closePrice = in.readByte() == 0x00 ? null : in.readFloat();
        bid = in.readByte() == 0x00 ? null : in.readFloat();
        offer = in.readByte() == 0x00 ? null : in.readFloat();
        lastPrice = in.readByte() == 0x00 ? null : in.readFloat();
        lastKnownDelayPrice = in.readByte() == 0x00 ? null : in.readFloat();
        priceStatus = in.readString();
        graphData = in.readString();
        maxTradeAmount = in.readString();
    }

    @Generated(hash = 1812176751)
    public CompanyItemInfo(Long id, String ISINCode, String contractCode, String instrumentName, String companyImageUrl, String companyInfo, String searchCriteria,
            String companyAvailabilityStatus, String updatedAt, String createdAt, Integer favourite, Integer isTopFort, Integer isReit, Integer isBigBrand, Integer isAltx, Integer isMostPopular,
            Integer isMain, Float closePrice, Float bid, Float offer, Float lastPrice, Float lastKnownDelayPrice, String priceStatus, String graphData, String maxTradeAmount) {
        this.id = id;
        this.ISINCode = ISINCode;
        this.contractCode = contractCode;
        this.instrumentName = instrumentName;
        this.companyImageUrl = companyImageUrl;
        this.companyInfo = companyInfo;
        this.searchCriteria = searchCriteria;
        this.companyAvailabilityStatus = companyAvailabilityStatus;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.favourite = favourite;
        this.isTopFort = isTopFort;
        this.isReit = isReit;
        this.isBigBrand = isBigBrand;
        this.isAltx = isAltx;
        this.isMostPopular = isMostPopular;
        this.isMain = isMain;
        this.closePrice = closePrice;
        this.bid = bid;
        this.offer = offer;
        this.lastPrice = lastPrice;
        this.lastKnownDelayPrice = lastKnownDelayPrice;
        this.priceStatus = priceStatus;
        this.graphData = graphData;
        this.maxTradeAmount = maxTradeAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(id);
        }
        dest.writeString(ISINCode);
        dest.writeString(contractCode);
        dest.writeString(instrumentName);
        dest.writeString(companyImageUrl);
        dest.writeString(companyInfo);
        dest.writeString(searchCriteria);
        dest.writeString(companyAvailabilityStatus);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        if (favourite == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(favourite);
        }
        if (isTopFort == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(isTopFort);
        }
        if (isReit == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(isReit);
        }
        if (isBigBrand == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(isBigBrand);
        }
        if (isAltx == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(isAltx);
        }
        if (isMostPopular == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(isMostPopular);
        }
        if (isMain == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(isMain);
        }
        if (closePrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(closePrice);
        }
        if (bid == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(bid);
        }
        if (offer == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(offer);
        }
        if (lastPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(lastPrice);
        }
        if (lastKnownDelayPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(lastKnownDelayPrice);
        }
        dest.writeString(priceStatus);
        dest.writeString(graphData);
        dest.writeString(maxTradeAmount);
    }

    @SuppressWarnings("unused")
    public static final Creator<CompanyItemInfo> CREATOR = new Creator<CompanyItemInfo>() {
        @Override
        public CompanyItemInfo createFromParcel(Parcel in) {
            return new CompanyItemInfo(in);
        }

        @Override
        public CompanyItemInfo[] newArray(int size) {
            return new CompanyItemInfo[size];
        }
    };


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getISINCode() {
        return ISINCode;
    }

    public void setISINCode(String ISINCode) {
        this.ISINCode = ISINCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getCompanyImageUrl() {
        return companyImageUrl;
    }

    public void setCompanyImageUrl(String companyImageUrl) {
        this.companyImageUrl = companyImageUrl;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getCompanyAvailabilityStatus() {
        return companyAvailabilityStatus;
    }

    public void setCompanyAvailabilityStatus(String companyAvailabilityStatus) {
        this.companyAvailabilityStatus = companyAvailabilityStatus;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    public Integer getIsTopFort() {
        return isTopFort;
    }

    public void setIsTopFort(Integer isTopFort) {
        this.isTopFort = isTopFort;
    }

    public Integer getIsReit() {
        return isReit;
    }

    public void setIsReit(Integer isReit) {
        this.isReit = isReit;
    }

    public Integer getIsBigBrand() {
        return isBigBrand;
    }

    public void setIsBigBrand(Integer isBigBrand) {
        this.isBigBrand = isBigBrand;
    }

    public Integer getIsAltx() {
        return isAltx;
    }

    public void setIsAltx(Integer isAltx) {
        this.isAltx = isAltx;
    }

    public Integer getIsMostPopular() {
        return isMostPopular;
    }

    public void setIsMostPopular(Integer isMostPopular) {
        this.isMostPopular = isMostPopular;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public Float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Float closePrice) {
        this.closePrice = closePrice;
    }

    public Float getBid() {
        return bid;
    }

    public void setBid(Float bid) {
        this.bid = bid;
    }

    public Float getOffer() {
        return offer;
    }

    public void setOffer(Float offer) {
        this.offer = offer;
    }

    public Float getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Float getLastKnownDelayPrice() {
        return lastKnownDelayPrice;
    }

    public void setLastKnownDelayPrice(Float lastKnownDelayPrice) {
        this.lastKnownDelayPrice = lastKnownDelayPrice;
    }

    public String getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(String priceStatus) {
        this.priceStatus = priceStatus;
    }

    public String getGraphData() {
        return graphData;
    }

    public void setGraphData(String graphData) {
        this.graphData = graphData;
    }

    public static Creator<CompanyItemInfo> getCREATOR() {
        return CREATOR;
    }

    private List<Float> getListFromJsonString(String jsonString) {
        try {
            return LoganSquare.parseList(jsonString, Float.class);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return new ArrayList<Float>();

    }

    public String getStringGraphList(List<Float> floatList) {
        if (floatList != null) {
            try {
                return LoganSquare.serialize(floatList);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Float> getGraphList() {
        if (!(TextUtils.isEmpty(graphData))) {
            return getListFromJsonString(graphData);
        }
        return new ArrayList<Float>();
    }

    public String getMaxTradeAmount() {
        return maxTradeAmount;
    }

    public void setMaxTradeAmount(String maxTradeAmount) {
        this.maxTradeAmount = maxTradeAmount;
    }
}