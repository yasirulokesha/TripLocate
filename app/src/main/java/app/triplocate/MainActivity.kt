package app.triplocate

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {

    private lateinit var recycler:RecyclerView
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
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(false)

        locations = arrayListOf<Place>()
        getData()
    }

    private fun getData(){
        for (i in imgList.indices){
            val dataItem = Place(title = titleList[i], description = descriptionList[i], imageResId = imgList[i], country = country[i])
            locations.add(dataItem)
        }
        recycler.adapter = RecyclerAdapter(locations)
    }
}