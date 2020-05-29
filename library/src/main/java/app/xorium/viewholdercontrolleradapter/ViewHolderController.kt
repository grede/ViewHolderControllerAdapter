package app.xorium.viewholdercontrolleradapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


interface ViewHolderController<T, VH : RecyclerView.ViewHolder> {
    fun createViewHolder(parent: ViewGroup): VH
    fun bind(data: T, viewHolder: VH)

    interface Factory<T> {
        fun createController(itemViewType: Int): ViewHolderController<T, RecyclerView.ViewHolder>
        fun getItemViewType(data: T): Int
    }
}