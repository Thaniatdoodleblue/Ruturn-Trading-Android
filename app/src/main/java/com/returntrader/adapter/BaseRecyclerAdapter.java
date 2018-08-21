package com.returntrader.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.returntrader.adapter.viewholder.BaseViewHolder;

import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guru karthik on 06-12-2016.
 */

abstract class BaseRecyclerAdapter<T, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    protected String TAG = getClass().getSimpleName();
    private List<T> data;

    public BaseRecyclerAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        if (holder instanceof BaseViewHolder) {
            holder.setData(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public T getItem(int position) throws IndexOutOfBoundsException {
        return data.get(position);
    }

    public void clear() {
        if (data != null) {
            data = new ArrayList<T>();
            notifyDataSetChanged();
        }
    }

    public void addItem(T object) {
        data.add(object);
        notifyItemInserted(data.indexOf(object));
    }

    public void updateItem(T object, int index) {
        boolean inBounds = (index >= 0) && (index < data.size());
        Log.d(TAG,"inBounds :" +inBounds);
        if (inBounds) {
            data.set(index,object);
            notifyItemChanged(index);
        }
    }

    public void removeItem(int position) throws IndexOutOfBoundsException {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void resetItems(List<T> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public void addItems(List<T> data) {
        if (data != null) {
            int startRange = (this.data.size() - 1) > 0 ? data.size() - 1 : 0;
            this.data.addAll(data);
            notifyItemRangeChanged(startRange, data.size());
        }
    }


}
