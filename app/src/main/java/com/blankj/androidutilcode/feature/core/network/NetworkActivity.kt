package com.blankj.androidutilcode.feature.core.network

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.subutil.util.ThreadPoolUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Network 工具类 Demo
</pre> *
 */
class NetworkActivity : BaseBackActivity() {

    internal lateinit var tvAboutNetwork: TextView
    internal lateinit var tvAboutNetworkAsync: TextView
    internal var threadPoolUtils = ThreadPoolUtils(ThreadPoolUtils.SingleThread, 1)

    internal var mHandler = Handler(Handler.Callback { msg ->
        var text = tvAboutNetworkAsync.text.toString()
        if (text.length != 0) {
            text += '\n'.toString()
        }
        if (msg.what == 1) {
            tvAboutNetworkAsync.text = SpanUtils()
                    .append(text + "isAvailableByPing: " + msg.obj)
                    .create()
        } else {
            tvAboutNetworkAsync.text = SpanUtils()
                    .append(text + "getDomainAddress: " + msg.obj)
                    .create()
        }
        true
    })

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_network
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_network)

        tvAboutNetwork = findViewById(R.id.tv_about_network)
        tvAboutNetworkAsync = findViewById(R.id.tv_about_network_async)
        findViewById<View>(R.id.btn_open_wireless_settings).setOnClickListener(this)
        findViewById<View>(R.id.btn_set_wifi_enabled).setOnClickListener(this)
        setAboutNetwork()
    }

    override fun doBusiness() {
        threadPoolUtils.execute(Runnable {
            val msg = Message.obtain()
            msg.what = 1
            msg.obj = NetworkUtils.isAvailableByPing
            mHandler.sendMessage(msg)
        })

        threadPoolUtils.execute(Runnable {
            val msg = Message.obtain()
            msg.what = 2
            msg.obj = NetworkUtils.getDomainAddress("baidu.com")
            mHandler.sendMessage(msg)
        })
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_open_wireless_settings -> NetworkUtils.openWirelessSettings()
            R.id.btn_set_data_enabled -> NetworkUtils.mobileDataEnabled = !NetworkUtils.mobileDataEnabled
            R.id.btn_set_wifi_enabled -> NetworkUtils.wifiEnabled = !NetworkUtils.wifiEnabled
        }
        setAboutNetwork()
    }

    private fun setAboutNetwork() {
        tvAboutNetwork.text = SpanUtils()
                .appendLine("isConnected: " + NetworkUtils.isConnected)
                .appendLine("getMobileDataEnabled: " + NetworkUtils.mobileDataEnabled)
                .appendLine("isMobileData: " + NetworkUtils.isMobileData)
                .appendLine("is4G: " + NetworkUtils.is4G)
                .appendLine("getWifiEnabled: " + NetworkUtils.wifiEnabled)
                .appendLine("isWifiConnected: " + NetworkUtils.isWifiConnected)
                .appendLine("isWifiAvailable: " + NetworkUtils.isWifiAvailable)
                .appendLine("getNetworkOperatorName: " + NetworkUtils.networkOperatorName!!)
                .appendLine("getNetworkTypeName: " + NetworkUtils.networkType)
                .append("getIPAddress: " + NetworkUtils.getIPAddress(true)!!)
                .create()
    }

    override fun onDestroy() {
        threadPoolUtils.shutDownNow()
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, NetworkActivity::class.java)
            context.startActivity(starter)
        }
    }
}
