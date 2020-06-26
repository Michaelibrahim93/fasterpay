package com.test.basemodule.base.view.adapter;

import android.view.View;

/**
 * Created by Michael on 12/25/17.
 */

public interface OnItemClickListener<K> {
    void onItemClick(View view, K item);
}