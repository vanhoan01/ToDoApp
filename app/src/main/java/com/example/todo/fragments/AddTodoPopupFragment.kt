package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.databinding.FragmentAddTodoPopupBinding
import com.example.todo.databinding.FragmentHomeBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddTodoPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListeners

    fun setListener(listener: DialogNextBtnClickListeners){
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvent()
    }

    private fun registerEvent(){
        binding.todoNextBtn.setOnClickListener{
            var todoTask = binding.todoEt.text.toString().trim()
            if (todoTask.isNotEmpty()){
                listener.onSaveTask(todoTask, binding.todoEt)
            }else{
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener{
            dismiss()
        }
    }

    interface DialogNextBtnClickListeners{
         fun onSaveTask(todo: String, todoEt: TextInputEditText)
    }
}