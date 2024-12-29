package com.novaengine.easyroutes.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novaengine.easyroutes.databinding.CategoriesSearchEntry2Binding

class CategoriesAdapter (private val list: List<CategoriesEntryModel>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    class ViewHolder(binding: CategoriesSearchEntry2Binding) : RecyclerView.ViewHolder(binding.root) {
        val categoryButton = binding.categorias
        val Immagine = binding.sfondocategorie
    }

    interface OnClickListener {
        fun onClick(position: Int, model: CategoriesEntryModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CategoriesSearchEntry2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CategoriesEntryModel = list[position]
        holder.categoryButton.text = CategoriesEntryModel.categoryName
        holder.Immagine.setImageDrawable(CategoriesEntryModel.immagine)
        holder.categoryButton.setOnClickListener {
            onClickListener?.onClick(position, CategoriesEntryModel)
        }
        holder.Immagine.setOnClickListener {
            onClickListener?.onClick(position, CategoriesEntryModel)
        }

    }
}