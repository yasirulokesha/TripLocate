package app.triplocate

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class Favourites: AppCompatActivity() {

//    private val favourites: MutableList<Place> = mutableListOf()
//    private val db = FirebaseFirestore.getInstance()
//    private val collectionRef = db.collection("locations")

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.favourites_layout)

    }
}