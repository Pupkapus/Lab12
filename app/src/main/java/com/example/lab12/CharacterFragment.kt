package com.example.lab12

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class CharacterFragment: Fragment() {

    private lateinit var nameText: TextView
    private lateinit var speciesText: TextView
    private lateinit var genderText: TextView
    private lateinit var statusText: TextView
    private lateinit var originText: TextView
    private lateinit var locationText: TextView
    private lateinit var imageCharacter: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        val characterName = arguments?.getString("Character_name")
        nameText = view.findViewById(R.id.textView_name) as TextView
        speciesText = view.findViewById(R.id.textView_species) as TextView
        genderText = view.findViewById(R.id.textView_gender) as TextView
        statusText = view.findViewById(R.id.textView_status) as TextView
        originText = view.findViewById(R.id.textView_origin) as TextView
        locationText = view.findViewById(R.id.textView_location) as TextView
        imageCharacter = view.findViewById(R.id.image_character) as ImageView

        val character = DataCharacters?.find { it.name.equals(characterName, ignoreCase = true) }

        nameText.text = character?.name
        speciesText.text = character?.species
        genderText.text = character?.gender
        statusText.text = character?.status
        originText.text = character?.origin
        locationText.text = character?.location
        Glide.with(this).load(character?.image).into(imageCharacter)
        return view
    }
}