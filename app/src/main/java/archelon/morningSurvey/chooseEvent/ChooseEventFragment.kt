package archelon.morningSurvey.chooseEvent

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentMorningSurveyChooseEventBinding

class ChooseEventFragment : Fragment() {

    private lateinit var viewModel : ChooseEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentMorningSurveyChooseEventBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_morning_survey_choose_event, container, false
        )

        // Create an instance of the ViewModel Factory.
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ChooseEventViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ChooseEventViewModel::class.java)


        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val cancelButton = view.findViewById<Button>(R.id.sub_menu_cancel)

        cancelButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Cancel Survey")
            builder.setMessage("Are you sure you want to cancel this survey and delete survey entries?")

            builder.setPositiveButton("YES") { dialog, _ -> navigateBackToStartMenu(dialog)}

            builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }

            val alert: AlertDialog = builder.create()
            alert.show()

        }


        val adultEmergenceButton = view.findViewById<Button>(R.id.adultEmergenceButton)

        adultEmergenceButton.setOnClickListener {
            //Go back to previous page
            findNavController().navigate(R.id.action_choose_event_to_adultEmergence)
        }

        val  hatchingButton = view.findViewById<Button>(R.id.hatching_choose_button)

        hatchingButton.setOnClickListener {
            //Go back to previous page
            findNavController().navigate(R.id.action_choose_event_to_hatchingOne)
        }

        val washButton = view.findViewById<Button>(R.id.wash_inundation_button)

        washButton.setOnClickListener {
            //Go back to previous page
            findNavController().navigate(R.id.action_choose_event_to_washFragment)
        }
    }

    private fun navigateBackToStartMenu(dialog: DialogInterface){
        //delete morning survey
        viewModel.removeCurrentMS()

        dialog.dismiss()

        //Go back to start
        findNavController().navigate(R.id.action_choose_event_to_home_fragment)
    }

}