package archelon.adultEmergence

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import archelon.MainActivity
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentAdultEmergenceBinding


class AdultEmergenceFragment : Fragment() {

    private lateinit var adultViewModel: AdultEmergenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAdultEmergenceBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_adult_emergence, container, false
        )

        // Create an instance of Factory.
        val application = requireNotNull(this.activity).application
        val adultViewModelFactory = AdultEmergenceViewFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        val adultViewModel = ViewModelProvider(this, adultViewModelFactory)
            .get(AdultEmergenceViewModel::class.java)

        // binding object to reference
        binding.adultViewModel = adultViewModel
        this.adultViewModel = adultViewModel


        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //get drawable Camera
        val cameraIcon = view.findViewById<ImageView>(R.id.camera_icon)

        cameraIcon.setOnClickListener {

            //Open camera app
            takePicture()
        }

        //Cancel Survey
        val cancelButton = view.findViewById<Button>(R.id.cancel_button_adult_main)

        cancelButton.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Cancelling the survey")
            builder.setMessage("Are you sure you wish to cancel?")

            builder.setPositiveButton("YES") { dialog, _ -> navigateBackToMenu(dialog)}

            builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }

            val alert: AlertDialog = builder.create()
            alert.show()

        }


        //Go to the next UI based on viewmodel's direction
        adultViewModel.location.observe(viewLifecycleOwner, { response ->
            when(adultViewModel.location.value){
                "nest" -> goToNestPage()
                "normal" -> goToNormalPage()
            }
        })

        adultViewModel.getNumberOfEntries()
    }

    private fun navigateBackToMenu(dialog: DialogInterface){
        dialog.dismiss()
        findNavController().navigate(R.id.action_adultEmergence_to_morningSurveySubMenuFragment)
    }

    private fun takePicture(){
        val imageTakeIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!, arrayOf(Manifest.permission.CAMERA),
                0
            )
        }else{
            startActivityForResult(imageTakeIntent, 100)
        }

    }

    private fun goToNestPage(){
        adultViewModel.resetResponse()
        findNavController().navigate(R.id.action_adultEmergence_to_adultEmergenceOne)
    }

    private fun goToNormalPage(){
        adultViewModel.resetResponse()
        findNavController().navigate(R.id.action_adultEmergence_to_adultEmergenceNAFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode==100 && resultCode==RESULT_OK){
            adultViewModel.addImage("photoID")
        }

    }

}