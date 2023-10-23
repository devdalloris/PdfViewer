package com.example.pdfviewer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.pdfviewer.R
import com.example.pdfviewer.databinding.FragmentPdfViewBinding
import java.io.File

class PdfViewFragment : Fragment(R.layout.fragment_pdf_view) {
    private var _binding: FragmentPdfViewBinding? = null
    private val binding get() = _binding!!

    private val arguments by navArgs<PdfViewFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPdfViewBinding.bind(view)

        val filePath = arguments.path
        val file= File(filePath)
        binding.pdfView.fromFile(file).load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}