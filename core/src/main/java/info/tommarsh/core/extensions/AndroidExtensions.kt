package info.tommarsh.core.extensions

import android.view.Menu
import androidx.recyclerview.widget.DiffUtil

//region listadapter
fun <T> getDiffUtilItemCallback(compare: (T, T) -> Boolean) =
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(old: T, new: T) = compare(old, new)

        override fun areContentsTheSame(old: T, new: T) = old?.equals(new) ?: false
    }
//endregion

//region menu
inline fun <reified T> Menu.getActionItem(item: Int): T = findItem(item).actionView as T
//endregion