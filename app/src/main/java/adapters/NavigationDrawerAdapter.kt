package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calcheck.MainActivity
import com.example.calcheck.R
import com.example.calcheck.fragments.*

class NavigationDrawerAdapter(contentList : ArrayList<String> , getImages:IntArray , context: Context):RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>(){

    var contentList:ArrayList<String>?=null
    var getImages : IntArray?=null
    var context : Context?=null

    init{
        this.contentList = contentList
        this.getImages =getImages
        this.context = context
    }

    class NavViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var iconGet : ImageView ? =null
        var textGet : TextView? =null
        var contentHolder : RelativeLayout?=null
        init{
            iconGet = itemView.findViewById(R.id.icon_navdrawer)
            textGet = itemView.findViewById(R.id.text_navdrawer)
            contentHolder = itemView.findViewById(R.id.navdrawer_item_content_holder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_custom_navigationdrawer , parent , false)
        return NavViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return (contentList as ArrayList).size
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.iconGet?.setBackgroundResource(getImages?.get(position) as Int)
        holder.textGet?.setText(contentList?.get(position))
        holder.contentHolder?.setOnClickListener{
            if(position==0){
             val mainScreenFrag = MainScreenFrag()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailsFragment , mainScreenFrag)
                    .commit()

            }
            if(position==1){
                val trackIntakeFragment =
                    TrackIntakeFragment()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailsFragment , trackIntakeFragment)
                    .commit()
            }
            if(position==2){
                val stepCounterFrag =
                    StepTrackerFragment()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailsFragment , stepCounterFrag)
                    .commit()
            }
            if(position==3){
                val settingsFrag =
                    SettingsFragment()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detailsFragment , settingsFrag)
                    .commit()
            }
            if(position==4){
                val aboutUsFrag = AboutUsFrag()
                    (context as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.detailsFragment , aboutUsFrag)
                        .commit()
            }
            MainActivity.Statified.drawerLayout?.closeDrawers()
        }
    }
}