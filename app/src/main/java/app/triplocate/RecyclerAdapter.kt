package app.triplocate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter(private val locationList:ArrayList<Place>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder> ()
{
//    Storing when select an item
    var onItemSelect: ((Place)->Unit)? =  null

//    Referencing a holder for each item
    class ViewHolder(item:View):RecyclerView.ViewHolder(item){
        val img:ImageView = item.findViewById(R.id.item_img)
        val title:TextView = item.findViewById(R.id.title)
        val country:TextView = item.findViewById(R.id.country)
    }

//    Creating items for each
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_container,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

//    Make reference for each item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentLocation = locationList[position]
        holder.title.text = currentLocation.title
        holder.country.text = currentLocation.country


        Picasso.get()
            .load(currentLocation.imageResId)
            .into(holder.img)

        holder.itemView.setOnClickListener{
            onItemSelect?.invoke(currentLocation)
        }
    }
}
