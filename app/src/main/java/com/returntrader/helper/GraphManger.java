package com.returntrader.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.DayHistoryDo;
import com.returntrader.model.common.LineGraphDataSet;
import com.returntrader.model.dto.request.HistoryRequest;
import com.returntrader.model.dto.response.DayHistoryPriceResponse;
import com.returntrader.model.dto.response.HistoryResponse;
import com.returntrader.model.imodel.HistoricPriceLogs;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.DayHistoryPriceModel;
import com.returntrader.model.webservice.GetHistoryModel;
import com.returntrader.utils.CodeSnippet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by moorthy on 10/5/2017.
 */

public class GraphManger {
    private String TAG = getClass().getSimpleName();
    private List<HistoricPriceLogs> mHistoricPriceLogs;
    private List<DayHistoryDo> mDayHistoryDoList;
    private int i = 0;
    private String EMPTY = "empty";
    private String DEFAULT_PRICE = "0.00";
    private OnGraphLoadedListener iOnGraphLoadedListener;
    private CodeSnippet mCodeSnippet;
    private Context mContext;
    private String isinCode;
    private AppConfigurationManager mAppConfigurationManager;
    private GetHistoryModel mGetHistoryModel;
    private DayHistoryPriceModel mDayHistoryPriceModel;


    public GraphManger(Context context, OnGraphLoadedListener iOnGraphLoadedListener) {
        this.mContext = context;
        this.iOnGraphLoadedListener = iOnGraphLoadedListener;
        this.mCodeSnippet = new CodeSnippet(context);
        this.mAppConfigurationManager = new AppConfigurationManager(context);
        this.mGetHistoryModel = new GetHistoryModel(historyResponseListener);
        this.mDayHistoryPriceModel = new DayHistoryPriceModel(priceResponseIBaseModelListener);

    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void fetchGraphDetails(String isinCode) {
        setIsinCode(isinCode);
        callApi();
    }

    public void fetchGraphDetails(CompanyItemInfo companyItemInfoDao) {
        setIsinCode(companyItemInfoDao.getISINCode());
        callApi();
    }

    private void callApi() {
        getDayHistoryData(getIsinCode()); // For day graph
        getHistoryData(getIsinCode());
    }


    private void showProgressbar() {
        if (iOnGraphLoadedListener != null)
            iOnGraphLoadedListener.showGraphLoadingProgressBar();
    }

    private void showNetworkMessage() {
        if (iOnGraphLoadedListener != null)
            iOnGraphLoadedListener.showNetworkMessage();
    }

    private void dismissProgressbar() {
        if (iOnGraphLoadedListener != null)
            iOnGraphLoadedListener.dismissGraphLoadingProgressBar();
    }


    private void getHistoryData(String isinCode) {
        if (mCodeSnippet.hasNetworkConnection()) {
            showProgressbar();
            mGetHistoryModel.getHistoryLogs(0, new HistoryRequest(mAppConfigurationManager.getUserId(), isinCode));
        } else {
            showNetworkMessage();
        }
    }

    private void getDayHistoryData(String isinCode) {
        if (mCodeSnippet.hasNetworkConnection()) {
            showProgressbar();
            mDayHistoryPriceModel.getDayHistoryPrice(new HistoryRequest(mAppConfigurationManager.getUserId(), isinCode));
        } else {
            showNetworkMessage();
        }
    }


    private IBaseModelListener<HistoryResponse> historyResponseListener = new IBaseModelListener<HistoryResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, HistoryResponse response) {
            dismissProgressbar();
            if (response == null) {
                return;
            }

            if (response.getGraphHistoryData() == null) {
                return;
            }
            setHistoricPriceLogs(response.getGraphHistoryData());
            Stream.of(mHistoricPriceLogs).sortBy(HistoricPriceLogs::getFormatterDate);
            //onLoad(Constants.GraphFilter.FILTER_TYPE_LAST_WEEK);
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            dismissProgressbar();
        }
    };


    private IBaseModelListener<DayHistoryPriceResponse> priceResponseIBaseModelListener = new IBaseModelListener<DayHistoryPriceResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, DayHistoryPriceResponse response) {
            dismissProgressbar();
            if (response == null) {
                return;
            }

            if (response.getDayHistoryList() == null || response.getDayHistoryList().size() <= 0) {
                return;
            }
            setDayHistoryDoList(response.getDayHistoryList());
            Stream.of(mDayHistoryDoList).sortBy(DayHistoryDo::getFormatterDate);
            calculateHighLowForDay(response.getDayHistoryList());
            onLoad(Constants.GraphFilter.FILTER_TYPE_TODAY);

        }


        @Override
        public void onFailureApi(long taskId, CustomException e) {
            dismissProgressbar();
        }
    };


    private void calculateHighLowForDay(List<DayHistoryDo> dayHistoryList) {


        if (dayHistoryList == null || dayHistoryList.size() <= 0) {
            return;
        }

        DayHistoryDo max = null;
        try {
            max = Stream.of(dayHistoryList).max((dayHistoryDo, t1) -> Float.compare(dayHistoryDo.getLast(), t1.getLast())).get();
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
        }
        DayHistoryDo min = null;
        try {
            min = Stream.of(dayHistoryList).min((dayHistoryDo, t1) -> Float.compare(dayHistoryDo.getLast(), t1.getLast())).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        setMinMaxValue(dayHistoryList.get(0).getLast(), min != null ? min.getLast() : 0, max != null ? max.getLast() : 0);
    }


    /***/
    private void calculateHighLow(List<HistoricPriceLogs> dayHistoryList) {
        Log.e("DayHistoryList", "" + dayHistoryList.size());
        if (dayHistoryList == null || dayHistoryList.size() <= 0) {
            return;
        }

        Log.e("DayHistoryList", "" + dayHistoryList.size());

        HistoricPriceLogs max = null;
        try {
            max = Stream.of(dayHistoryList).max((dayHistoryDo, t1) -> Float.compare(getPrice(dayHistoryDo.getPrice()), getPrice(t1.getPrice()))).get();
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
        }
        HistoricPriceLogs min = null;
        try {
            min = Stream.of(dayHistoryList).min((dayHistoryDo, t1) -> Float.compare(getPrice(dayHistoryDo.getPrice()), getPrice(t1.getPrice()))).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        Log.e("Open = " + dayHistoryList.get(0).getPrice(), "Min = " + min.getPrice() + " & Max = " + max.getPrice());

        setMinMaxValue(getPrice(dayHistoryList.get(0).getPrice()), min != null ? getPrice(min.getPrice()) : 0, max != null ? getPrice(max.getPrice()) : 0);
    }

    /***/
    private void setMinMaxValue(Float open, Float min, Float max) {
        if (iOnGraphLoadedListener != null)
            iOnGraphLoadedListener.setHighLowAmount(open, min, max);
    }

    /***/
    private void setHistoricPriceLogs(List<HistoricPriceLogs> historicPriceLogses) {
        this.mHistoricPriceLogs = historicPriceLogses;
    }

    /***/
    private void setDayHistoryDoList(List<DayHistoryDo> dayHistoryDoList) {
        this.mDayHistoryDoList = dayHistoryDoList;
    }

    /***/
    private void setEmptyGraph() {
        LineGraphDataSet lineGraphDataSet = new LineGraphDataSet();
        lineGraphDataSet.setLineData(null);
        setMinMaxValue(getPrice(Constants.Common.DEFAULT_PRICE_ZERO),
                getPrice(Constants.Common.DEFAULT_PRICE_ZERO),
                getPrice(Constants.Common.DEFAULT_PRICE_ZERO));
        setDataOnListener(lineGraphDataSet);
    }

    /***/
    private void setDataOnListener(LineGraphDataSet lineGraphDataSet) {
        if (iOnGraphLoadedListener != null)
            iOnGraphLoadedListener.loadGraphView(lineGraphDataSet);

        if (lineGraphDataSet.getFilterType() == Constants.GraphFilter.FILTER_TYPE_TODAY) {
            calculateHighLowForDay(mDayHistoryDoList);
        } else {
            if (lineGraphDataSet.getGraphSourceData() != null) {
                calculateHighLow(lineGraphDataSet.getGraphSourceData());
            }
        }
    }


    /***/
    private boolean isHistoryLogsNull() {
        if (mHistoricPriceLogs == null) {
            Log.d(TAG, "historyData null");
            return true;
        }
        return false;
    }


    /***/
    private Float getPrice(String graphPrice) {
        String price = removeExtraString(graphPrice);
        if (price.equalsIgnoreCase(EMPTY)) {
            price = DEFAULT_PRICE;
        }
        return Float.valueOf(price);
    }


    List<HistoricPriceLogs> historicPriceGraphData;

    /***/
    private LineGraphDataSet getFilterData(int type) {
        LineGraphDataSet lineGraphDataSet = new LineGraphDataSet();
        switch (type) {
            case Constants.GraphFilter.FILTER_TYPE_TODAY:
                /*String grphData = mCodeSnippet.loadJSONFromAsset("mockup_graph_data.json");
                DayHistoryPriceResponse dayHistoryPriceResponse  = mCodeSnippet.getObjectFromJsonStringRetro(grphData,DayHistoryPriceResponse.class);*/

                if (mDayHistoryDoList == null || mDayHistoryDoList.size() <= 0) {
                    return null;
                } else {
                    // mDayHistoryDoList = mDayHistoryDoList.subList(0,4);

                    Log.d("mDayHistoryDoList = ", "" + mDayHistoryDoList.size());

                    if (mDayHistoryDoList.size() > 33) {
                        mDayHistoryDoList = mDayHistoryDoList.subList(0, 33);
                    }

                    ArrayList<Entry> entriesDay = new ArrayList<>();
                    List<String> labels = new ArrayList<>(mDayHistoryDoList.size());
                    for (int j = 0; j < mDayHistoryDoList.size(); j++) {
                        DayHistoryDo log = mDayHistoryDoList.get(j);
                        Float yValue = log.getLast();
                        yValue = convertCentToZar(yValue);
                        entriesDay.add(new Entry(j, yValue));
                        labels.add(mCodeSnippet.getDayTimeText(log.getFormatterDate()));
                    }

                    LineDataSet dataSetDay = new LineDataSet(entriesDay, "");
//                    dataSetDay.setCubicIntensity(0.0f);
                    dataSetDay = getLineChartConfiguration(dataSetDay, type);
                    LineData lineData = new LineData(dataSetDay);
                    lineGraphDataSet.setLabelList(labels);
                    lineGraphDataSet.setLineData(lineData);
                    lineGraphDataSet.setFilterType(type);
                    return lineGraphDataSet;
                }

            case Constants.GraphFilter.FILTER_TYPE_LAST_WEEK:
                if (isHistoryLogsNull()) {
                    return null;
                }

                List<HistoricPriceLogs> weekGraphData =
                        new ArrayList<>(mHistoricPriceLogs.subList(mHistoricPriceLogs.size() - 5, mHistoricPriceLogs.size()));

                ArrayList<Entry> entriesWeek = new ArrayList<>();

                List<String> labels = new ArrayList<>(weekGraphData.size());
                for (int j = 0; j < weekGraphData.size(); j++) {
                    HistoricPriceLogs log = weekGraphData.get(j);
                    //Log.d(TAG, "date " + log.getClosingDate());
                    String price = removeExtraString(log.getPrice());
                    if (price.equalsIgnoreCase(EMPTY)) {
                        price = DEFAULT_PRICE;
                    }

                    Float yValue = Float.valueOf(price);
                    if (yValue == null) {
                        yValue = 0F;
                    }

                    yValue = convertCentToZar(yValue);
                    entriesWeek.add(new Entry(j, yValue));
                    labels.add(mCodeSnippet.getDayText(log.getFormatterDate()));
                }

                LineDataSet dataSetWeek = new LineDataSet(entriesWeek, "");
//                dataSetWeek.setCubicIntensity(0.0f);
                dataSetWeek = getLineChartConfiguration(dataSetWeek, type);
                LineData lineData = new LineData(dataSetWeek);
                lineGraphDataSet.setLabelList(labels);
                lineGraphDataSet.setLineData(lineData);
                lineGraphDataSet.setGraphSourceData(weekGraphData);
                lineGraphDataSet.setFilterType(type);
                return lineGraphDataSet;


            case Constants.GraphFilter.FILTER_TYPE_LAST_MONTH:
                if (isHistoryLogsNull()) {
                    return null;
                }

                Pair<Date, Date> yearDatePairMonth = mCodeSnippet.getMonthFirstLastDate();
                Date yearStartDateMonth = yearDatePairMonth.first;
                Date yearCurrentDateMonth = yearDatePairMonth.second;

                Log.d(TAG, "month start label : " + yearStartDateMonth + "month end label : " + yearCurrentDateMonth);

                final int[] i = {0};
                ArrayList<Entry> entriesMonth = new ArrayList<>();
                List<String> labelsMonth = new ArrayList<>();
                List<HistoricPriceLogs> yearGraphData = Stream.of(mHistoricPriceLogs).filter((HistoricPriceLogs log) -> {
                    if (new Date(yearCurrentDateMonth.getTime()).after(log.getFormatterDate()) &&
                            new Date(yearStartDateMonth.getTime()).before(log.getFormatterDate()) ||
                            ((new Date(yearCurrentDateMonth.getTime()).equals(log.getFormatterDate())) ||
                                    (new Date(yearStartDateMonth.getTime()).equals(log.getFormatterDate())))) {

                        //Log.d(TAG, "date " + log.getClosingDate());
                        String price = removeExtraString(log.getPrice());
                        if (price.equalsIgnoreCase(EMPTY)) {
                            price = DEFAULT_PRICE;
                        }
                        Float yValue = Float.valueOf(price);
                        if (yValue == null) {
                            yValue = 0F;
                        }

                        yValue = convertCentToZar(yValue);
                        entriesMonth.add(new Entry(i[0], yValue));
                        labelsMonth.add(mCodeSnippet.getNumberDayText(log.getFormatterDate()));
                        i[0] = i[0] + 1;
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());


                if (entriesMonth.size() > 0 && labelsMonth.size() > 0) {
                    LineDataSet dataSetMonth = new LineDataSet(entriesMonth, "month");
//                    dataSetMonth.setCubicIntensity(0.0f);
                    dataSetMonth = getLineChartConfiguration(dataSetMonth, type);
                    LineData lineDataMonth = new LineData(dataSetMonth);
                    lineGraphDataSet.setLineData(lineDataMonth);
                    lineGraphDataSet.setLabelList(labelsMonth);
                    lineGraphDataSet.setFilterType(type);
                    lineGraphDataSet.setGraphSourceData(yearGraphData);
                    // setDataOnListener(lineGraphDataSet);
                    return lineGraphDataSet;
                } else {
                    return null;
                }

            case Constants.GraphFilter.FILTER_TYPE_ONE_YEAR:
                if (isHistoryLogsNull()) {
                    return null;
                }

                Pair<Date, Date> yearDatePair = mCodeSnippet.getLastYearFirstLastDate();
                Date yearStartDate = yearDatePair.first;
                Date yearCurrentDate = yearDatePair.second;

                Log.d(TAG, "year start label : " + yearStartDate + "year end label : " + yearCurrentDate);
                final int[] iteration = {0};
                List<String> labelsYear = new ArrayList<>();
                ArrayList<Entry> entriesYear = new ArrayList<>();

                List<HistoricPriceLogs> historicPriceYear = Stream.of(mHistoricPriceLogs).filter((HistoricPriceLogs log) -> {
                    if (new Date(yearCurrentDate.getTime()).after(log.getFormatterDate()) &&
                            new Date(yearStartDate.getTime()).before(log.getFormatterDate()) ||
                            ((new Date(yearCurrentDate.getTime()).equals(log.getFormatterDate())) ||
                                    (new Date(yearStartDate.getTime()).equals(log.getFormatterDate())))) {

                        String price = removeExtraString(log.getPrice());
                        if (price.equalsIgnoreCase(EMPTY)) {
                            price = DEFAULT_PRICE;
                        }
                        Float yValue = Float.valueOf(price);
                        if (yValue == null) {
                            yValue = 0F;
                        }

                        yValue = convertCentToZar(yValue);
                        entriesYear.add(new Entry(iteration[0], yValue));
                        labelsYear.add(mCodeSnippet.getMonthText(log.getFormatterDate()));
                        iteration[0] = iteration[0] + 1;
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

                if (labelsYear.size() > 0 && entriesYear.size() > 0) {
                    //New implementation
                    LineDataSet dataSetYeaer = new LineDataSet(entriesYear, "");
//                    dataSetYeaer.setCubicIntensity(0.0f);
                    dataSetYeaer = getLineChartConfiguration(dataSetYeaer, type);
                    LineData lineDataYear = new LineData(dataSetYeaer);
                    lineGraphDataSet.setLabelList(labelsYear);
                    lineGraphDataSet.setLineData(lineDataYear);
                    lineGraphDataSet.setGraphSourceData(historicPriceYear);
                    lineGraphDataSet.setFilterType(type);
                    return lineGraphDataSet;
                } else {
                    return null;
                }

            case Constants.GraphFilter.FILTER_TYPE_THREE_YEARS:
                if (isHistoryLogsNull()) {
                    return null;
                }

                Pair<Date, Date> threeYearDatePair = mCodeSnippet.getThreeYearFirstLastDate();
                Date threeYearStartDate = threeYearDatePair.first;
                Date threeYearCurrentDate = threeYearDatePair.second;

                Log.d(TAG, "three year start label : " + threeYearStartDate + "three year end label : " + threeYearCurrentDate);
                final int[] iterationThreeYear = {0};
                ArrayList<Entry> entriesThreeYears = new ArrayList<>();
                List<String> labelsThreeYear = new ArrayList<>();
                historicPriceGraphData = new ArrayList<>();

                Stream.of(mHistoricPriceLogs).filter((HistoricPriceLogs log) -> {
                    if (new Date(threeYearCurrentDate.getTime()).after(log.getFormatterDate()) &&
                            new Date(threeYearStartDate.getTime()).before(log.getFormatterDate()) ||
                            ((new Date(threeYearCurrentDate.getTime()).equals(log.getFormatterDate())) ||
                                    (new Date(threeYearStartDate.getTime()).equals(log.getFormatterDate())))) {
                        String price = removeExtraString(log.getPrice());
                        if (price.equalsIgnoreCase(EMPTY)) {
                            price = DEFAULT_PRICE;
                        }
                        Float yValue = Float.valueOf(price);
                        if (yValue == null) {
                            yValue = 0F;
                        }

                        Log.d("Running", "3 Years Data Stream");
                        yValue = convertCentToZar(yValue);
                        entriesThreeYears.add(new Entry(iterationThreeYear[0], yValue));
                        labelsThreeYear.add(mCodeSnippet.getYearText(log.getFormatterDate()));
                        iterationThreeYear[0] = iterationThreeYear[0] + 1;
                        historicPriceGraphData.add(log);
                    }
                    return false;
                }).collect(Collectors.toList());

                Log.e("3 Years Entries " + historicPriceGraphData.size(), "" + entriesThreeYears.size());

                if (entriesThreeYears.size() > 0) {
                    LineDataSet dataSetYear = new LineDataSet(entriesThreeYears, "");
//                    dataSetYear.setCubicIntensity(0.0f);
                    dataSetYear = getLineChartConfiguration(dataSetYear, type);
                    LineData lineDataThreeYear = new LineData(dataSetYear);
                    lineGraphDataSet.setLabelList(labelsThreeYear);
                    lineGraphDataSet.setGraphSourceData(historicPriceGraphData);
                    lineGraphDataSet.setLineData(lineDataThreeYear);
                    lineGraphDataSet.setFilterType(type);
                    Log.e("LineGraphDataSet = ", "" + historicPriceGraphData.size());
                    return lineGraphDataSet;
                } else {
                    return null;
                }

        }
        return null;
    }

    private boolean isCompanyAvailable;

    /***/
    public void isCompanyAvailable(boolean isCompanyAvailable) {
        this.isCompanyAvailable = isCompanyAvailable;
    }


    /***/
    private class GetFilterData extends AsyncTask<Integer, Void, LineGraphDataSet> {

        @Override
        protected LineGraphDataSet doInBackground(Integer... type) {
            return getFilterData(type[0]);
        }

        @Override
        protected void onPostExecute(LineGraphDataSet lineGraphDataSet) {
            super.onPostExecute(lineGraphDataSet);

            if (lineGraphDataSet == null) {
                setEmptyGraph();
                return;
            }

            if (lineGraphDataSet.getLabelList().size() > 0) {
                setDataOnListener(lineGraphDataSet);
            } else {
                setEmptyGraph();
            }
        }
    }


    /***/
    public void onLoad(int type) {
        Log.d(TAG, "type :" + type);
        new GetFilterData().execute(type);
    }

    /***/
    private String removeExtraString(String input) {
        /*if (TextUtils.isEmpty(input)) {
            return null;
        }*/
        return input.replaceAll("\\r", "").trim();
    }


    /***/
    private Float convertCentToZar(Float price) {
        return TraderApplication.getInstance().convertPriceCentToRand(price);
    }


    /***/
    private LineDataSet getGraphColors(LineDataSet set) {
        if (mAppConfigurationManager.isMarketAvailable()) {
            set = showAsEnabled(set);
        } else {
            set = showAsSuspended(set);
        }

        if (!isCompanyAvailable) {
            set = showAsSuspended(set);
        }
        return set;
    }


    /***/
    private LineDataSet showAsEnabled(LineDataSet set) {
        set.setFillColor(ColorTemplate.rgb("#288fc9"));
        set.setColor(ColorTemplate.rgb("#1F618D"));
        set.setHighLightColor(ColorTemplate.rgb("#1F618D"));
        return set;
    }

    /***/
    private LineDataSet showAsSuspended(LineDataSet set) {
        set.setFillColor(ColorTemplate.rgb("#808D93"));
        set.setColor(ColorTemplate.rgb("#6A757B"));
        set.setHighLightColor(ColorTemplate.rgb("#6A757B"));
        return set;
    }

//    /**To remove curvy edges*/
//    private LineDataSet getLineChartConfiguration(LineDataSet set, int filterType) {
//        set.setMode(LineDataSet.Mode.LINEAR);
//        set.setDrawFilled(true);
//        set.setDrawValues(false);
//        set.setDrawCircles(false);
//        set.setHighlightEnabled(true);
//        set.setDrawVerticalHighlightIndicator(true);
//        set.setDrawHorizontalHighlightIndicator(false);
//        set.setHighlightLineWidth(1.0f);
//        switch (filterType) {
//            case Constants.GraphFilter.FILTER_TYPE_TODAY:
//                set = getGraphColors(set);
//                break;
//            default:
//                set = showAsEnabled(set);//getGraphColors(set);
//        }
//        set.setFillAlpha(255);
//        set.setLineWidth(1.0f);
//        return set;
//    }


    /***/
    private LineDataSet getLineChartConfiguration(LineDataSet set, int filterType) {
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawFilled(true);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setHighlightEnabled(true);
        set.setDrawVerticalHighlightIndicator(true);
        set.setDrawHorizontalHighlightIndicator(false);
        set.setHighlightLineWidth(1.0f);
        switch (filterType) {
            case Constants.GraphFilter.FILTER_TYPE_TODAY:
                set = getGraphColors(set);
                break;
            default:
                set = showAsEnabled(set);//getGraphColors(set);
        }
        set.setFillAlpha(255);
        set.setLineWidth(1.0f);
        return set;
    }


    /***/
    public interface OnGraphLoadedListener {

        void loadGraphView(LineGraphDataSet lineDataSet);

        void showGraphLoadingProgressBar();

        void dismissGraphLoadingProgressBar();

        void showNetworkMessage();

        void setHighLowAmount(Float open, Float min, Float max);
    }
}
