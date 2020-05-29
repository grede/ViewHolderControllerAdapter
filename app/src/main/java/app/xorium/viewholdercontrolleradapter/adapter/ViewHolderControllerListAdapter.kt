package app.xorium.viewholdercontrolleradapter.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.xorium.viewholdercontrolleradapter.ViewHolderController


class ViewHolderControllerListAdapter<T : Any>(private val factory: ViewHolderController.Factory<T>,
                                               private val onClick: ((T) -> Unit)? = null) : ListAdapter<T, RecyclerView.ViewHolder>(
    ListItemCallback()
) {
    private val controllerCache: MutableMap<Int, ViewHolderController<T, RecyclerView.ViewHolder>> = HashMap()

    private fun getController(viewType: Int): ViewHolderController<T, RecyclerView.ViewHolder> =
            controllerCache.getOrElse(viewType, { factory.createController(viewType).also { controllerCache[viewType] = it } })

    override fun getItemViewType(position: Int): Int = factory.getItemViewType(getItem(position))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val controller = getController(getItemViewType(position))

        holder.itemView.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            getItemOpt(adapterPosition)?.let { onClick?.invoke(it) }
        }

        controller.bind(item, holder)
    }

    private fun getItemOpt(position: Int): T? {
        if (position in 0 until itemCount) {
            return getItem(position)
        }

        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val controller = getController(viewType)
        return controller.createViewHolder(parent)
    }

    private class ListItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = (oldItem == newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = (oldItem == newItem)
    }
}