package app.triplocate

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddPlace : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var imageUri:Uri

    private val openImagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            imageView.setImageURI(uri)
            imageUri = uri
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_place)

        imageView = findViewById(R.id.img_preview)

        val btnUploadImage:FloatingActionButton = findViewById(R.id.img_upload)
        btnUploadImage.setOnClickListener {
            openImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val saveBtn:Button = findViewById(R.id.save_btn)

        saveBtn.setOnClickListener {
            val title = findViewById<EditText>(R.id.title_input).text.toString()
            val description = findViewById<EditText>(R.id.description_input).text.toString()
            val country = findViewById<EditText>(R.id.country_input).text.toString()

            uploadToFirebaseStorage(imageUri){ url->
                if (url != null) {
                    uploadFirestore(url, title, description, country)
                } else {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun uploadToFirebaseStorage(imageUri: Uri, callback: (String?) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().getReference("locations/${UUID.randomUUID()}")

        imageUri.let {
            storageRef.putFile(it)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        callback(downloadUrl)
                    }.addOnFailureListener { exception ->
                        callback(null)
                        Toast.makeText(this, "Failed to retrieve URL: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    callback(null)
                    Toast.makeText(this, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun uploadFirestore(url:String?, title:String, description:String, country:String){
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show()
        val db = Firebase.firestore
        val location = hashMapOf(
            "title" to title,
            "description" to description,
            "img" to url,
            "country" to country
        )
        db.collection("locations")
            .add(location)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully uploaded to firestore", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed uploaded", Toast.LENGTH_SHORT).show()
                val storageRef = FirebaseStorage.getInstance().getReference(url.toString())
                storageRef.delete()
            }

    }

    private fun ImageId(title:String):String{
        val uuid = UUID.randomUUID().toString() // Generates a unique UUID
        return "${title}_$uuid"
    }

}
