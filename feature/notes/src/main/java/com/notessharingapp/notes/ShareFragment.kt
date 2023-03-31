package com.notessharingapp.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.notessharingapp.notes.databinding.BottomsheetShareBinding
import dagger.hilt.android.AndroidEntryPoint

//FIXME; as of now this is not used, could be removed in the later release?
@AndroidEntryPoint
class ShareFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetShareBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetShareBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        return binding.root
    }


    fun setSwitchListener(listener: CompoundButton.OnCheckedChangeListener){
        arguments?.getBoolean("isPublic")?.let {
            binding.switchPublic.isChecked = it
        }
        binding.switchPublic.setOnCheckedChangeListener(listener)
    }

    companion object {
        fun getInstance(isPublic:Boolean) : ShareFragment{
            return ShareFragment().apply {
                arguments?.putBoolean("isPublic",isPublic)
            }
        }
    }

}