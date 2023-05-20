package org.d3if3008.annualsavings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if3008.annualsavings.R
import org.d3if3008.annualsavings.databinding.FragmentSaranBinding
import org.d3if3008.annualsavings.model.KategoriSave

class SaranFragment : Fragment() {
    private lateinit var binding: FragmentSaranBinding
    private val args: SaranFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUI(kategori: KategoriSave) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriSave.TINGKATKAN -> {
                actionBar?.title = getString(R.string.judul_tingkatkan)
                binding.imageView.setImageResource(R.drawable.tingkatkan)
                binding.textView.text = getString(R.string.saran_tingkatkan)
            }
            KategoriSave.CUKUP -> {
                actionBar?.title = getString(R.string.judul_cukup)
                binding.imageView.setImageResource(R.drawable.cukup)
                binding.textView.text = getString(R.string.saran_cukup)
            }
            KategoriSave.BERLEBIHAN -> {
                actionBar?.title = getString(R.string.judul_berlebihan)
                binding.imageView.setImageResource(R.drawable.berlebihan)
                binding.textView.text = getString(R.string.saran_berlebihan)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI(args.kategori)
    }
}