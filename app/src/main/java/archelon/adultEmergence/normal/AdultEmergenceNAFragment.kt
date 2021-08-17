package archelon.adultEmergence.normal

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
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
import archelon.GPS.LocationGPSListener
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentAdultEmergenceNormalBinding

class AdultEmergenceNAFragment: Fragment() {

    private lateinit var vm : AdultEmergenceNAViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentAdultEmergenceNormalBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_adult_emergence_normal, container, false
        )

        // Create an instance of Factory.
        val application = requireNotNull(this.activity).application
        val vmFactory = AdultEmergenceNAViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        val viewModel = ViewModelProvider(this, vmFactory)
            .get(AdultEmergenceNAViewModel::class.java)

        // binding object to reference
        binding.viewModel = viewModel
        this.vm = viewModel

        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //get drawable Camera
        val cameraIcon = view.findViewById<ImageView>(R.id.camera_icon_normal)

        cameraIcon.setOnClickListener {
            //Open camera app
            takePicture()
        }


        //Cancel Survey
        val cancelButton = view.findViewById<Button>(R.id.cancel_button_adult_normal)

        cancelButton.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Cancel Emergence")
            builder.setMessage("Are you sure you want to cancel this Emergence?")

            builder.setPositiveButton("YES") { dialog, _ -> navigateBackToMenu(dialog)}

            builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }

            val alert: AlertDialog = builder.create()
            alert.show()

        }


        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //check that they have the permissions given
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Ask again for the permissions
            ActivityCompat.requestPermissions(
                (context as Activity?)!!, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                0
            )
        }
        else {

            //create location logger
            val locationListener: LocationListener = LocationGPSListener(vm)
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000L, 10f, locationListener
            )

        }

        //movement control
        vm.response.observe(viewLifecycleOwner) { response ->
            if (response) {
                goToNextScreen()
            } else {
                showError()
            }
        }


        //Previous Button
        val previousButton = view.findViewById<Button>(R.id.button_previous_normal)

        previousButton.setOnClickListener{
            //Remove main adult emergence record from data
            vm.removeLastAdultMain()

            //Navigate back to menu
            findNavController().navigate(R.id.action_adultEmergenceNAFragment_to_adultEmergence)

        }

    }


    private fun navigateBackToMenu(dialog: DialogInterface) {
        //Remove main adult emergence record from data
        vm.removeLastAdultMain()

        //remove dialog
        dialog.dismiss()

        //Navigate back to menu
        findNavController().navigate(R.id.action_adultEmergenceNAFragment_to_morningSurveySubMenuFragment)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode==100 && resultCode== Activity.RESULT_OK){
            val extras = data?.extras

            val imageBitmap = extras?.get("data") as Bitmap

            //current version only needs name
            vm.addImage("photoID1")
        }

    }

    private fun showError(){
        //TODO: Add functionality here
    }

    private fun goToNextScreen(){
        vm.resetResponse()
        findNavController().navigate(R.id.action_adultEmergenceNAFragment_to_morningSurveySubMenuFragment)
    }

}

