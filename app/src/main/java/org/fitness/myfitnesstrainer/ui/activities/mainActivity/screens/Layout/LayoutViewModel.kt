package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Layout


import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.repository.Resource


class LayoutViewModel(myFitnessRepository: MyFitnessRepository) : ViewModel() {
    private val myFitnessRepository = myFitnessRepository

    fun getProfileRepository(): Resource<Profile> {
        return myFitnessRepository.profile
    }

    fun logout() {
        myFitnessRepository.logout()
    }

    fun login() {
        myFitnessRepository.login()
    }
}
