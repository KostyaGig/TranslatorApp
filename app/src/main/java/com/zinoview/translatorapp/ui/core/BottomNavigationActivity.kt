package com.zinoview.translatorapp.ui.core

import androidx.annotation.MenuRes

interface BottomNavigationActivity : SelectItem {

    fun inflateBottomNavigationMenu(@MenuRes id: Int)
}