package br.com.android.minipokedex

import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import br.com.android.minipokedex.data.StatEntry
import br.com.android.minipokedex.data.TypeEntry
import br.com.android.minipokedex.utils.ColorUtils
import br.com.android.minipokedex.viewmodel.PokemonDetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import java.util.Locale

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var pokemonNameTextView: TextView
    private lateinit var pokemonNumberTextView: TextView
    private lateinit var pokemonWeightTextView: TextView
    private lateinit var pokemonHeightTextView: TextView
    private lateinit var pokemonImageView: ImageView
    private lateinit var typesContainer: LinearLayout
    private lateinit var statsContainer: LinearLayout
    private lateinit var toolbar: Toolbar
    private lateinit var mainLayout: CoordinatorLayout

    companion object {
        const val EXTRA_POKEMON_ID = "extra_pokemon_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pokemonId = intent.getStringExtra(EXTRA_POKEMON_ID)
        if (pokemonId == null) {
            finish()
            return
        }

        viewModel = ViewModelProvider(this)[PokemonDetailViewModel::class.java]

        mainLayout = findViewById(R.id.main)
        pokemonNameTextView = findViewById(R.id.pokemonNameDetail)
        pokemonNumberTextView = findViewById(R.id.pokemonNumberDetail)
        pokemonWeightTextView = findViewById(R.id.pokemonWeight)
        pokemonHeightTextView = findViewById(R.id.pokemonHeight)
        pokemonImageView = findViewById(R.id.pokemonImageDetail)
        typesContainer = findViewById(R.id.typesContainer)
        statsContainer = findViewById(R.id.statsContainer)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        observeViewModel()
        viewModel.fetchPokemonDetails(pokemonId)
    }

    private fun observeViewModel() {
        viewModel.pokemonDetails.observe(this) { details ->
            if (details == null) {
                pokemonNameTextView.text = "Erro ao carregar"
                return@observe
            }

            val typeName = details.types.firstOrNull()?.type?.name ?: "normal"
            val pastelColor = ColorUtils.getPastelColorForType(typeName)
            mainLayout.setBackgroundColor(pastelColor)

            pokemonNameTextView.text = details.name.replaceFirstChar { it.titlecase() }
            pokemonNumberTextView.text = "#${details.id}"
            pokemonWeightTextView.text = "${details.weight / 10.0} kg"
            pokemonHeightTextView.text = "${details.height / 10.0} m"

            Glide.with(this)
                .load(details.sprites.other.official_artwork.front_default)
                .placeholder(R.mipmap.ic_launcher)
                .into(pokemonImageView)

            addTypes(details.types)
            addStats(details.stats)
        }
    }

    private fun addTypes(types: List<TypeEntry>) {
        typesContainer.removeAllViews()
        types.forEach { typeEntry ->
            val chip = Chip(this)
            chip.text = typeEntry.type.name.replaceFirstChar { it.titlecase() }

            val margin = (4 * resources.displayMetrics.density).toInt()
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(margin, 0, margin, 0)
            chip.layoutParams = params

            typesContainer.addView(chip)
        }
    }

    private fun addStats(stats: List<StatEntry>) {
        while (statsContainer.childCount > 1) {
            statsContainer.removeViewAt(1)
        }

        stats.forEach { statEntry ->
            val statLayout = LinearLayout(this)
            statLayout.orientation = LinearLayout.HORIZONTAL
            statLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            statLayout.setPadding(0, 8, 0, 8)

            val statName = TextView(this)
            statName.text = statEntry.stat.name.replaceFirstChar { it.titlecase() }
            statName.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)

            val statValue = TextView(this)
            statValue.text = statEntry.base_stat.toString()
            statValue.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            statValue.setPadding(16, 0, 16, 0)

            val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
            progressBar.max = 200
            progressBar.progress = statEntry.base_stat
            progressBar.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f)

            statLayout.addView(statName)
            statLayout.addView(statValue)
            statLayout.addView(progressBar)

            statsContainer.addView(statLayout)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}