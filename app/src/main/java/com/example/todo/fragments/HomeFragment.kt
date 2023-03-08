package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.databinding.FragmentSignInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment(), AddTodoPopupFragment.DialogNextBtnClickListeners {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popUpFragment: AddTodoPopupFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvent()
    }

    private fun init(view: View){
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Tasks").child(auth.currentUser?.uid.toString())
    }

    private fun registerEvent(){
        binding.addTaskBtn.setOnClickListener{
            popUpFragment = AddTodoPopupFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddTodoPopupFragment"
            )
        }
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        databaseRef.push().setValue(todo).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context, "Todo saved successfully", Toast.LENGTH_SHORT).show()
                todoEt.text = null
            }else{
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            popUpFragment.dismiss()
        }
    }
}