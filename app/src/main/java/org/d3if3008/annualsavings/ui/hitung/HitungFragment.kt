package org.d3if3008.annualsavings.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3008.annualsavings.R
import org.d3if3008.annualsavings.databinding.FragmentHitungBinding
import org.d3if3008.annualsavings.db.SaveDb
import org.d3if3008.annualsavings.model.HasilSave
import org.d3if3008.annualsavings.model.KategoriSave

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = SaveDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener{hitungSave()}
        binding.saranButton.setOnClickListener{viewModel.mulaiNavigasi()}
        binding.shareButton.setOnClickListener{shareData()}
        viewModel.getHasilSave().observe(requireActivity(), { showResult(it) })
        viewModel.getNavigasi().observe(viewLifecycleOwner) {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections.actionHitungFragmentToSaranFragment(it)
            )
            viewModel.selesaiNavigasi()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun hitungSave(){
        val penghasilan = binding.bulananEditText.text.toString()
        if (TextUtils.isEmpty(penghasilan)) {
            Toast.makeText(context, R.string.penghasilan_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tabungan = binding.tabunganEditText.text.toString()
        if (TextUtils.isEmpty(tabungan)) {
            Toast.makeText(context, R.string.tabungan_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.status_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungSave(
            penghasilan.toDouble(),
            tabungan.toDouble(),
            selectedId == R.id.mahasiswaRadioButton
        )
    }

    private fun getKategoriLabel(kategori: KategoriSave): String {
        val stringRes = when (kategori) {
            KategoriSave.TINGKATKAN -> R.string.tingkatkan
            KategoriSave.CUKUP -> R.string.cukup
            KategoriSave.BERLEBIHAN -> R.string.berlebihan
        }
        return getString(stringRes)
    }

    private fun showResult(result: HasilSave?) {
        if (result == null) return
        binding.saveTextView.text = getString(R.string.save_x,result.save)
        binding.kategoriTextView.text = getString(R.string.kategori_x,getKategoriLabel(result.kategori))
        binding.buttonGroup.visibility = View.VISIBLE
    }

    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.mahasiswaRadioButton)
            getString(R.string.mahasiswa)
        else
            getString(R.string.bekerja)
        val message = getString(R.string.bagikan_template,
            binding.bulananEditText.text,
            binding.tabunganEditText.text,
            gender,
            binding.saveTextView.text,
            binding.kategoriTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }
}