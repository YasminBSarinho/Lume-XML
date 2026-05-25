package com.example.lume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LivroViewModel : ViewModel() {

    val listaDeLivros = MutableLiveData<MutableList<Livro>>(mutableListOf())

    fun adicionarLivro(livro: Livro) {
        listaDeLivros.value?.let { lista ->
            lista.add(livro)
            listaDeLivros.value = lista
        }
    }

    fun removerLivro(livro: Livro) {
        listaDeLivros.value?.let { lista ->
            lista.remove(livro)
            listaDeLivros.value = lista
        }
    }
}
