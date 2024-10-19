package app.triplocate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class DetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_view)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val data = intent.getParcelableExtra<Place>("data")
        if (data!=null){
            val img:ImageView = findViewById(R.id.detailed_img)
            val title:TextView = findViewById(R.id.title_detail_view)
            val country:TextView = findViewById(R.id.country_view)

            img.setImageResource(data.imageResId)
            title.text = data.title
            country.text = data.country
        }
    }
}