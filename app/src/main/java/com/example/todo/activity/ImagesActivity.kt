package com.example.todo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.databinding.ActivityImagesBinding
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.utils.ImagesAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagesBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFt: FirebaseFirestore
    private var mList = mutableListOf <String>()
    private lateinit var adapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        getImages()
    }

    private fun initVars(){
        storageRef = FirebaseStorage.getInstance().reference.child("Images")
        firebaseFt = FirebaseFirestore.getInstance()

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ImagesAdapter(mList)
        binding.recyclerView.adapter = adapter
    }

    private fun getImages(){
        firebaseFt.collection("images").get().addOnSuccessListener {
            for (i in it){
                mList.add(i.data["pic"].toString())
                adapter.notifyDataSetChanged()
            }
        }
    }
}