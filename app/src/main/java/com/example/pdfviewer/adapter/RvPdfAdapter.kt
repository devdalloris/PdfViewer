package com.example.pdfviewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfviewer.databinding.RowItemBinding
import com.example.pdfviewer.model.Pdf

class RvPdfAdapter(private val list: List<Pdf>) : RecyclerView.Adapter<RvPdfAdapter.ViewHOlder>() {
    private var listener: OnPdfClickedListener? = null

    inner class ViewHOlder(private val binding: RowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pdf: Pdf) {
            binding.txFileName.text = pdf.fileName
            binding.txFileSize.text = pdf.fileSize
            binding.root.setOnClickListener {
                if(listener!=null){
                    listener!!.onClicked(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHOlder {
        return ViewHOlder(
            RowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHOlder, position: Int) {
        holder.onBind(list[position])
    }

    fun setOnPdfClickedListener(listener: OnPdfClickedListener) {
        this.listener = listener
    }

    interface OnPdfClickedListener {
        fun onClicked(position: Int)
    }
}