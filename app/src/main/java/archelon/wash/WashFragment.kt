package archelon.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.decouikit.atom.R
import com.decouikit.atom.databinding.FragmentInundationWashBinding

class WashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentInundationWashBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_inundation_wash, container, false
        )

        // Specify the current activity as the lifecycle owner of the binding
        binding.lifecycleOwner = this

        return binding.root
    }
}