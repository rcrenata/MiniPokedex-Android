package br.com.android.minipokedex.data

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val stats: List<StatEntry>,
    val types: List<TypeEntry>
)

data class Sprites(
    val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val official_artwork: OfficialArtwork
)

data class OfficialArtwork(
    val front_default: String?
)

data class StatEntry(
    val base_stat: Int,
    val stat: PokemonResult
)

data class TypeEntry(
    val type: PokemonResult
)