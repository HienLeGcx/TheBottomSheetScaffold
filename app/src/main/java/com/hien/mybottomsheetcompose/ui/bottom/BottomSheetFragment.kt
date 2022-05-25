package com.hien.mybottomsheetcompose.ui.bottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hien.mybottomsheetcompose.databinding.FragmentBottomSheetBinding

class BottomSheetFragment : Fragment() {

    private lateinit var viewModel: BottomSheetViewModel
    private var _binding: FragmentBottomSheetBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var bottomCollectionAdapter: BottomCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        viewPager = binding.pagerBottom
        tabLayout = binding.tabLayoutBottom
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomCollectionAdapter = BottomCollectionAdapter(this)
        viewPager.adapter = bottomCollectionAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab ${(position + 1)}"
        }.attach()
    }

}

class BottomCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BottomFirstFragment()
            1 -> BottomSecondFragment()
            else -> {
                BottomFirstFragment()
            }
        }
    }
}