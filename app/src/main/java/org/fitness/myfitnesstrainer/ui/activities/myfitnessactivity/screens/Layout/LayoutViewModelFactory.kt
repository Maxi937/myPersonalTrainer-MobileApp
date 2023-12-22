package org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity.screens.Layout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.fitness.myfitnesstrainer.auth.AuthManager

class LayoutViewModelFactory(private val authManager: AuthManager) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = LayoutViewModel(authManager) as T
}