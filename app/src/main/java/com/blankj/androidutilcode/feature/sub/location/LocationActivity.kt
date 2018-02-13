package com.blankj.androidutilcode.feature.sub.location

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Location 工具类 Demo
</pre> *
 */
class LocationActivity : BaseBackActivity() {

    internal lateinit var tvAboutLocation: TextView
    internal lateinit var mLocationService: LocationService

    internal var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {

        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mLocationService = (service as LocationService.LocationBinder).service
            mLocationService.setOnGetLocationListener(object : LocationService.OnGetLocationListener {
                override fun getLocation(lastLatitude: String, lastLongitude: String, latitude: String, longitude: String, country: String, locality: String, street: String) {
                    runOnUiThread {
                        tvAboutLocation.text = SpanUtils()
                                .appendLine("lastLatitude: " + lastLatitude)
                                .appendLine("lastLongitude: " + lastLongitude)
                                .appendLine("latitude: " + latitude)
                                .appendLine("longitude: " + longitude)
                                .appendLine("getCountryName: " + country)
                                .appendLine("getLocality: " + locality)
                                .appendLine("getStreet: " + street)
                                .create()
                    }
                }
            })

        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_location
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_location)

        tvAboutLocation = findViewById(R.id.tv_about_location)
        tvAboutLocation.text = SpanUtils()
                .appendLine("lastLatitude: unknown")
                .appendLine("lastLongitude: unknown")
                .appendLine("latitude: unknown")
                .appendLine("longitude: unknown")
                .appendLine("getCountryName: unknown")
                .appendLine("getLocality: unknown")
                .appendLine("getStreet: unknown")
                .create()
    }

    override fun doBusiness() {
        bindService(Intent(this, LocationService::class.java), conn, Context.BIND_AUTO_CREATE)
    }

    override fun onWidgetClick(view: View) {

    }

    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, LocationActivity::class.java)
            context.startActivity(starter)
        }
    }
}
