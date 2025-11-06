package br.com.android.minipokedex.data

import com.google.gson.annotations.SerializedName

data class PokemonGenerationDetailResponse(
    @SerializedName("pokemon_species")
    val pokemon_species: List<PokemonResult>
)