package com.challenge.meli.utils

import androidx.navigation.NavController
import com.google.android.material.appbar.MaterialToolbar

class ViewHelper {
     fun goBack(toolbar: MaterialToolbar, navController:NavController){
         toolbar.setOnClickListener {
             navController.popBackStack()
         }
     }
}