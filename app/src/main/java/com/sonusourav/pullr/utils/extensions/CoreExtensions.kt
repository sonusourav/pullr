package com.sonusourav.pullr.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(layoutId: Int, fragment: Fragment) {
    this.supportFragmentManager.beginTransaction().add(layoutId, fragment).commit()
}


fun AppCompatActivity.addFragmentWithBackStack(layoutId: Int, fragment: Fragment, tag: String?) {
    this.supportFragmentManager.beginTransaction().add(layoutId, fragment)
        .addToBackStack(tag).commit()
}

fun Fragment.addFragment(layoutId: Int, fragment: Fragment) {
    this.requireFragmentManager().beginTransaction().add(layoutId, fragment).commit()
}

fun Fragment.addChildFragment(layoutId: Int, fragment: Fragment) {
    this.childFragmentManager.beginTransaction().add(layoutId, fragment).commit()
}

fun Fragment.addFragmentBackStack(layoutId: Int, fragment: Fragment, tag: String?) {
    this.fragmentManager?.beginTransaction()?.add(layoutId, fragment)?.addToBackStack(tag)
        ?.commit()
}

fun Fragment.replaceFragment(layoutId: Int, fragment: Fragment) {
    this.requireFragmentManager().beginTransaction().replace(layoutId, fragment).commit()
}

fun Fragment.replaceChildFragment(layoutId: Int, fragment: Fragment) {
    if (isAdded) {
        this.childFragmentManager.beginTransaction().replace(layoutId, fragment)
            .commitAllowingStateLoss()
    }
}

fun AppCompatActivity.replaceFragmentWithBackStack(
    layoutId: Int,
    fragment: Fragment,
    tag: String?
) {
    this.supportFragmentManager.beginTransaction().replace(layoutId, fragment)
        .addToBackStack(tag).commit()
}

fun Fragment.replaceFragmentBackStack(layoutId: Int, fragment: Fragment, tag: String?) {
    this.requireFragmentManager().beginTransaction().replace(layoutId, fragment).addToBackStack(tag)
        .commit()
}
