package com.example.lume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lume.databinding.FragmentBibliotecaBinding
import com.example.lume.databinding.ItemLivroBinding

class BibliotecaFragment : Fragment() {

    private lateinit var binding: FragmentBibliotecaBinding
    private val viewModel: LivroViewModel by activityViewModels()
    private val adapter = LivroAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBibliotecaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLivros.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLivros.adapter = adapter

        viewModel.listaDeLivros.observe(viewLifecycleOwner) { livros ->
            adapter.atualizar(livros)
            binding.txtSubtituloBiblioteca.text = "${livros.size} livros na sua coleção"
        }
    }

    inner class LivroAdapter : RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {
        private var listaLivros = listOf<Livro>()

        fun atualizar(novaLista: List<Livro>) {
            listaLivros = novaLista
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
            val item = ItemLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LivroViewHolder(item)
        }

        override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
            val livro = listaLivros[position]
            holder.itemBinding.txtNomeLivroItem.text = livro.titulo
            holder.itemBinding.txtGeneroLivroItem.text = livro.genero
        }

        override fun getItemCount() = listaLivros.size

        inner class LivroViewHolder(val itemBinding: ItemLivroBinding) : RecyclerView.ViewHolder(itemBinding.root)
    }
}
