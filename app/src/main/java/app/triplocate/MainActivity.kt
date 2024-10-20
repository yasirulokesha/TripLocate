package app.triplocate

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var recycler:RecyclerView
    private lateinit var locations:ArrayList<Place>
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)
        supportActionBar?.hide()

        recycler = findViewById(R.id.item_view)
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.setHasFixedSize(false)

        locations = arrayListOf<Place>()
        fetchLocationsFromFirebase()

        updateView(locations)



    }

    private fun fetchLocationsFromFirebase() {
        db.collection("locations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val title = document.getString("title")
                    val description = document.getString("description")
                    val imageResId = document.getString("img")
                    val country = document.getString("country")

                    if (title != null && description != null && imageResId != null && country != null) {
                        val location = Place(title, description, imageResId, country)
                        locations.add(location)
                    }
                    updateView(locations)
                    Toast.makeText(this, "Successfully imported doc", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }



    private fun updateView(locations:ArrayList<Place>){
//        for (i in locations.indices){
//            val dataItem = Place(title = titleList[i], description = descriptionList[i], imageResId = imgList[i], country = country[i])
//            locations.add(dataItem)
//        }

        val adapter:RecyclerAdapter = RecyclerAdapter(locations)
        recycler.adapter = adapter

        adapter.onItemSelect = {
            val intent = Intent(this, DetailView::class.java)
            intent.putExtra("data", it)
            startActivity(intent)
        }

    }
}