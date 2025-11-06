package br.com.android.minipokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import br.com.android.minipokedex.R
import br.com.android.minipokedex.data.PokemonResult

class PokemonAdapter(
    private val onItemClicked: (PokemonResult) -> Unit
) : ListAdapter<PokemonResult, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.pokemonName)
        val numberText: TextView = view.findViewById(R.id.pokemonNumber)
        val image: ImageView = view.findViewById(R.id.pokemonImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)

        val id = pokemon.url.split("/").filter { it.isNotEmpty() }.last()
        holder.nameText.text = pokemon.name.replaceFirstChar { it.titlecase() }
        holder.numberText.text = "#$id"

        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClicked(pokemon)
        }
    }
}

private class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonResult>() {
    override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
        return oldItem == newItem
    }
}