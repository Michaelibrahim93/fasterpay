package com.test.basemodule.base.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.test.basemodule.utils.ReflectionUtils
import com.test.basemodule.utils.Utils.equals
import java.util.*

/**
 * Created by Michael on 12/25/17.
 */
abstract class AbstractRecyclerAdapter<E, K : BaseViewHolder<E>>
    : ListAdapter<E, K> {
    protected var mData: List<E> = ArrayList()
    protected var clickListener: OnItemClickListener<E>? =
        null
    protected var isVertical = true
    protected abstract val viewRes: Int

    constructor(
        clickListener: OnItemClickListener<E>? = null
        , diffCallback: DiffUtil.ItemCallback<E>? = null)
            : super(createConfiguration(diffCallback)) {
        this.clickListener = clickListener
    }

    abstract fun onCreateDefaultViewHolder(viewGroup: ViewGroup?, viewType: Int, itemView: View): K
    abstract fun onViewHolderBond(k: K, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        val itemView = LayoutInflater.from(parent.context).inflate(viewRes, parent, false)
        val viewHolder = onCreateDefaultViewHolder(parent, viewType, itemView)
        viewHolder.setOnItemClickListener(clickListener)
        return viewHolder
    }

    override fun onBindViewHolder(k: K, position: Int) {
        k.bind(mData[position])
        onViewHolderBond(k, position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setData(mData: List<E>) {
        this.mData = mData
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        private fun <E> createConfiguration(diffUtil: DiffUtil.ItemCallback<E>?): AsyncDifferConfig<E> {
            val actualDiffUtil: DiffUtil.ItemCallback<E> = diffUtil?: defaultDifUtils()
            return AsyncDifferConfig.Builder(actualDiffUtil).build()
        }

        @JvmStatic
        private fun <E> defaultDifUtils(): DiffUtil.ItemCallback<E> {
            return object : DiffUtil.ItemCallback<E>() {
                override fun areItemsTheSame(oldItem: E, newItem: E): Boolean {
                    return equals(oldItem, newItem, true)
                }

                override fun areContentsTheSame(oldItem: E, newItem: E): Boolean {
                    return ReflectionUtils.isIdentical(oldItem, newItem)
                }
            }
        }
    }
}