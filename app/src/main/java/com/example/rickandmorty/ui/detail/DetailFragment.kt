package com.example.rickandmorty.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentDetailBinding
import com.example.rickandmorty.databinding.FragmentListBinding
import com.example.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val args : DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {

            charName.text = args.details.name
            val imgLink = args.details.image

            charImg.load(imgLink){
                crossfade(true)
                crossfade(1000)
            }
            speciesTextView.text = "Species : " +args.details.species
            genderTextView.text = "Gender : " + args.details.gender
            if(args.details.type == ""){
                typeTextView.text = "Type : " + "No Data"
            }
            else typeTextView.text = "Type : " + args.details.type
            if (args.details.status == ("Alive")) charStatus.setBackgroundResource(R.drawable.ic_alive)
            else charStatus.setBackgroundResource(R.drawable.ic_dead)
        }
    }
}