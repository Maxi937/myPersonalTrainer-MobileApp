package org.fitness.myfitnesstrainer.ui.activities.workoutActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Layout.LayoutViewModel

class WorkoutActivityViewModelFactory(private val workout: WorkoutModel) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = WorkoutActivityViewModel(workout) as T
}
class WorkoutActivityViewModel(workout: WorkoutModel): ViewModel() {
    val workout = workout

}