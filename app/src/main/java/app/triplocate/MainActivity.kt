package app.triplocate

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var recycler:RecyclerView
    private lateinit var locations:ArrayList<Place>
    private val db = FirebaseFirestore.getInstance()
    lateinit var bottomNav: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddPlace::class.java)
            startActivity(intent)
        }

        loadLayout(R.layout.home_layout_)

        recycler = findViewById(R.id.item_view)
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.setHasFixedSize(false)

        locations = arrayListOf<Place>()
        fetchLocationsFromFirebase()
        updateView(locations)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    loadLayout(R.layout.home_layout_)
                    recycler = findViewById(R.id.item_view)
                    recycler.layoutManager = GridLayoutManager(this, 2)
                    recycler.setHasFixedSize(false)
//
                    locations = arrayListOf<Place>()
                    fetchLocationsFromFirebase()
                    updateView(locations)
                    fetchLocationsFromFirebase()
                    updateView(locations)
                    true
                }
                R.id.nav_favorites -> {
                    loadLayout(R.layout.favourites_layout)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    fun loadLayout(layout: Int){
        val container:FrameLayout = findViewById(R.id.container)
        container.removeAllViews()

        val inflater = LayoutInflater.from(this)
        val newLayout = inflater.inflate(layout, container, false)

        container.addView(newLayout)
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
                    val favourite = document.getBoolean("fav") ?: false

                    if (title != null && description != null && imageResId != null && country != null) {
                        val location = Place(title, description, imageResId, country, document.id, favourite)
                        locations.add(location)
                    }

                    updateView(locations)
                    Log.d(TAG, "Successfully imported data from firebase!")
                }
            }
    }


    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
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