package br.com.android.minipokedex.data

data class PokemonTypeDetailResponse(
    val pokemon: List<PokemonEntry>
)

data class PokemonEntry(
    val pokemon: PokemonResult
)