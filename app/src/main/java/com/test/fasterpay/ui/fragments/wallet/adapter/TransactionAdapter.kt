package com.test.fasterpay.ui.fragments.wallet.adapter

import android.view.ViewGroup
import com.test.basemodule.base.view.adapter.AbstractRecyclerAdapter
import com.test.fasterpay.R
import com.test.fasterpay.databinding.ItemTransactionBinding
import com.test.fasterpay.vo.Constants
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter()
    : AbstractRecyclerAdapter<UiTransaction, ItemTransactionBinding, TransactionViewHolder>(
    null,
    null
) {
    private val toFormat = SimpleDateFormat("MM.dd.yyyy", Locale.getDefault())
    private val fromFormat = Constants.databaseDateFormat()
    override val viewRes: Int
        get() = R.layout.item_transaction

    override fun onCreateDefaultViewHolder(
        viewGroup: ViewGroup?,
        viewType: Int,
        binding: ItemTransactionBinding
    ): TransactionViewHolder {
        return TransactionViewHolder(binding, fromFormat, toFormat)
    }

    override fun onViewHolderBond(k: TransactionViewHolder, position: Int) {
    }

}