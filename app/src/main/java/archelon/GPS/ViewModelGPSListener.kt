package archelon.GPS

import android.location.Location

interface ViewModelGPSListener {
    fun setLocation(location:Location?)
}