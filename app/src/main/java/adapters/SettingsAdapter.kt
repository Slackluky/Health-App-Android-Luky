package adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.calcheck.MainActivity
import com.example.calcheck.R
import com.example.calcheck.fragments.SettingsFragment
import com.github.mikephil.charting.components.Description
import detailFetcher.LoginPage
import kotlinx.android.synthetic.main.row_custom_settings.view.*

class SettingsAdapter(context : Context , list:ArrayList<String>) : RecyclerView.Adapter<SettingsAdapter.NavViewHolder>() {
    var context : Context?=null
    var list : ArrayList<String> ? =null

    init{
        this.context = context
        this.list = list
    }

    class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var settingsText : TextView?=null
        var contentHolder : RelativeLayout ? = null
        init{
            settingsText = itemView.findViewById(R.id.textSetting)
            contentHolder = itemView.findViewById(R.id.settingsContentHolderID)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_custom_settings , parent , false)
        return NavViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size as Int
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.settingsText?.setText(list?.get(position))
        holder.contentHolder?.setOnClickListener{
            if(position==0){
                val i = Intent(context, detailFetcher.Description :: class.java)
                context?.startActivity(i)
            }
            if(position == 1){
                val id = LoginPage.Statified.id
                val SPString = id.toString()
                val sp = context!!.getSharedPreferences("Global", Context.MODE_PRIVATE)
                val editor1 : SharedPreferences.Editor = sp.edit()
                editor1.putBoolean("Login",false)
                editor1.putString("id",null)
                editor1.apply()
                editor1.commit()

                val i = Intent(context , LoginPage ::class.java)
                context?.startActivity(i)
                //(context as MainActivity).finish()
            }
        }
    }
}