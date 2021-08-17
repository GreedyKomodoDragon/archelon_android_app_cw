package archelon.adultEmergence.nest.relocation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentRelocationStartBinding

class RelocationStartFragment : Fragment() {
    private lateinit var  viewModel: RelocationStartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentRelocationStartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_relocation_start, container, false
        )

        // Create an instance of Factory.
        val application = requireNotNull(this.activity).application
        val vmFactory = RelocationStartViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        val vm = ViewModelProvider(this, vmFactory)
            .get(RelocationStartViewModel::class.java)

        // binding object to reference
        binding.viewModel = vm
        this.viewModel = vm


        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}