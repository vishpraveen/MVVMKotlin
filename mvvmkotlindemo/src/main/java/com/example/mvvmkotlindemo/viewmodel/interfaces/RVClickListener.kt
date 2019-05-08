package com.example.mvvmkotlindemo.viewmodel.interfaces

interface RVClickListener {
    fun onClick(where: EnumClick, objects: Any?, position: Int, checked: Boolean)
}