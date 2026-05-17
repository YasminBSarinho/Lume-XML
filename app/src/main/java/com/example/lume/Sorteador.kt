package com.example.lume

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lume.databinding.ActivitySorteadorBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class Sorteador : AppCompatActivity() {

    private lateinit var binding: ActivitySorteadorBinding
    private var generoSelecionado = "Todos"
    private val listaLivros = mutableListOf<Livro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySorteadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtGeneroSelecionado.text = generoSelecionado

        atualizarListaNaTela()
        configurarDropdown()
        configurarBotoes()
    }

    private fun atualizarListaNaTela() {
        binding.containerLivros.removeAllViews()
        binding.txtQuantidadeLivros.text = "${listaLivros.size} Livros"

        val inflater = LayoutInflater.from(this)
        listaLivros.forEach { livro ->
            val itemView = inflater.inflate(R.layout.item_livro, binding.containerLivros, false)
            
            val txtNome = itemView.findViewById<TextView>(R.id.txtNomeLivroItem)
            val txtGenero = itemView.findViewById<TextView>(R.id.txtGeneroLivroItem)

            txtNome.text = livro.nome
            txtGenero.text = livro.genero

            binding.containerLivros.addView(itemView)
        }
    }

    private fun configurarDropdown() {
        binding.layoutGenero.setOnClickListener {
            val generosExistentes = listaLivros.map { it.genero }.distinct().sorted()
            val generosParaExibir = (generosExistentes + "Todos").toTypedArray()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Escolha um Gênero")
            builder.setItems(generosParaExibir) { _, which ->
                generoSelecionado = generosParaExibir[which]
                binding.txtGeneroSelecionado.text = generoSelecionado
            }
            builder.show()
        }
    }

    private fun configurarBotoes() {
        binding.btnSortear.setOnClickListener {
            realizarSorteio()
        }

        binding.btnCadastrar.setOnClickListener {
            exibirModalCadastro()
        }
    }

    private fun realizarSorteio() {
        val livrosFiltrados = if (generoSelecionado == "Todos") {
            listaLivros
        } else {
            listaLivros.filter { it.genero == generoSelecionado }
        }

        if (livrosFiltrados.isNotEmpty()) {
            val livroSorteado = livrosFiltrados.random()
            exibirModalResultado(livroSorteado)
        } else {
            Toast.makeText(this, "Nenhum livro disponível para sortear!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun exibirModalResultado(livro: Livro) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.modal_resultado_sorteio, null)

        val txtNome = view.findViewById<TextView>(R.id.txtNomeSorteado)
        val txtGenero = view.findViewById<TextView>(R.id.txtGeneroSorteado)
        val btnSortearNovamente = view.findViewById<Button>(R.id.btnSortearNovamente)
        val btnExcluir = view.findViewById<Button>(R.id.btnExcluirLivro)

        txtNome.text = livro.nome
        txtGenero.text = livro.genero

        btnSortearNovamente.setOnClickListener {
            dialog.dismiss()
            realizarSorteio()
        }

        btnExcluir.setOnClickListener {
            listaLivros.remove(livro)
            atualizarListaNaTela()
            Toast.makeText(this, "${livro.nome} removido da lista", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }

    private fun exibirModalCadastro() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.modal_cadastrar_livro, null)

        val editTitulo = view.findViewById<EditText>(R.id.editTituloLivro)
        val editGenero = view.findViewById<EditText>(R.id.editGeneroLivro)
        val btnConfirmar = view.findViewById<Button>(R.id.btnConfirmarCadastro)

        btnConfirmar.setOnClickListener {
            val titulo = editTitulo.text.toString()
            val genero = editGenero.text.toString()

            if (titulo.isNotEmpty() && genero.isNotEmpty()) {
                listaLivros.add(Livro(titulo, genero))
                atualizarListaNaTela()
                Toast.makeText(this, "$titulo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setContentView(view)
        dialog.show()
    }
}