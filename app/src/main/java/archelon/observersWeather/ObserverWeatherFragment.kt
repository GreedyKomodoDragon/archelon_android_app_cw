package archelon.observersWeather

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentObserversWeatherBinding


class ObserverWeatherFragment : Fragment() {
    lateinit var viewModel:ObserverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentObserversWeatherBinding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_observers_weather, container, false
        )

        // Create an instance of the ViewModel Factory.
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ObserversViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ObserverViewModel::class.java)

        // binding object to reference
        binding.viewModel = viewModel


        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.response.observe(viewLifecycleOwner, { response ->
            if (response) {
                goToNextScreen()
            } else {
                showError()
            }
        })

        //Cancel Survey
        val cancelButton = view.findViewById<Button>(R.id.observer_cancel_button)

        cancelButton.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Cancel Survey")
            builder.setMessage("Are you sure you want to cancel this survey and delete survey entries??")

            builder.setPositiveButton("YES") { dialog, _ -> navigateBackToStartMenu(dialog)}

            builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }

            val alert: AlertDialog = builder.create()
            alert.show()

        }

        //Previous Button
        val previousButton = view.findViewById<Button>(R.id.observer_previous_button)

        previousButton.setOnClickListener{onClickPreviousButton()}


        //On back press Remove last morning survey
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK  && event.action == KeyEvent.ACTION_DOWN) {
                viewModel.removeLastMorningSurvey()
            }
            false
        }
    }

    private fun navigateBackToStartMenu(dialog: DialogInterface){
        //Remove main adult emergence record from data
        viewModel.removeLastMorningSurvey()

        //remove dialog
        dialog.dismiss()

        findNavController().navigate(R.id.action_observers_weather_to_home_fragment)
    }

    private fun onClickPreviousButton(){
        //Remove last morning survey
        viewModel.removeLastMorningSurvey()

        findNavController().navigate(R.id.action_observers_weather_to_morningSurveyStartFragment)
    }

    private fun showError(){
        //TODO: Add functionality here
    }

    private fun goToNextScreen(){
        viewModel.resetResponse()
        findNavController().navigate(R.id.action_observers_weather_to_morningSurveySubMenuFragment)
    }

}