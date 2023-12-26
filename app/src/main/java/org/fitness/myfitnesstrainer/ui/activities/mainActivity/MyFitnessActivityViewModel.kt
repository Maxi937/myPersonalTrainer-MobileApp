package org.fitness.myfitnesstrainer.ui.activities.mainActivity


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.repository.Resource

//class LayoutViewModelFactory(private val myFitnessProfileRepository: MyFitnessProfileRepository) :
//    ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T = LayoutViewModel(myFitnessProfileRepository) as T
//}
//
//class LayoutViewModel(myFitnessProfileRepository: MyFitnessProfileRepository) : ViewModel() {
//    private val myFitnessRepository = myFitnessProfileRepository
//
//    fun getProfileRepository(): Resource<Profile> {
//        return myFitnessRepository.profile
//    }
//
//    fun logout() {
//        myFitnessRepository.logout()
//    }
//
//    fun login() {
//        myFitnessRepository.login()
//    }
//}
