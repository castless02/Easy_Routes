package com.novaengine.easyroutes.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novaengine.easyroutes.databinding.BookingsEntryBinding

class BookingsAdapter (private val list: List<BookingsEntryModel>) : RecyclerView.Adapter<BookingsAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    class ViewHolder(binding: BookingsEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        val bookId = binding.bookId
        val destinationName = binding.destinationName
        val location = binding.locationText
        val startDate = binding.startDateText
        val endDate = binding.endDateText
    }

    interface OnClickListener {
        fun onClick(position: Int, model: BookingsEntryModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = BookingsEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val BooksEntryModel = list[position]
        holder.bookId.text = BooksEntryModel.bookId.toString()
        holder.destinationName.text = BooksEntryModel.destinationName
        holder.location.text = BooksEntryModel.location
        holder.startDate.text = BooksEntryModel.startDate
        holder.endDate.text = BooksEntryModel.endDate

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, BooksEntryModel)
        }

    }

}