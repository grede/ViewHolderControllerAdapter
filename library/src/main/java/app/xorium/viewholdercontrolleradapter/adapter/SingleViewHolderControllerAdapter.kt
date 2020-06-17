package app.xorium.viewholdercontrolleradapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.xorium.viewholdercontrolleradapter.ViewHolderController


class SingleViewHolderControllerAdapter<T, VH : RecyclerView.ViewHolder>(
    private val controller: Lazy<ViewHolderController<T, VH>>,
    private val content: List<T>,
    private val onClick: ((T) -> Unit)? = null
) : RecyclerView.Adapter<VH>() {

    constructor(
        controller: ViewHolderController<T, VH>,
        content: List<T>,
        onClick: ((T) -> Unit)? = null
    ) : this(
        controller = lazy(LazyThreadSafetyMode.NONE) { controller },
        content = content,
        onClick = onClick
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return controller.value.createViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        controller.value.bind(content[position], holder)
    }
}