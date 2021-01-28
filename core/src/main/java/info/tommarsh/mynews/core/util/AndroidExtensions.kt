package info.tommarsh.mynews.core.util

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.CONSUMED
import androidx.core.view.WindowInsetsCompat.Type.navigationBars
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.recyclerview.widget.DiffUtil

//region listadapter
fun <T> getDiffUtilItemCallback(compare: (T, T) -> Boolean) =
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(old: T, new: T) = compare(old, new)

        override fun areContentsTheSame(old: T, new: T) = old?.equals(new) ?: false
    }

inline fun <reified T : Any> createDiffItemCallback(crossinline contentsTheSame: (old: T, new: T) -> Boolean) =
    object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem::class.java == newItem::class.java
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return contentsTheSame(oldItem, newItem)
        }
    }

//endregion

//region view
fun View.doOnInsets(block: (systemBarInsets: Insets, navigationBarInsets: Insets) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val systemBarInsets = insets.getInsets(systemBars())
        val navigationInsets = insets.getInsets(navigationBars())
        block(systemBarInsets, navigationInsets)
        CONSUMED
    }
}
//endregion