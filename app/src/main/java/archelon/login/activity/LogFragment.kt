package archelon.login.activity

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText


class LogFragment() : Fragment() {
    private lateinit var viewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding:FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        // Create an instance of the ViewModel Factory.
        val application = requireNotNull(this.activity).application
        val viewModelFactory = LogViewFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        val logViewModel = ViewModelProvider(this, viewModelFactory)
            .get(LogViewModel::class.java)

        // binding object to reference
        binding.viewModel = logViewModel
        viewModel = logViewModel

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

        ActivityCompat.requestPermissions(
            (context as Activity?)!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
            0
        )
    }


    private fun showError(){
        //TODO: Add functionality here
    }

    private fun goToNextScreen(){
        viewModel.resetResponse()
        findNavController().navigate(R.id.login_to_menu)
    }

}