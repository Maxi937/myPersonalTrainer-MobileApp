package org.fitness.myfitnesstrainer.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


open class GenericAdapter<T>(data: List<T>) : RecyclerView.Adapter<BaseViewHolder<T>>() {
    var listOfItems = data

    open var expressionViewHolderBinding: ((T, ViewBinding) -> Unit)? = null
    open var expressionOnCreateViewHolder:((ViewGroup)-> ViewBinding)? = null

    open fun deleteItemFromData(d: T) {
        listOfItems.forEachIndexed { index, element ->
            if(element == d) {
                hideItem(index)
            }
        }
    }
    open fun hideItem(position: Int) {
        val newItemList = ArrayList<T>()
        for(item in listOfItems) {
            newItemList.add(item)
        }
        newItemList.removeAt(position)
        listOfItems = newItemList
        notifyItemRemoved(position)
    }

    open fun getItem(position: Int): T? {
        return listOfItems?.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return expressionOnCreateViewHolder?.let { it(parent) }?.let { BaseViewHolder(it, expressionViewHolderBinding!!) }!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(listOfItems!![position])
    }

    override fun getItemCount(): Int {
        return listOfItems!!.size
    }
}

class BaseViewHolder<T> internal constructor(private val binding: ViewBinding, private val experssion:(T, ViewBinding)->Unit) : RecyclerView.ViewHolder(binding.root){
    fun bind(item:T){ experssion(item,binding) }

    fun getPos(): Int {
        return adapterPosition
    }
}

