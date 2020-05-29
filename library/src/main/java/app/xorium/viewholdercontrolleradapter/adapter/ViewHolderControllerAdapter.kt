package app.xorium.viewholdercontrolleradapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.xorium.viewholdercontrolleradapter.ViewHolderController


class ViewHolderControllerAdapter<in T : Any>(private val factory: ViewHolderController.Factory<T>,
                                              private val content: List<T>,
                                              private val onClick: ((T) -> Unit)? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val controllerCache: MutableMap<Int, ViewHolderController<T, RecyclerView.ViewHolder>> = HashMap()

    private fun getItem(position: Int) = content[position]

    private fun getController(viewType: Int): ViewHolderController<T, RecyclerView.ViewHolder> =
            controllerCache.getOrElse(viewType, { factory.createController(viewType).also { controllerCache[viewType] = it } })

    override fun getItemViewType(position: Int): Int = factory.getItemViewType(getItem(position))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val controller = getController(getItemViewType(position))

        holder.itemView.setOnClickListener { onClick?.invoke(content[holder.adapterPosition]) }

        controller.bind(item, holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val controller = getController(viewType)
        return controller.createViewHolder(parent)
    }

    override fun getItemCount(): Int = content.size
}