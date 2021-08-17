package archelon.hatching.hatchTwo

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
import com.decouikit.atom.databinding.FragmentHatchingTwoBinding


class HatchingTwoFragment : Fragment()  {

    private lateinit var viewModel: HatchingTwoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val binding: FragmentHatchingTwoBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_hatching_two, container, false
    )

        // Create an instance of Factory.
        val application = requireNotNull(this.activity).application
        val vmFactory = HatchingTwoViewFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        val viewModel = ViewModelProvider(this, vmFactory)
            .get(HatchingTwoViewModel::class.java)

        // binding object to reference
        binding.viewModel = viewModel
        this.viewModel = viewModel

        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //get drawable Camera
        val cameraIcon = view.findViewById<ImageView>(R.id.camera_icon_hatch_two)

        cameraIcon.setOnClickListener {
            //Open camera app
            takePicture()
        }

        //movement control
        viewModel.response.observe(viewLifecycleOwner) { response ->
            if (response) {
                goToNextScreen()
            } else {
                showError()
            }
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
                (context as Activity?)!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }else{

            //create location logger
            val locationListener: LocationListener = LocationGPSListener(viewModel)
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000L, 10f, locationListener
            )

        }

        //Cancel Survey
        val cancelButton = view.findViewById<Button>(R.id.button_cancel_HatchTwo)

        cancelButton.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Cancelling the survey")
            builder.setMessage("Are you sure you wish to cancel?")

            builder.setPositiveButton("YES") { dialog, _ -> navigateBackToChooseMenu(dialog)}

            builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }

            val alert: AlertDialog = builder.create()
            alert.show()

        }

        //previous Button
        val previousButton = view.findViewById<Button>(R.id.button_previous_hatchTwo)

        previousButton.setOnClickListener {

            //remove hatch one
            viewModel.removeHatchOne()

            //Go Back
            findNavController().navigate(R.id.action_hatchingTwo_to_hatchingOne)

        }

    }

    private fun navigateBackToChooseMenu(dialog: DialogInterface) {
        //remove hatch one
        viewModel.removeHatchOne()

        dialog.dismiss()

        //Go back to choose menu
        findNavController().navigate(R.id.action_hatchingTwo_to_morningSurveySubMenuFragment)

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
            viewModel.addImage("photoID1")
        }

    }

    private fun showError(){
        //TODO: Add functionality here
    }

    private fun goToNextScreen(){
        viewModel.resetResponse()
        findNavController().navigate(R.id.action_hatchingTwo_to_morningSurveySubMenuFragment)
    }

}