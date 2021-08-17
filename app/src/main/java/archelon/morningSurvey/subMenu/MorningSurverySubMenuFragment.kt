package archelon.morningSurvey.subMenu

import android.R.attr.duration
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentMorningSurveySubMenuBinding


class MorningSurverySubMenuFragment : Fragment() {

    private lateinit var viewModel : MorningSurveySubMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentMorningSurveySubMenuBinding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_morning_survey_sub_menu, container, false
        )

        // Create an instance of the ViewModel Factory.
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MorningSurveySubMenuViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MorningSurveySubMenuViewModel::class.java)

        // binding object to reference
        binding.viewModel = viewModel


        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //get all buttons on bottom of the screen; control management buttons
        val previousButton = view.findViewById<Button>(R.id.sub_menu_previous)
        val cancelButton = view.findViewById<Button>(R.id.sub_menu_cancel)
        val addNewEventButton = view.findViewById<Button>(R.id.add_new_event)


        previousButton.setOnClickListener {
            //Go back to previous page
            findNavController().navigate(R.id.action_morningSurveySubMenuFragment_to_observers_weather)
        }

        cancelButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Cancel Survey")
            builder.setMessage("Are you sure you want to cancel this survey and delete survey entries?")

            builder.setPositiveButton("YES") { dialog, _ -> navigateBackToStartMenu(dialog)}

            builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }

            val alert: AlertDialog = builder.create()
            alert.show()

        }

        addNewEventButton.setOnClickListener {
            //Go back to start
            findNavController().navigate(R.id.action_morningSurveySubMenuFragment_to_choose_event)
        }


        viewModel.response.observe(viewLifecycleOwner, { response ->
            when (response) {
                "failed" -> {
                    viewModel.resetResponse()
                    val toast: Toast = Toast.makeText(context, "Unable to upload your survey", Toast.LENGTH_LONG)
                    toast.show()
                }
                "passed" -> {
                    viewModel.resetResponse()
                    findNavController().navigate(R.id.action_morningSurveySubMenuFragment_to_home_fragment)
                }
            }


        })

    }

    private fun navigateBackToStartMenu(dialog: DialogInterface){
        //delete morning survey
        viewModel.removeCurrentMS()

        dialog.dismiss()

        //Go back to start
        findNavController().navigate(R.id.action_morningSurveySubMenuFragment_to_home_fragment)
    }

}