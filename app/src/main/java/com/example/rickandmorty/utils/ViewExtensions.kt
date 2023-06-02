package com.example.rickandmorty.utils

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ChipGroup.getTextChipChecked() : String{
    val selectedId : Int = this.checkedChipId
    return if(selectedId> -1) findViewById<Chip>(selectedId).text.toString() else ""
}

