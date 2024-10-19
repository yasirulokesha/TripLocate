package app.triplocate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val locationList:ArrayList<Place>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder> ()
{
    var onItemSelect: ((Place)->Unit)? =  null

    class ViewHolder(item:View):RecyclerView.ViewHolder(item){
        val img:ImageView = item.findViewById(R.id.item_img)
        val title:TextView = item.findViewById(R.id.title)
        val shortDescription:TextView = item.findViewById(R.id.short_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_container,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentLocation = locationList[position]
        holder.title.text = currentLocation.title
        holder.shortDescription.text = currentLocation.description
        holder.img.setImageResource(currentLocation.imageResId)

        holder.itemView.setOnClickListener{
            onItemSelect?.invoke(currentLocation)
        }
    }
}