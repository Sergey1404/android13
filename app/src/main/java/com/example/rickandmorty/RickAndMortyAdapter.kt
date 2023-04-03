package com.example.rickandmorty

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RickAdapter(
    private val onButtonClick: () -> Unit,
    private val onCharacterClick: (episodes: List<String>) -> Unit
):ListAdapter<RickAndMortySealed, RecyclerView.ViewHolder>(RickAndMortyDiffCallback()) {
    companion object {
        const val ITEM_TYPE_CHARACTER = 0
        const val ITEM_TYPE_BUTTON = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_TYPE_CHARACTER -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.item_rick_and_morty_holder,
                        parent,
                        false
                    )
                RickAndMortyViewHolder(view)
            }
            ITEM_TYPE_BUTTON -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.button,
                        parent,
                        false
                    )
                ButtonViewHolder(view, onButtonClick)
            }
            else -> throw java.lang.IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_CHARACTER -> {
                (holder as RickAndMortyViewHolder).bind(getItem(position) as RickAndMortySealed.Character)
                holder.itemView.setOnClickListener {
                    onCharacterClick((getItem(position) as RickAndMortySealed.Character).character.episode)
                }
            }
            ITEM_TYPE_BUTTON -> (holder as ButtonViewHolder).bind(getItem(position) as RickAndMortySealed.Button)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RickAndMortySealed.Character -> ITEM_TYPE_CHARACTER
            is RickAndMortySealed.Button -> ITEM_TYPE_BUTTON
        }
    }
    class RickAndMortyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(rickAndMorty: RickAndMortySealed.Character) {
            view.findViewById<TextView>(R.id.tvName).text = rickAndMorty.character.name
            view.findViewById<TextView>(R.id.tvGender).text = rickAndMorty.character.gender
            Glide
                .with(view.rootView)
                .load(rickAndMorty.character.image)
                .into(view.findViewById(R.id.ivIcon))
        }
    }

    class ButtonViewHolder(private val view: View, private val onButtonClick: () -> Unit): RecyclerView.ViewHolder(view){
        fun bind(button: RickAndMortySealed.Button){
            view.findViewById<Button>(R.id.ivButton).setOnClickListener {
                onButtonClick()
            }
        }
    }
}
class RickAndMortyDiffCallback : DiffUtil.ItemCallback<RickAndMortySealed>() {
    override fun areItemsTheSame(oldItem: RickAndMortySealed, newItem: RickAndMortySealed): Boolean {
        return when {
            oldItem is RickAndMortySealed.Character && newItem is RickAndMortySealed.Character ->
                oldItem.character.id == newItem.character.id
            oldItem is RickAndMortySealed.Button && newItem is RickAndMortySealed.Button ->
                true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: RickAndMortySealed, newItem: RickAndMortySealed): Boolean {
        return oldItem == newItem
    }
}