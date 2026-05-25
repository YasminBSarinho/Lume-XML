package com.example.lume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lume.databinding.FragmentSorteadorBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class SorteadorFragment : Fragment() {

    private lateinit var binding: FragmentSorteadorBinding
    private val viewModel: LivroViewModel by activityViewModels()
    private var generoSelecionado = "Todos"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSorteadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtGeneroSelecionado.text = generoSelecionado

        binding.layoutGenero.setOnClickListener {
            configurarDropdown()
        }

        binding.btnSortear.setOnClickListener {
            realizarSorteio()
        }
    }

    private fun configurarDropdown() {
        val listaLivros = viewModel.listaDeLivros.value ?: emptyList<Livro>()
        val generosExistentes = listaLivros.map { it.genero }.distinct().sorted()
        val generosParaExibir = (generosExistentes + "Todos").toTypedArray()

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Escolha um Gênero")
        builder.setItems(generosParaExibir) { _, which ->
            generoSelecionado = generosParaExibir[which]
            binding.txtGeneroSelecionado.text = generoSelecionado
        }
        builder.show()
    }

    private fun realizarSorteio() {
        val listaLivros = viewModel.listaDeLivros.value ?: emptyList<Livro>()
        val livrosFiltrados = if (generoSelecionado == "Todos") {
            listaLivros
        } else {
            listaLivros.filter { it.genero == generoSelecionado }
        }

        if (livrosFiltrados.isNotEmpty()) {
            val livroSorteado = livrosFiltrados.random()
            exibirModalResultado(livroSorteado)
        } else {
            Toast.makeText(context, "Nenhum livro disponível para sortear!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun exibirModalResultado(livro: Livro) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.modal_resultado_sorteio, null)

        val txtNome = view.findViewById<TextView>(R.id.txtNomeSorteado)
        val txtGenero = view.findViewById<TextView>(R.id.txtGeneroSorteado)
        val btnSortearNovamente = view.findViewById<Button>(R.id.btnSortearNovamente)
        val btnExcluir = view.findViewById<Button>(R.id.btnExcluirLivro)

        txtNome.text = livro.titulo
        txtGenero.text = livro.genero

        btnSortearNovamente.setOnClickListener {
            dialog.dismiss()
            realizarSorteio()
        }

        btnExcluir.setOnClickListener {
            viewModel.removerLivro(livro)
            Toast.makeText(context, "${livro.titulo} removido da lista", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }
}
