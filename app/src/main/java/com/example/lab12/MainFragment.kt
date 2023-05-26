package com.example.lab12


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button

class MainFragment : Fragment() {

    private lateinit var searchEditText: AutoCompleteTextView
    private lateinit var searchButton: Button

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        searchEditText = view.findViewById(R.id.search_character) as AutoCompleteTextView
        searchButton = view.findViewById(R.id.button_search) as Button
        var NamesList: Array<String>? = null
        val dataCharacters = DataCharacters
        if (dataCharacters != null) {
            NamesList = dataCharacters.map { it.name }.toTypedArray()
        }

        var adapter: ArrayAdapter<String>? = null
        if (NamesList != null) {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                NamesList)
            searchEditText.setAdapter(adapter)
        }

        searchButton.setOnClickListener {
            val inputData = searchEditText.text.toString()

            val characterFragment = CharacterFragment()
            val bundle = Bundle()
            bundle.putString("Character_name", inputData)
            characterFragment.arguments = bundle

            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.container, characterFragment)
                .addToBackStack(null)
                .commit()
        }
        return view
    }

}