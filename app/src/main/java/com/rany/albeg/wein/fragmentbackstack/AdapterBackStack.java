package com.rany.albeg.wein.fragmentbackstack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Stack;

/**
 * Created by Rany Albeg Wein on 26/04/2017.
 * This file is a part of FragmentBackStack project.
 */

class AdapterBackStack extends RecyclerView.Adapter<AdapterBackStack.MyViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private Stack<String> mBackStackEntries;
    private MyViewHolder.OnStackEntryClickedListener mStackEntryClickedListener;

    AdapterBackStack(Context context,
                     MyViewHolder.OnStackEntryClickedListener stackEntryClickedListener) {
        mStackEntryClickedListener = stackEntryClickedListener;
        mBackStackEntries = new Stack<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.item_rv_back_stack, parent, false);
        return new MyViewHolder(rootView, mStackEntryClickedListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String entryName = mBackStackEntries.get(position);

        TextView textViewEntry = (TextView) holder.itemView;
        textViewEntry.setText(entryName);
    }

    void push(String entryName) {
        mBackStackEntries.push(entryName);
    }

    void pop() {
        mBackStackEntries.pop();
    }

    @Override
    public int getItemCount() {
        return mBackStackEntries.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnStackEntryClickedListener mStackEntryClickedListener;

        MyViewHolder(View itemView, OnStackEntryClickedListener stackEntryClickedListener) {
            super(itemView);
            mStackEntryClickedListener = stackEntryClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mStackEntryClickedListener.onStackEntryClick(getAdapterPosition());
        }

        interface OnStackEntryClickedListener {
            void onStackEntryClick(int adapterPosition);
        }
    }
}
