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

    private lateinit var viewPager: BottomSheetViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        viewPager = binding.bottomPager
        tabLayout = binding.tabLayoutBottom
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
    }

    private fun setupTabs() {
        val bottomViewPagerAdapter = BottomViewPagerAdapter()
        viewPager.viewPager.isUserInputEnabled = false
        viewPager.adapter = bottomViewPagerAdapter
        viewPager.offscreenPageLimit = bottomViewPagerAdapter.itemCount
        viewPager.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

            }
        })
        TabLayoutMediator(
            tabLayout,
            viewPager.viewPager
        ) { tab, position ->
            tab.text = "Tab ${(position + 1)}"
        }.attach()
    }

    private inner class BottomViewPagerAdapter() : FragmentStateAdapter(childFragmentManager, lifecycle) {

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> BottomFirstFragment()
            1 -> BottomSecondFragment()
            2 -> BottomFirstFragment()
            else -> throw IllegalArgumentException("Illegal fragment position: $position")
        }

        override fun getItemCount(): Int = 3
    }

}

