package br.com.android.minipokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.android.minipokedex.data.PokemonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class PokemonListViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonResult>>()
    val pokemonList: LiveData<List<PokemonResult>> get() = _pokemonList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var originalList = listOf<PokemonResult>()

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                val response = URL("https://pokeapi.co/api/v2/pokemon?limit=151").readText()
                val json = JSONObject(response)
                val results = json.getJSONArray("results")
                List(results.length()) { i ->
                    val obj = results.getJSONObject(i)
                    PokemonResult(
                        name = obj.getString("name"),
                        url = obj.getString("url")
                    )
                }
            }
            originalList = result
            _pokemonList.value = result
            _isLoading.value = false
        }
    }

    fun fetchPokemonByType(type: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                val response = URL("https://pokeapi.co/api/v2/type/${type.lowercase()}").readText()
                val json = JSONObject(response)
                val pokemonArray = json.getJSONArray("pokemon")
                List(pokemonArray.length()) { i ->
                    val obj = pokemonArray.getJSONObject(i).getJSONObject("pokemon")
                    PokemonResult(
                        name = obj.getString("name"),
                        url = obj.getString("url")
                    )
                }
            }
            _pokemonList.value = result
            _isLoading.value = false
        }
    }

    fun fetchPokemonByGeneration(genId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                val response = URL("https://pokeapi.co/api/v2/generation/$genId").readText()
                val json = JSONObject(response)
                val pokemonArray = json.getJSONArray("pokemon_species")
                List(pokemonArray.length()) { i ->
                    val obj = pokemonArray.getJSONObject(i)
                    PokemonResult(
                        name = obj.getString("name"),
                        url = obj.getString("url")
                    )
                }
            }
            _pokemonList.value = result
            _isLoading.value = false
        }
    }

    fun resetToOriginalList() {
        _pokemonList.value = originalList
    }
}
