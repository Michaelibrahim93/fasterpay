package com.test.basemodule.base.view.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Michael on 12/25/17.
 */

public abstract class BaseViewHolder<E> extends RecyclerView.ViewHolder{
    protected OnItemClickListener<E> onItemClickListener;
    protected E itemData;

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(v, itemData);
        });
    }


    public void setOnItemClickListener(OnItemClickListener<E> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void bind(E itemData){
        this.itemData = itemData;
        onBindView(itemData);
    }

    protected abstract void onBindView(E itemData);

    protected Context getContext()
    {
        return itemView.getContext();
    }

    protected  E getItemData()
    {
        return itemData;
    }
}