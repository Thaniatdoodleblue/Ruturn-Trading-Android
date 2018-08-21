package com.returntrader.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.model.common.LineGraphDataSet;
import com.returntrader.presenter.GraphPresenter;
import com.returntrader.presenter.ipresenter.IGraphFragPresenter;
import com.returntrader.view.iview.IGraphView;
import com.returntrader.view.widget.CustomXAxisRenderer;
import com.returntrader.view.widget.CustomYRenderer;
import com.returntrader.view.widget.MyYAxisValueFormatter;
import com.returntrader.view.widget.ThreeYearAxisValueFormatter;
import com.returntrader.view.widget.XAxisValueFormatterForDay;
import com.returntrader.view.widget.YearAxisValueFormatter;


public class GraphFragment extends BaseFragment implements IGraphView, ViewTreeObserver.OnGlobalLayoutListener {
    private Spinner vSpinnerSort;
    private TextView vTvHigh;
    private TextView vTvnLow;
    private TextView vTvClose;
    private TextView vTvHighLightedData;
    private IGraphFragPresenter iGraphFragPresenter;
    private LineChart mLineChart;
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_graph, container, false);
        findByViews(mRootView);
        iGraphFragPresenter = new GraphPresenter(this);
        iGraphFragPresenter.onCreatePresenter(getArguments());
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        iGraphFragPresenter.onStartPresenter();
    }

    /***/
    private void findByViews(View view) {
        vSpinnerSort = view.findViewById(R.id.sortSpin);
        vSpinnerSort.setSelection(0);
        vSpinnerSort.setOnItemSelectedListener(onItemSelectedListener);

        mLineChart = view.findViewById(R.id.tv_line_chart);
        vTvHighLightedData = view.findViewById(R.id.tv_graph_data);
        vTvClose = view.findViewById(R.id.mtnClose);
        vTvHigh = view.findViewById(R.id.mtnHigh);
        vTvnLow = view.findViewById(R.id.mtnLow);

       /* vTvClose.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorGreen));
        vTvHigh.setTextColor(ContextCompat.getColor(view.getContext(), R.color.homePage));
        vTvnLow.setTextColor(ContextCompat.getColor(view.getContext(), R.color.mtnRed));*/

        mLineChart.getLegend().setEnabled(false);
        mLineChart.setPinchZoom(false);
        mLineChart.setDragEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.setHighlightPerDragEnabled(true);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setOnChartValueSelectedListener(onChartValueSelectedListener);
        mLineChart.setViewPortOffsets(60, 20, 60, 68);
        mLineChart.setExtraBottomOffset(30f);
//        mLineChart.setNoDataText(getString(R.string.txt_loading));
        mLineChart.setNoDataText(getString(R.string.txt_nodata));
    }

    /***/
    private OnChartValueSelectedListener onChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            vTvHighLightedData.setText(getCodeSnippet().formatStringTo2Decimal(e.getY()));
        }

        @Override
        public void onNothingSelected() {

        }
    };

    @Override
    public void showProgressbar() {
        //super.showProgressbar();
    }

    @Override
    public void dismissProgressbar() {
        //super.dismissProgressbar();
    }

    /***/
    private void clearHighLightText() {
        vTvHighLightedData.setText(R.string.txt_empty_string);
    }

    /***/
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            clearHighLightText();
            switch (position) {
                case 0:
                    iGraphFragPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_TODAY);
                    break;
                case 1:
                    iGraphFragPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_LAST_WEEK);
                    break;
                case 2:
                    iGraphFragPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_LAST_MONTH);
                    break;
                case 3:
                    iGraphFragPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_ONE_YEAR);
                    break;
                case 4:
                    iGraphFragPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_THREE_YEARS);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    @Override
    public void setGraphView(LineGraphDataSet lineGraphDataSet) {

        if (lineGraphDataSet.getLineData() == null) {
            mLineChart.clear();
        } else {
            YAxis y = mLineChart.getAxisLeft();
            y.setDrawGridLines(false);
            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            y.setValueFormatter(new MyYAxisValueFormatter());
            y.setLabelCount(5, false);
            y.setDrawAxisLine(false);

            mLineChart.setRendererLeftYAxis(new CustomYRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getAxisLeft(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT)));

            mLineChart.getAxisRight().setEnabled(false);
            mLineChart.clear();
            mLineChart.setXAxisRenderer(new XAxisRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getXAxis(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT)));
            XAxis x = mLineChart.getXAxis();
            CustomXAxisRenderer customXAxisRenderer = new CustomXAxisRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getXAxis(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT), lineGraphDataSet.getLabelList(), lineGraphDataSet.getFilterType());
            switch (lineGraphDataSet.getFilterType()) {
                case Constants.GraphFilter.FILTER_TYPE_TODAY:
                    if (lineGraphDataSet.getLabelList().size() > 1) {
                        mLineChart.setXAxisRenderer(customXAxisRenderer);
                        x.setValueFormatter(new XAxisValueFormatterForDay(lineGraphDataSet.getLabelList()));
                    } else {
                        x.setValueFormatter(new IndexAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    }
                    break;
                case Constants.GraphFilter.FILTER_TYPE_ONE_YEAR:
                    mLineChart.setXAxisRenderer(customXAxisRenderer);
                    x.setValueFormatter(new YearAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    break;
                case Constants.GraphFilter.FILTER_TYPE_THREE_YEARS:
                    mLineChart.setXAxisRenderer(customXAxisRenderer);
                    x.setValueFormatter(new ThreeYearAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    break;
                default:
                    x.setValueFormatter(new IndexAxisValueFormatter(lineGraphDataSet.getLabelList()));
            }
            x.setPosition(XAxis.XAxisPosition.BOTTOM);
            x.setDrawGridLines(false);
//            mLineChart.setExtraBottomOffset(50);
            x.setGranularityEnabled(true);
            mLineChart.setData(lineGraphDataSet.getLineData());
            mLineChart.invalidate();
        }
    }

    @Override
    public void setShareInfo(CompanyItemInfo itemInfo) {
        // vTvClose.setText(getString(R.string.open_price, getCodeSnippet().getDecimalPoint(itemInfo.getClosePrice())));
    }

    @Override
    public void setMinMaxValue(Float open, Float min, Float max) {
        if (getCodeSnippet() == null) {
            return;
        }

        if (open != null) {
            vTvClose.setText(getString(R.string.open_price, getCodeSnippet()
                    .getDecimalPoint(TraderApplication.getInstance()
                            .convertPriceCentToRand(open))));
        }

        if (min != null) {
            vTvnLow.setText(getString(R.string.low_price, getCodeSnippet()
                    .getDecimalPoint(TraderApplication.getInstance()
                            .convertPriceCentToRand(min))));
        }

        if (max != null) {
            vTvHigh.setText(getString(R.string.high_price, getCodeSnippet()
                    .getDecimalPoint(TraderApplication.getInstance()
                            .convertPriceCentToRand(max))));
        }
    }

    @Override
    public void onGlobalLayout() {
        //showMessage("onGlobalLayout");
        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        iGraphFragPresenter.callGraphApi();
    }
}
