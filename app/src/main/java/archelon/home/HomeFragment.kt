package archelon.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val surveyButton = view.findViewById<Button>(R.id.new_survey_Button_top)
        val logOutButton = view.findViewById<Button>(R.id.log_out_button)

        surveyButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_fragment_to_morningSurveyStartFragment)
        }

        logOutButton.setOnClickListener {
            //Remove the API key
            val application = requireNotNull(this.activity).application
            val sharedPref = application.getSharedPreferences("archelon", Context.MODE_PRIVATE)
            val edit = sharedPref.edit()
            edit.clear()
            edit.apply()

            findNavController().navigate(R.id.action_home_fragment_to_log_fragment)
        }

        //Check API key is given
        val application = requireNotNull(this.activity).application
        val sharedPref = application.getSharedPreferences("archelon", Context.MODE_PRIVATE)
        val key = sharedPref.getString("key", "a")

        if (key == "a"){
            findNavController().navigate(R.id.action_home_fragment_to_log_fragment)
        }
    }

}