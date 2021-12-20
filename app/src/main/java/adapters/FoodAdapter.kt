package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calcheck.R
import org.w3c.dom.Text

class FoodAdapter(context: Context, foodList:ArrayList<String>, quantityList:ArrayList<String>, CalorieList:ArrayList<String>):RecyclerView.Adapter<FoodAdapter.NavViewHolder>(){

    var quantityList:ArrayList<String>?=null
    var foodList:ArrayList<String>?=null
    var CalorieList : ArrayList<String>?=null
    var context:Context?=null

    init{
        this.quantityList = quantityList
        this.foodList = foodList
        this.CalorieList = CalorieList
        this.context = context
    }

    class NavViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var foodText : TextView?=null
        var Calorie : TextView?=null
        var quantity : TextView?=null
        var contentHolder : RelativeLayout?=null
        init{
            foodText = itemView.findViewById(R.id.foodTextID)
            Calorie = itemView.findViewById(R.id.CalorieTextID)
            quantity = itemView.findViewById(R.id.quantityTextID)
            contentHolder = itemView.findViewById(R.id.contentHolder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_custom_food_intake , parent ,false)
        return NavViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return (foodList as ArrayList).size
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.foodText?.setText(foodList?.get(position))
        holder.Calorie?.setText((CalorieList as ArrayList).get(position))
        holder.quantity?.setText((quantityList as ArrayList).get(position))
    }
}