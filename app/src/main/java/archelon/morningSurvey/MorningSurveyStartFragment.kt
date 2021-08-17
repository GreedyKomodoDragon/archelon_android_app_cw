package archelon.morningSurvey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentMorningSurveyBinding


class MorningSurveyStartFragment : Fragment() {

    private lateinit var viewModel : MorningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentMorningSurveyBinding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_morning_survey, container, false
        )


        // Create an instance of the ViewModel Factory.
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MorningViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MorningViewModel::class.java)

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



    }

    private fun showError(){
        //TODO: Add functionality here
    }

    private fun goToNextScreen(){
        viewModel.resetResponse()
        findNavController().navigate(R.id.action_morningSurveyStartFragment_to_observers_weather)
    }

}