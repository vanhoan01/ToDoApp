package com.example.todo.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.ActivityImagesMainBinding
import com.example.todo.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ImagesMainActivity : Fragment() {
    private lateinit var binding: ActivityImagesMainBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFt: FirebaseFirestore
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityImagesMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        registerClickEvents()
    }



    private fun registerClickEvents(){
        binding.uploadBtn.setOnClickListener{
            uploadImage()
        }

        binding.showAllBtn.setOnClickListener{
            startActivity(Intent(context, ImagesActivity::class.java))
        }

        binding.imageView.setOnClickListener{
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){
        imageUri = it
        binding.imageView.setImageURI(it)

    }

    private fun initVars(){
        storageRef = FirebaseStorage.getInstance().reference.child("Images")
        firebaseFt = FirebaseFirestore.getInstance()
    }

    private fun uploadImage(){
        binding.progressBar.visibility = View.VISIBLE
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"]  = uri.toString()

                        firebaseFt.collection("images").add(map).addOnCompleteListener{ firestoreTask ->
                            if (firestoreTask.isSuccessful){

                                Toast.makeText(context, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            binding.progressBar.visibility = View.GONE
                            binding.imageView.setImageResource(R.drawable.vector)
                        }
                    }
                }else{
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    binding.imageView.setImageResource(R.drawable.vector)
                }


            }
        }
    }
}