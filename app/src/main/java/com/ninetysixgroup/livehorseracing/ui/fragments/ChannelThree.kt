package com.ninetysixgroup.livehorseracing.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.ninetysixgroup.livehorseracing.R
import com.ninetysixgroup.livehorseracing.api.HorseModel
import com.ninetysixgroup.livehorseracing.api.videoModel
import com.ninetysixgroup.livehorseracing.ui.RecyclerAdapter
import com.ninetysixgroup.livehorseracing.ui.StreamVideo
import com.ninetysixgroup.livehorseracing.ui.`interface`.FragmentCommunicator
import kotlinx.android.synthetic.main.fragment_channel_one.*
import okhttp3.*
import java.io.IOException
import java.net.Inet4Address
import java.net.NetworkInterface


class ChannelThree : Fragment(), RecyclerAdapter.OnItemClickListener {

    private lateinit var communicator: FragmentCommunicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel_three, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        communicator = activity as FragmentCommunicator

        home_recycler.layoutManager = GridLayoutManager(
            activity,
            2,
            LinearLayoutManager.VERTICAL,
            false)
        home_recycler.adapter = activity?.let {
            RecyclerAdapter(it, getAllRacing(), 0 , this)
        }
    }

    private fun getAllRacing(): ArrayList<HorseModel> {
        val imgs = getResources().obtainTypedArray(R.array.array_horse_racing3)
        val title = resources.getStringArray(R.array.array_title3)
        val dateR = resources.getStringArray(R.array.array_date3)
        val timeR = resources.getStringArray(R.array.array_time3)
        val id = resources.getStringArray(R.array.array_channel3)
        val list = ArrayList<HorseModel>()
        for (i in 0 until imgs.length()){
            list.add(HorseModel(id[i],title[i],title[i],dateR[i],timeR[i],imgs.getResourceId(i, -1),0))
        }

        return list
    }

    override fun onClickItem(position: Int, title: String, channel: String,context: Context) {
        val ipAddress = getIpv4HostAddress()
        getStreamChannel(channel,ipAddress, context)
    }

    private fun getStreamChannel(channel: String?, userIp: String?, context: Context) {
        //show the loading screen in main activity
        communicator.dialogShow()

        val baseURL = "https://3webasketball.com/ninety-six-group-api/public/latest/stream/${channel}/${userIp}/geth5link"
        val request = Request.Builder().url(baseURL).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val video = gson.fromJson(body, videoModel::class.java)

                val intent = Intent(context, StreamVideo::class.java).apply {
                    putExtra("link", "${video.H5LINKROW}")
                }
                startActivity(intent)

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to request")
            }

        })
    }
    fun getIpv4HostAddress(): String {
        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
            networkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let { return it.hostAddress }
        }
        return ""
    }
}