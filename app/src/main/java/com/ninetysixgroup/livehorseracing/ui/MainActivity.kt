package com.ninetysixgroup.livehorseracing.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import com.ninetysixgroup.livehorseracing.R
import com.ninetysixgroup.livehorseracing.api.HorseModel
import com.ninetysixgroup.livehorseracing.api.videoModel
import com.ninetysixgroup.livehorseracing.ui.`interface`.FragmentCommunicator
import com.ninetysixgroup.livehorseracing.ui.fragments.ChannelOne
import com.ninetysixgroup.livehorseracing.ui.fragments.ChannelThree
import com.ninetysixgroup.livehorseracing.ui.fragments.ChannelTwo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_dialog.*
import okhttp3.*
import java.io.IOException
import java.net.Inet4Address
import java.net.NetworkInterface
import java.nio.channels.Channel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, RecyclerAdapter.OnItemClickListener, FragmentCommunicator {

    private lateinit var dialog: AlertDialog.Builder
    private lateinit var dialogAlert: AlertDialog

    lateinit var channelOne: ChannelOne
    lateinit var channelTwo: ChannelTwo
    lateinit var channelThree : ChannelThree

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupDialog()

        var toggle = ActionBarDrawerToggle(this, drawerview, toolbar , R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerview.addDrawerListener(toggle)
        toggle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)

        channelOne = ChannelOne()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, channelOne)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        supportFragmentManager.popBackStackImmediate()

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.channel1 -> {
                channelOne = ChannelOne()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, channelOne)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                supportFragmentManager.popBackStackImmediate()
            }
            R.id.channel2 -> {
                channelTwo = ChannelTwo()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, channelTwo)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                supportFragmentManager.popBackStackImmediate()
            }
            R.id.channel3 -> {
                channelThree = ChannelThree()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, channelThree)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                supportFragmentManager.popBackStackImmediate()
            }
        }
        drawerview.closeDrawer(GravityCompat.START)
        return  true
    }

    override fun onClickItem(position: Int, title: String, channel: String, context: Context) {
//        val ipAddress = getIpv4HostAddress()
//        getStreamChannel(channel,ipAddress, this)
    }

    fun setupDialog(){
        dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.loading_dialog, null)
        dialogView.setBackgroundColor(Color.TRANSPARENT)
        dialog.setView(dialogView)
        dialog.setCancelable(false)

        dialogAlert = dialog.create()
        dialogAlert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onStop() {
        super.onStop()
        if (dialogAlert.isShowing()) {
            dialogAlert.dismiss();
        }
    }

    override fun dialogShow() {
        dialogAlert.show()
    }

}




//    private fun getStreamChannel(channel: String?, userIp: String?, context: Context) {
//        dialogAlert.show()
//
//        val baseURL = "https://3webasketball.com/ninety-six-group-api/public/latest/stream/${channel}/${userIp}/geth5link"
//        val request = Request.Builder().url(baseURL).build()
//        val client = OkHttpClient()
//
//        client.newCall(request).enqueue(object : Callback{
//
//            override fun onResponse(call: Call, response: Response) {
//                val body = response.body?.string()
//
//                val gson = GsonBuilder().create()
//
//                val video = gson.fromJson(body, videoModel::class.java)
//
//                val intent = Intent(context, StreamVideo::class.java).apply {
//                    putExtra("link", "${video.H5LINKROW}")
//                }
//                startActivity(intent)
//
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                println("Failed to request")
//            }
//
//        })
//    }

//    fun getIpv4HostAddress(): String {
//        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
//            networkInterface.inetAddresses?.toList()?.find {
//                !it.isLoopbackAddress && it is Inet4Address
//            }?.let { return it.hostAddress }
//        }
//        return ""
//    }

