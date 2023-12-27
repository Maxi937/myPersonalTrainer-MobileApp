package org.fitness.myfitnesstrainer.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.data.local.models.UserDetails


class ProfilePreviewParameterProvider : PreviewParameterProvider<Profile> {
    override val values = sequenceOf(
        getPreviewProfile(),
    )
}
fun getPreviewProfile() : Profile {
    val squat = ExerciseModel(bodyPart = "Legs", description = "Leg Exercise", name = "Squat")
    val deadlift = ExerciseModel(bodyPart = "Back", description = "Back Exercise", name = "Deadlift")
    val benchPress = ExerciseModel(bodyPart = "Chest", description = "Chest Exercise", name = "Bench Press")
    val shoulderPress = ExerciseModel(bodyPart = "Shoulders", description = "Shoulder Exercise", name = "Shoulder Press")
    val lateralRaise = ExerciseModel(bodyPart = "Shoulders", description = "Shoulder Exercise", name = "Lateral Raise")

    val workoutA = WorkoutModel(exercises = mutableListOf(shoulderPress, benchPress, deadlift), name = "Workout A" )
    val workoutB = WorkoutModel(exercises = mutableListOf(squat, lateralRaise, deadlift), name = "Workout B" )
    val workoutC = WorkoutModel(exercises = mutableListOf(shoulderPress, benchPress, squat), name = "Workout C" )

    val exercises = listOf(squat, deadlift, benchPress, shoulderPress, lateralRaise)
    val workouts = listOf(workoutA, workoutB, workoutC)

    val userDetails = UserDetails("preview@email.com", "Mr. Preview", "Previewer")

    return Profile(exercises, workouts, userDetails)
}