package com.hien.mybottomsheetcompose.ui.bottom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hien.mybottomsheetcompose.R

class BottomSecondFragment : Fragment() {

    companion object {
        fun newInstance() = BottomSecondFragment()
    }

    private lateinit var viewModel: BottomSecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_second, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BottomSecondViewModel::class.java)
        // TODO: Use the ViewModel
    }

}