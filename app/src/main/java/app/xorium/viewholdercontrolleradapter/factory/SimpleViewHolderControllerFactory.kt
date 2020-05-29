package app.xorium.viewholdercontrolleradapter.factory

import androidx.recyclerview.widget.RecyclerView
import app.xorium.viewholdercontrolleradapter.ViewHolderController


class SimpleViewHolderControllerFactory(private val controllers: Map<Class<out Any>, ViewHolderController<out Any, out RecyclerView.ViewHolder>>) :
    ViewHolderController.Factory<Any> {

    private val controllerClassList: List<Class<out Any>> = controllers.keys.toList()

    @Suppress("UNCHECKED_CAST")
    override fun createController(itemViewType: Int): ViewHolderController<Any, RecyclerView.ViewHolder> {
        val clazz = controllerClassList[itemViewType]
        return controllers[clazz] as ViewHolderController<Any, RecyclerView.ViewHolder>
    }

    override fun getItemViewType(data: Any): Int {
        return controllerClassList.indexOf(data.javaClass)
    }
}