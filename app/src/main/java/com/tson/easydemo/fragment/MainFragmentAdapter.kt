package com.tson.easydemo.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * @author Tson
 */
class MainFragmentAdapter(fm: FragmentManager, var frags: MutableList<Fragment>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    fun setData(frags: MutableList<Fragment>) {
        this.frags = frags
        notifyDataSetChanged()
    }

    fun replace(frg: Fragment, index: Int) {
        this.frags[index] = frg
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment = getFragment(position)

    private fun getFragment(position: Int): Fragment {
        return frags[position]
    }

    override fun getCount(): Int = frags.size

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

}