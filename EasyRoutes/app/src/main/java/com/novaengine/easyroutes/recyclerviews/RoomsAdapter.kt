package com.novaengine.easyroutes.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novaengine.easyroutes.databinding.RoomsEntryBinding

class RoomsAdapter(private val list: List<RoomsEntryModel>) : RecyclerView.Adapter<RoomsAdapter.ViewHolder>()
{
    private var onClickListener: RoomsAdapter.OnClickListener? = null
    class ViewHolder(binding: RoomsEntryBinding) : RecyclerView.ViewHolder(binding.root) {

        val cameraImage = binding.cameraImage
        val Name = binding.cameraName
        val Persone = binding.personenumero
       // val location = binding.locationText
    }
    interface OnClickListener {
        fun onClick(position: Int, model: RoomsEntryModel)
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsAdapter.ViewHolder {
        val view = RoomsEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: RoomsAdapter.ViewHolder, position: Int) {
        val RoomsEntryModel = list[position]

        holder.cameraImage.setImageDrawable(RoomsEntryModel.cameraImage)
        holder.Name.text = RoomsEntryModel.NomeCamera
        holder.Persone.text = "Capienza: " + RoomsEntryModel.NumeroPersone

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, RoomsEntryModel)
        }

    }
}