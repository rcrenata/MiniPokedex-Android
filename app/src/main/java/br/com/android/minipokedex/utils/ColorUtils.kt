package br.com.android.minipokedex.utils

import android.graphics.Color

object ColorUtils {

    fun getPastelColorForType(typeName: String): Int {
        val hexColor = when (typeName.lowercase()) {
            "grass" -> "#A7D7A7"
            "fire" -> "#F7A57C"
            "water" -> "#A7C8E8"
            "bug" -> "#C8D7A7"
            "normal" -> "#E0E0E0"
            "poison" -> "#C7A7D7"
            "electric" -> "#F7F7A7"
            "ground" -> "#E8D7A7"
            "fairy" -> "#F7C8E8"
            "fighting" -> "#E8A7A7"
            "psychic" -> "#F7A7C8"
            "rock" -> "#D7C8A7"
            "ghost" -> "#B7A7D7"
            "ice" -> "#C8F7F7"
            "dragon" -> "#A7A7F7"
            else -> "#E0E0E0"
        }
        return Color.parseColor(hexColor)
    }
}