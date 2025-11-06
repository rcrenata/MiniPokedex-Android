package br.com.android.minipokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.android.minipokedex.api.RetrofitInstance
import br.com.android.minipokedex.data.PokemonDetailResponse
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    private val _pokemonDetails = MutableLiveData<PokemonDetailResponse?>()
    val pokemonDetails: LiveData<PokemonDetailResponse?> = _pokemonDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchPokemonDetails(pokemonId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPokemonDetail(pokemonId)
                _pokemonDetails.value = response
            } catch (e: Exception) {
                _pokemonDetails.value = null
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}