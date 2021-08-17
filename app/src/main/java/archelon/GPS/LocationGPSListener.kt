package archelon.GPS

import android.R.attr.tag
import android.location.Location
import android.location.LocationListener
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.widget.Toast


class LocationGPSListener(private val viewModel: ViewModelGPSListener) : LocationListener {
    override fun onLocationChanged(location: Location?) {
        viewModel.setLocation(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        when (status) {
            LocationProvider.OUT_OF_SERVICE -> {
                Log.v("test", "Status Changed: Out of Service")

            }
            LocationProvider.TEMPORARILY_UNAVAILABLE -> {
                Log.v("test", "Status Changed: Temporarily Unavailable")

            }
            LocationProvider.AVAILABLE -> {
                Log.v("test", "Status Changed: Available")

            }
        }
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }
}