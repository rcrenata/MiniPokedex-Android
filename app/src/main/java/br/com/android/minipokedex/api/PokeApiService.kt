package br.com.android.minipokedex.api

import br.com.android.minipokedex.data.PokemonDetailResponse
import br.com.android.minipokedex.data.PokemonListResponse
import br.com.android.minipokedex.data.PokemonTypeDetailResponse
import br.com.android.minipokedex.data.PokemonGenerationDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{idOuNome}")
    suspend fun getPokemonDetail(
        @Path("idOuNome") idOuNome: String
    ): PokemonDetailResponse

    @GET("type/{name}")
    suspend fun getPokemonListByType(
        @Path("name") typeName: String
    ): PokemonTypeDetailResponse

    @GET("generation/{id}")
    suspend fun getPokemonListByGeneration(
        @Path("id") generationId: String
    ): PokemonGenerationDetailResponse
}