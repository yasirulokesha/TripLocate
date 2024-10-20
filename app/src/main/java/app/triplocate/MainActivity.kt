package app.triplocate

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddPlace::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        locations = arrayListOf<Place>()
        fetchLocationsFromFirebase()
        updateView(locations)
    }

    private fun fetchLocationsFromFirebase() {
        db.collection("locations")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                locations.clear()
                for (document in snapshots!!) {
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
                    Log.d(TAG, "Successfully imported data from firebase!")
                }
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        updateView(locations)
    }

    private fun updateView(locations:ArrayList<Place>){
        val adapter:RecyclerAdapter = RecyclerAdapter(locations)
        recycler.adapter = adapter

        adapter.onItemSelect = {
            val intent = Intent(this, DetailView::class.java)
            intent.putExtra("data", it)
            startActivity(intent)
        }
    }
}