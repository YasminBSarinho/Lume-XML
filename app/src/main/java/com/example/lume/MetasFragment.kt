package com.example.lume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lume.databinding.FragmentMetasBinding

class MetasFragment : Fragment() {

    private lateinit var binding: FragmentMetasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMetasBinding.inflate(inflater, container, false)
        return binding.root
    }
}
