package br.com.android.minipokedex

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.android.minipokedex.adapter.PokemonAdapter
import br.com.android.minipokedex.data.PokemonResult
import com.google.android.material.chip.Chip

import br.com.android.minipokedex.viewmodel.PokemonListViewModel
import br.com.android.minipokedex.PokemonDetailActivity


class PokemonListActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonListViewModel
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView

    private lateinit var chipType: Chip
    private lateinit var chipGeneration: Chip
    private lateinit var btnClearFilters: Button

    private var fullPokemonList = listOf<PokemonResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        searchView = findViewById(R.id.searchView)
        chipType = findViewById(R.id.chipType)
        chipGeneration = findViewById(R.id.chipGeneration)
        btnClearFilters = findViewById(R.id.btnClearFilters)

        setupRecyclerView()
        observeViewModel()
        setupSearchView()
        setupFilterClicks()
    }

    private fun setupRecyclerView() {
        pokemonAdapter = PokemonAdapter { pokemon ->
            val idOuNome = pokemon.name
            val intent = Intent(this, PokemonDetailActivity::class.java)
            intent.putExtra(PokemonDetailActivity.EXTRA_POKEMON_ID, idOuNome)
            startActivity(intent)
        }
        recyclerView.adapter = pokemonAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.pokemonList.observe(this) { list ->
            fullPokemonList = list
            pokemonAdapter.submitList(list)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: ""
                val filteredList = fullPokemonList.filter {
                    it.name.lowercase().contains(query.lowercase())
                }
                pokemonAdapter.submitList(filteredList)
                return true
            }
        })
    }

    private fun setupFilterClicks() {

        chipType.setOnClickListener {
            showTypeFilterDialog()
        }

        chipGeneration.setOnClickListener {
            showGenerationFilterDialog()
        }

        btnClearFilters.setOnClickListener {
            viewModel.resetToOriginalList()
            searchView.setQuery("", false)
            chipType.text = "Tipo"
            chipGeneration.text = "Geração"
            btnClearFilters.visibility = View.GONE
        }
    }

    private fun showTypeFilterDialog() {
        val types = arrayOf(
            "Normal", "Fire", "Water", "Grass", "Electric", "Ice",
            "Fighting", "Poison", "Ground", "Flying", "Psychic",
            "Bug", "Rock", "Ghost", "Dragon", "Steel", "Fairy"
        )

        AlertDialog.Builder(this)
            .setTitle("Selecionar Tipo")
            .setItems(types) { dialog, which ->
                val selectedType = types[which]

                viewModel.fetchPokemonByType(selectedType)

                chipType.text = "Tipo: $selectedType"
                chipGeneration.text = "Geração"
                btnClearFilters.visibility = View.VISIBLE
                searchView.setQuery("", false)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showGenerationFilterDialog() {
        val generations = arrayOf(
            "Geração I", "Geração II", "Geração III", "Geração IV",
            "Geração V", "Geração VI", "Geração VII", "Geração VIII", "Geração IX"
        )

        AlertDialog.Builder(this)
            .setTitle("Selecionar Geração")
            .setItems(generations) { dialog, which ->
                val selectedGenId = (which + 1).toString()
                val selectedGenName = generations[which]

                viewModel.fetchPokemonByGeneration(selectedGenId)

                chipGeneration.text = selectedGenName
                chipType.text = "Tipo"
                btnClearFilters.visibility = View.VISIBLE
                searchView.setQuery("", false)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}