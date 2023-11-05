package org.fitness.myfitnesstrainer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.databinding.FragmentDashboardBinding

class FragmentDashboard : Fragment() {
    private val user: String = ""
    lateinit var activity: MainActivity
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        activity = getActivity() as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.txtDashboardUserName.text = activity.profile.fname
        val view : View = binding.root;
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = FragmentDashboard()

    }
}