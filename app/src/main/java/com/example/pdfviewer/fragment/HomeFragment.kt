package com.example.pdfviewer.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pdfviewer.R
import com.example.pdfviewer.adapter.RvPdfAdapter
import com.example.pdfviewer.databinding.FragmentHomeBinding
import com.example.pdfviewer.model.Pdf
import java.io.File
import kotlin.text.StringBuilder


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var rvPdfAdapter: RvPdfAdapter? = null
    var listOfPdf: MutableList<Pdf> = ArrayList()

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        listOfPdf.clear()
        findPdfFiles(Environment.getExternalStorageDirectory())

    }

    private fun findPdfFiles(directory: File) {
        val fileFormat = ".pdf"
        val listOfFiles = directory.listFiles()
        if (listOfFiles != null && listOfFiles.isNotEmpty()) {
            for (i in listOfFiles.indices) {
                if (listOfFiles[i].isDirectory) {
                    findPdfFiles(listOfFiles[i])
                } else {
                    if (listOfFiles[i].name.endsWith(fileFormat)) {
                        listOfPdf.add(
                            Pdf(
                                listOfFiles[i].name,
                                formatSize(listOfFiles[i].length()),
                                listOfFiles[i].absolutePath
                            )
                        )
                    }
                }
            }
            rvPdfAdapter = RvPdfAdapter(listOfPdf)
            binding.recyclerview.adapter = rvPdfAdapter
            rvPdfAdapter!!.setOnPdfClickedListener(object : RvPdfAdapter.OnPdfClickedListener {
                override fun onClicked(position: Int) {
                    val directions =
                        HomeFragmentDirections.actionHomeFragmentToPdfViewFragment(listOfPdf[position].filePath)
                    findNavController().navigate(directions)
                }
            })

        }
    }

    private fun formatSize(size: Long): String {
        listOfPdf.clear()
        var tempSize = size
        var format = ""
        if (tempSize >= 1024) {
            format = "Bytes"
            tempSize /= 1024
            if (tempSize >= 1024) {
                format = "Mb"
                tempSize /= 1024
            }
        }
        val stringBuilder = StringBuilder(tempSize.toString())
        if (format.isNotEmpty()) {
            stringBuilder.append(format)
        }
        return stringBuilder.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}