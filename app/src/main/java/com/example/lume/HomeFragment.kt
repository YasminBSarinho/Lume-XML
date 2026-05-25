package com.example.lume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lume.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: LivroViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCadastrar.setOnClickListener {
            val titulo = binding.editTitulo.text.toString()
            val autor = binding.editAutor.text.toString()
            val ano = binding.editAno.text.toString()
            val genero = binding.editGenero.text.toString()

            if (titulo.isNotEmpty() && autor.isNotEmpty() && ano.isNotEmpty() && genero.isNotEmpty()) {
                val novoLivro = Livro(titulo, autor, ano, genero, "Lido")
                viewModel.adicionarLivro(novoLivro)
                
                Toast.makeText(context, "$titulo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()

                binding.editTitulo.text.clear()
                binding.editAutor.text.clear()
                binding.editAno.text.clear()
                binding.editGenero.text.clear()
            } else {
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
