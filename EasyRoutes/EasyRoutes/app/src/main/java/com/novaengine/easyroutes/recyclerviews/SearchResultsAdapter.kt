package com.novaengine.easyroutes.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novaengine.easyroutes.databinding.SearchresultsEntryBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class SearchResultsAdapter (private val list: List<SearchResultsEntryModel>) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    class ViewHolder(binding: SearchresultsEntryBinding) : RecyclerView.ViewHolder(binding.root) {

        val destinationImage = binding.destinationImage
        val destinationName = binding.destinationName
        val destinationRating = binding.rating
        val destinationStars = binding.ratingBar
        val location = binding.locationText

    }

    interface OnClickListener {
        fun onClick(position: Int, model: SearchResultsEntryModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchresultsEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val SearchResultsEntryModel = list[position]

        holder.destinationImage.setImageDrawable(SearchResultsEntryModel.destinationImage)
        holder.destinationName.text = SearchResultsEntryModel.destinationName
        holder.location.text = SearchResultsEntryModel.location
        holder.destinationStars.rating = SearchResultsEntryModel.destinationStars.toFloat()

        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.roundingMode = RoundingMode.HALF_UP
        var rating = decimalFormat.format(SearchResultsEntryModel.destinationRating.toDouble())

        holder.destinationRating.text = "Valutazione: ${rating.toString()}"
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, SearchResultsEntryModel)
        }

    }

}