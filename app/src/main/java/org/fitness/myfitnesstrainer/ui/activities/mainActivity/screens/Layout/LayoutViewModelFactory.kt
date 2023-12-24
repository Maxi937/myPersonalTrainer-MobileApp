package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Layout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository

class LayoutViewModelFactory(private val myFitnessRepository: MyFitnessRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = LayoutViewModel(myFitnessRepository) as T
}