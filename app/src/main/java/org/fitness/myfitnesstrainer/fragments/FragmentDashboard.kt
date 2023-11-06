package org.fitness.myfitnesstrainer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.databinding.FragmentDashboardBinding

class FragmentDashboard : Fragment() {
    lateinit var activity: MainActivity
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        activity = getActivity() as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        var welcome = "Welcome, " + activity.app.profile!!.fname
        val fullName = activity.app.profile!!.fname + activity.app.profile!!.lname

        binding.txtDashboardUserName.text = (welcome)
        binding.txtFullName.text = fullName
        binding.txtUserEmail.text = activity.app.profile!!.email

        val view : View = binding.root;
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = FragmentDashboard()

    }
}