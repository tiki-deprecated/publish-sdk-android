package com.mytiki.tiki_sdk_android.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AppOpsManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


enum class Permission(val code: Int) {
    CAMERA(100),
    MICROPHONE(101),
    PHOTO_LIBRARY(102),
    LOCATION_IN_USE(103),
    LOCATION_ALWAYS(104),
    NOTIFICATIONS(105),
    CALENDAR(106),
    CONTACTS(107),
    REMINDERS(108),
    SPEECH_RECOGNITION(109),
    HEALTH(110),
    MEDIA_LIBRARY(111),
    MOTION(112),
    TRACKING(113);

    fun isAuthorized(context: Context): Boolean = when (this) {
        CAMERA -> isPermissionGranted(Manifest.permission.CAMERA, context)
        MICROPHONE -> isPermissionGranted(Manifest.permission.RECORD_AUDIO, context)
        PHOTO_LIBRARY -> isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, context)
        LOCATION_IN_USE, LOCATION_ALWAYS -> isLocationPermissionGranted(context)
        NOTIFICATIONS -> isNotificationPermissionGranted(context)
        CALENDAR -> isPermissionGranted(Manifest.permission.READ_CALENDAR, context)
        CONTACTS -> isPermissionGranted(Manifest.permission.READ_CONTACTS, context)
        REMINDERS -> isPermissionGranted(Manifest.permission.READ_CALENDAR, context)
        SPEECH_RECOGNITION -> isPermissionGranted(Manifest.permission.RECORD_AUDIO, context)
        HEALTH -> isPermissionGranted(Manifest.permission.BODY_SENSORS, context)
        MEDIA_LIBRARY -> isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, context)
        MOTION -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isPermissionGranted(Manifest.permission.ACTIVITY_RECOGNITION, context)
        } else {
            isTrackingPermissionGranted(context)
        }
        TRACKING -> isTrackingPermissionGranted(context)
    }

    fun requestAuth(context: Context, onRequestResult: ((Boolean) -> Unit) = {}) {
        when (this) {
            CAMERA -> requestPermission(context, Manifest.permission.CAMERA, code, onRequestResult)
            MICROPHONE -> requestPermission(
                context,
                Manifest.permission.RECORD_AUDIO,
                code,
                onRequestResult
            )
            PHOTO_LIBRARY -> requestPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                code,
                onRequestResult
            )
            LOCATION_IN_USE -> requestLocationPermission(context, onRequestResult)
            LOCATION_ALWAYS -> requestLocationPermission(context, onRequestResult)
            NOTIFICATIONS -> requestNotificationPermission(context, onRequestResult)
            CALENDAR -> requestPermission(
                context,
                Manifest.permission.READ_CALENDAR,
                code,
                onRequestResult
            )
            CONTACTS -> requestPermission(
                context,
                Manifest.permission.READ_CONTACTS,
                code,
                onRequestResult
            )
            REMINDERS -> requestPermission(
                context,
                Manifest.permission.READ_CALENDAR,
                code,
                onRequestResult
            )
            SPEECH_RECOGNITION -> requestPermission(
                context,
                Manifest.permission.RECORD_AUDIO,
                code,
                onRequestResult
            )
            HEALTH -> requestPermission(
                context,
                Manifest.permission.BODY_SENSORS,
                code,
                onRequestResult
            )
            MEDIA_LIBRARY -> requestPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                code,
                onRequestResult
            )
            MOTION -> requestActivityRecognitionPermission(context, code, onRequestResult)
            TRACKING -> requestTrackingPermission(context, onRequestResult)
        }
    }

    private fun isPermissionGranted(permission: String, context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationPermissionGranted(context: Context): Boolean {
        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
                isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION, context)
                        || isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION, context)
            }
            else -> {
                isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION, context)
                        && isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION, context)
            }
        }
    }

    private fun requestPermission(
        context: Context,
        permission: String,
        requestCode: Int,
        onRequestResult: ((Boolean) -> Unit)
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(permission),
            requestCode
        )
        onRequestResult(false)
    }

    private fun requestLocationPermission(context: Context, onRequestResult: ((Boolean) -> Unit)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                LOCATION_ALWAYS.code
            )
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_IN_USE.code
            )
        }
        onRequestResult(false)
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun isNotificationPermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For Android O and above, check if the app has the permission to show notifications and the notifications are enabled
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.areNotificationsEnabled()
        } else {
            // For Android N and below, check if the app has the permission to show notifications
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            try {
                val method = appOps.javaClass.getDeclaredMethod(
                    "checkOp",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
                val op =
                    method.invoke(appOps, 11, Binder.getCallingUid(), context.packageName) as Int
                op == AppOpsManager.MODE_ALLOWED
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun requestNotificationPermission(
        context: Context,
        onRequestResult: ((Boolean) -> Unit)
    ) {
        val intent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        } else {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo.uid)
        }
        context.startActivity(intent)
        onRequestResult(false)
    }

    private fun isTrackingPermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        } else {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            try {
                @Suppress("DEPRECATION") val mode = appOps.checkOpNoThrow(
                    "android:activity_recognition",
                    Binder.getCallingUid(),
                    context.packageName
                )
                mode == AppOpsManager.MODE_ALLOWED
            } catch (e: Exception) {
                false
            }
        }
    }

    @SuppressLint("AnnotateVersionCheck")
    private fun requestTrackingPermission(context: Context, onRequestResult: ((Boolean) -> Unit)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onRequestResult(false)
        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", context.packageName, null)
            context.startActivity(intent)
            onRequestResult(false)
        }
    }

    fun requestActivityRecognitionPermission(
        context: Context,
        code: Int,
        onRequestResult: (Boolean) -> Unit
    ) {
        val permission = "android.permission.ACTIVITY_RECOGNITION"
        val permissionGranted = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
        if (permissionGranted) {
            onRequestResult(true)
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), code)
            onRequestResult(false)
        }
    }

}
