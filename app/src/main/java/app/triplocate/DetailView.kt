package app.triplocate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class DetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_view)

        val backBtn = findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val data = intent.getParcelableExtra<Place>("data")
        if (data!=null){
            val img:ImageView = findViewById(R.id.img_preview)
            val title:TextView = findViewById(R.id.title_detail_view)
            val description:TextView = findViewById(R.id.description_details)
            val country:TextView = findViewById(R.id.country_view)
            val favBtn:Button = findViewById(R.id.fav_btn)

            Picasso.get()
                .load(data.imageResId)
                .into(img)

            title.text = data.title
            country.text = data.country
            description.text = data.description
            favBtn.setOnClickListener {
                data.fav = !data.fav
                if (data.fav) {
                    favBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_favorite_24, 0, 0)  // Filled heart
                } else {
                    favBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_favorite_border_24, 0, 0)  // Outlined heart
                }
            }
        }

        val deleteBtn = findViewById<Button>(R.id.delete_btn)
        deleteBtn.setOnClickListener {
            val storageRef = FirebaseStorage.getInstance().getReference("locations/${data!!.imageResId}")
            storageRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully image deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete image", Toast.LENGTH_SHORT).show()
                }
            val db = Firebase.firestore
            db.collection("locations").document(data.id).delete()
                .addOnSuccessListener {
                Toast.makeText(this, "Successfully deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
                }
        }

    }
}