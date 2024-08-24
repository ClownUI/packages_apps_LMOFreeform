package com.libremobileos.freeform

import android.app.PendingIntent
import android.os.Build
import android.os.IBinder
import android.os.ServiceManager
import android.util.Log
import com.libremobileos.freeform.ILMOFreeformUIService
import java.util.Date

object LMOFreeformServiceManager {
    private const val TAG = "LMOFreeformServiceManager"
    private var iLMOFreeformService: ILMOFreeformUIService? = null

    fun init() {
        try {
            val r = ServiceManager.getService("lmo_freeform")
            iLMOFreeformService = ILMOFreeformUIService.Stub.asInterface(r)
            iLMOFreeformService?.ping()
        } catch (e: Exception) {
            Log.e(TAG, "$e")
            e.printStackTrace()
        }
    }

    fun ping(): Boolean {
        return try {
            iLMOFreeformService!!.ping()
            true
        } catch (e: Exception) {
            Log.e(TAG, "$e")
            e.printStackTrace()
            false
        }
    }

    fun createWindow(packageName: String, activityName: String, userId: Int, width: Int, height: Int, densityDpi: Int) {
        iLMOFreeformService?.startAppInFreeform(
            packageName,
            activityName,
            userId,
            null,
            width,
            height,
            densityDpi,
            120.0f,
            false,
            true,
            false,
            "com.libremobileos.freeform",
            "view_freeform"
        )
    }

    fun createWindow(pendingIntent: PendingIntent?, width: Int, height: Int, densityDpi: Int) {
        iLMOFreeformService?.startAppInFreeform(
            pendingIntent?.creatorPackage?:"pendingIntentCreatorPackage",
            "unknownActivity-${Date().time}",
            -100,
            pendingIntent,
            width,
            height,
            densityDpi,
            120.0f,
            false,
            true,
            false,
            "com.libremobileos.freeform",
            "view_freeform"
        )
    }

    fun removeFreeform(freeformId: String) {
        iLMOFreeformService?.removeFreeform(freeformId)
    }
}