package archelon.adultEmergence.nest.one

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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
import com.decouikit.atom.databinding.FragmentEmergenceNestOneBinding

class AdultEmergenceSubOneFragment : Fragment() {

    private lateinit var  nestOneViewModel : AdultEmergenceSubOneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentEmergenceNestOneBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_emergence_nest_one, container, false
        )

        // Create an instance of Factory.
        val application = requireNotNull(this.activity).application
        val adultEmergenceSubOneFactory = AdultEmergenceSubOneFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        val adultViewModel = ViewModelProvider(this, adultEmergenceSubOneFactory)
            .get(AdultEmergenceSubOneViewModel::class.java)

        // binding object to reference
        binding.nestOneViewModel = adultViewModel
        this.nestOneViewModel = adultViewModel


        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Cancel Survey
        val cancelButton = view.findViewById<Button>(R.id.cancel_action_nest_one)

        cancelButton.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Cancel Emergence")
            builder.setMessage("Are you sure you want to cancel this Emergence?")

            builder.setPositiveButton("YES") { dialog, _ -> navigateBackToMenu(dialog)}

            builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }

            val alert: AlertDialog = builder.create()
            alert.show()

        }

        //movement control
        nestOneViewModel.response.observe(viewLifecycleOwner) { response ->
            if (response == "nest") {
                goToNestTwoScreen()
            }else if(response == "relocated"){
                goToRelocationScreen()
            }
        }

        //On back press remove current main
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK  && event.action == KeyEvent.ACTION_DOWN) {
                nestOneViewModel.removeLastAdultMain()
            }
            false
        }

    }

    private fun navigateBackToMenu(dialog: DialogInterface) {
        //Remove main adult emergence record from data
        nestOneViewModel.removeLastAdultMain()

        //remove dialog
        dialog.dismiss()

        //Navigate back to menu
        findNavController().navigate(R.id.action_adultEmergenceOne_to_morningSurveySubMenuFragment)
    }

    private fun goToNestTwoScreen(){
        nestOneViewModel.resetResponse()
        findNavController().navigate(R.id.action_adultEmergenceOne_to_adultEmergenceSubTwoFragment)
    }

    private fun goToRelocationScreen(){
        nestOneViewModel.resetResponse()
        findNavController().navigate(R.id.action_adultEmergenceOne_to_relocationStartFragment)
    }

}