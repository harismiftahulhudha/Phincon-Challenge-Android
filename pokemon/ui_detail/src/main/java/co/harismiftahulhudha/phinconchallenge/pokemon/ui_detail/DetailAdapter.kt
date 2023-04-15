package co.harismiftahulhudha.phinconchallenge.pokemon.ui_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.DetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_detail.databinding.ItemDetailBinding

class DetailAdapter: ListAdapter<DetailModel, DetailAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemDetailBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailModel) {
            binding.apply {
                txtLabel.text = data.label
                txtValue.text = data.value
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DetailModel>() {
        override fun areItemsTheSame(
            oldItem: DetailModel,
            newItem: DetailModel
        ) = oldItem.label == newItem.label

        override fun areContentsTheSame(
            oldItem: DetailModel,
            newItem: DetailModel
        ) = oldItem == newItem
    }
}