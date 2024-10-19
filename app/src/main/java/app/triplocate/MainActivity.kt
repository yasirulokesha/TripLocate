package app.triplocate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recycler:RecyclerView
    private lateinit var viewAdapter: RecyclerAdapter
    private lateinit var locations:ArrayList<Place>
    lateinit var imgList:Array<Int>
    lateinit var titleList:Array<String>
    lateinit var descriptionList:Array<String>
    lateinit var country:Array<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        imgList = arrayOf(
            R.drawable.thai,
            R.drawable.bg_medium,
            R.drawable.thai,
            R.drawable.bg_medium,
            R.drawable.thai,
            R.drawable.bg_medium,
            R.drawable.thai,
            R.drawable.bg_medium,
            R.drawable.thai,
            R.drawable.bg_medium,
            R.drawable.thai,
            R.drawable.bg_medium,
            R.drawable.thai,
            R.drawable.bg_medium
        )

        titleList = arrayOf(
            "thai",
            "sample",
            "thai",
            "sample","thai",
            "sample","thai",
            "sample","thai",
            "sample","thai",
            "sample","thai",
            "sample"
        )

        descriptionList = arrayOf(
            "sample des",
            "sample des",
            "sample des",
            "sample des","sample des",
            "sample des","sample des",
            "sample des","sample des",
            "sample des","sample des",
            "sample des","sample des",
            "sample des"
        )

        country = arrayOf(
            "Thailand",
            "internet",
            "Thailand",
            "internet","Thailand",
            "internet","Thailand",
            "internet","Thailand",
            "internet","Thailand",
            "internet","Thailand",
            "internet"
        )

        recycler = findViewById(R.id.item_view)
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.setHasFixedSize(false)

        locations = arrayListOf<Place>()
        updateView(locations)



    }

    private fun updateView(locations:ArrayList<Place>){
        for (i in imgList.indices){
            val dataItem = Place(title = titleList[i], description = descriptionList[i], imageResId = imgList[i], country = country[i])
            locations.add(dataItem)
        }

        var adapter:RecyclerAdapter = RecyclerAdapter(locations)
        recycler.adapter = adapter

        adapter.onItemSelect = {
            val intent = Intent(this, DetailView::class.java)
            intent.putExtra("data", it)
            startActivity(intent)
        }


    }
}