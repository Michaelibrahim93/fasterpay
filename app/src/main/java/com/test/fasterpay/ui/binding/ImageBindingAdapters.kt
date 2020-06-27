package com.test.fasterpay.ui.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import java.io.File

/**
 * created by george
 * Binding adapters that work with a fragment instance.
 */
object ImageBindingAdapters {
    @BindingAdapter(
        value = ["android:src", "placeholderImage", "errorImage", "imageRequestListener", "isCircular"],
        requireAll = false
    )
    fun loadImageWithGlide(
        imageView: ImageView,
        obj: Any?,
        placeholder: Any?,
        errorImage: Any?,
        listener: RequestListener<Drawable?>?,
        isCircular: Boolean = false
    ) {
        val options = RequestOptions()
        if (placeholder is Drawable) options.placeholder(placeholder)
        if (placeholder is Int) options.placeholder(placeholder)

        if (errorImage is Drawable) options.error(errorImage)
        if (errorImage is Int) options.error(errorImage)

        val manager = Glide.with(imageView).applyDefaultRequestOptions(options)
        val builder: RequestBuilder<Drawable>


        builder = when (obj) {
            is String -> manager.load(obj)
            is Uri -> manager.load(obj)
            is Drawable -> manager.load(obj)
            is Bitmap -> manager.load(obj)
            is Int -> manager.load(obj)
            is File -> manager.load(obj)
            is Array<*> -> manager.load(obj)
            else -> manager.load(obj)
        }

        if (isCircular ) {
            builder.listener(listener).apply(RequestOptions().circleCrop()).into(imageView)
        }else {
            builder.listener(listener).into(imageView)
        }
    }
}

