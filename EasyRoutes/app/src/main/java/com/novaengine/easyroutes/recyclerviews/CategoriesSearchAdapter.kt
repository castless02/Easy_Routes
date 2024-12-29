package com.novaengine.easyroutes.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novaengine.easyroutes.databinding.CategoriesSearchEntryBinding

class CategoriesSearchAdapter (private val list: List<CategoriesSearchEntryModel>) : RecyclerView.Adapter<CategoriesSearchAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    class ViewHolder(binding: CategoriesSearchEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        val categoryButton = binding.categoryButton
    }

    interface OnClickListener {
        fun onClick(position: Int, model: CategoriesSearchEntryModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CategoriesSearchEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CategoriesSearchEntryModel = list[position]
        holder.categoryButton.text = CategoriesSearchEntryModel.categoryName

        holder.categoryButton.setOnClickListener {
            onClickListener?.onClick(position, CategoriesSearchEntryModel)
        }
    }

}