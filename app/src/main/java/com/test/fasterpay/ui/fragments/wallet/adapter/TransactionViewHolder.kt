package com.test.fasterpay.ui.fragments.wallet.adapter

import com.test.basemodule.base.view.adapter.BaseViewHolder
import com.test.fasterpay.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat

class TransactionViewHolder(
    binding: ItemTransactionBinding,
    fromFormat: SimpleDateFormat,
    toFormat: SimpleDateFormat
) : BaseViewHolder<UiTransaction, ItemTransactionBinding>(binding) {
    init {
        binding.fromFormatter = fromFormat
        binding.toFormatter = toFormat
    }

    override fun onBindView(itemData: UiTransaction) {
        binding.itemData = itemData
    }

}