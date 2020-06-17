package app.xorium.viewholdercontrolleradapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.xorium.viewholdercontrolleradapter.ViewHolderController


class SingleViewHolderControllerAdapter<in T : Any>(
    private val controller: Lazy<ViewHolderController<T, RecyclerView.ViewHolder>>,
    private val content: List<T>,
    private val onClick: ((T) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(
        controller: ViewHolderController<T, RecyclerView.ViewHolder>,
        content: List<T>,
        onClick: ((T) -> Unit)? = null
    ) : this(
        controller = lazy(LazyThreadSafetyMode.NONE) { controller },
        content = content,
        onClick = onClick
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return controller.value.createViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        controller.value.bind(content[position], holder)
    }
}