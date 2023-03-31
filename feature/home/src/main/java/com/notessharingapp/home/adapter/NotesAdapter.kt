package com.notessharingapp.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notessharingapp.home.databinding.ViewholderNotesBinding
import com.notessharingapp.model.Notes
import com.notessharingapp.ui.hide
import com.notessharingapp.ui.show

class NotesAdapter(val notesList: List<Notes>,val onClickNote: (Notes) -> Unit): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    inner class ViewHolder(val binding: ViewholderNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        val backGroundColor = listOf<String>("#F44336","#EC407A","#AB47BC","#7E57C2","#5C6BC0")
        fun bind(notes: Notes) {
            binding.nickName.text = notes.authorNickName
            if (notes.note.isEmpty()){
                binding.note.hide()
            }else{
                binding.note.show()
            }

            binding.note.text = notes.note

            if (notes.title.isEmpty()){
                binding.title.hide()
            }else{
                binding.title.show()
            }
            binding.title.text = notes.title
            binding.card.setCardBackgroundColor(Color.parseColor(notes.bgColor))
            binding.card.setOnClickListener {
                onClickNote(notes)
            }
        }

    }
}