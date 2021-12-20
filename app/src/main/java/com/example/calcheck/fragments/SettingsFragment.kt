package com.example.calcheck.fragments

import adapters.SettingsAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.ReceiverCallNotAllowedException
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calcheck.MainActivity

import com.example.calcheck.R
import com.example.calcheck.databse.SqlHelper
import detailFetcher.LoginPage
import detailFetcher.SignUp
import modelClass.ModelClass
import splashScreen.Splash

class SettingsFragment : Fragment() {

    var myActivity : Activity?=null
    lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        recyclerView = view.findViewById(R.id.RecyclerViewSettings)
        activity?.title="Settings"
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myActivity = activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val list = ArrayList<String>()
        list.add("Update Details")
        list.add("Logout")

        var setAdapter = SettingsAdapter(myActivity as Context , list)
        setAdapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(myActivity as Context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = setAdapter

    }
}
