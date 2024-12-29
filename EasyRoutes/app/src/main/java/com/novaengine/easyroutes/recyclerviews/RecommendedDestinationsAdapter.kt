package com.novaengine.easyroutes.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novaengine.easyroutes.databinding.RecommendedDestinationsEntryBinding

class RecommendedDestinationsAdapter(private val list: List<RecommendedDestinationsEntryModel>) : RecyclerView.Adapter<RecommendedDestinationsAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    class ViewHolder(binding: RecommendedDestinationsEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        val destinationImage = binding.destinationImage
        val destinationName = binding.destinationName
    }

    interface OnClickListener {
        fun onClick(position: Int, model: RecommendedDestinationsEntryModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecommendedDestinationsEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val RecommendedDestinationEntryModel = list[position]
        holder.destinationImage.setImageDrawable(RecommendedDestinationEntryModel.destinationImage)
        holder.destinationName.text = RecommendedDestinationEntryModel.destinationName

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, RecommendedDestinationEntryModel)
        }

    }

}