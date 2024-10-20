package app.triplocate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class DetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_view)

        val data = intent.getParcelableExtra<Place>("data")
        if (data!=null){
            val img:ImageView = findViewById(R.id.img_preview)
            val title:TextView = findViewById(R.id.title_detail_view)
            val country:TextView = findViewById(R.id.country_view)

//            img.setImageResource(data.imageResId)
            Picasso.get()
                .load(data.imageResId)
                .into(img)

            title.text = data.title
            country.text = data.country
        }
    }
}