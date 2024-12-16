package com.hometest.weather.domain.usecases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GetLocationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    suspend operator fun invoke(): Location? {
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                getLastLocationAsync()
            } catch (e: Exception) {
                Log.e("GetLocationUseCase", "Failed to get location", e)
                null
            }
        } else {
            Log.e("GetLocationUseCase", "Permission not granted")
            null
        }
    }

    private suspend fun getLastLocationAsync(): Location? {
        return suspendCancellableCoroutine { continuation ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationTask: Task<Location> = fusedLocationProviderClient.lastLocation
                locationTask.addOnSuccessListener { location ->
                    continuation.resume(location)
                }
                locationTask.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
            } else {
                continuation.resume(null)
            }
        }
    }
}
