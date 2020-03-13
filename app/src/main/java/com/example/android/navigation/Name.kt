package com.example.android.navigation


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentNameBinding
//import com.example.android.navigation.Name as Name::

/**
 * A simple [Fragment] subclass.
 */
class Name : Fragment() {

    private val infoget: Infoget = Infoget()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentNameBinding>(inflater,R.layout.fragment_name,container,false)
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.constraintLayout

        binding.infoget = this.infoget

        //edit nickname
        binding.nicknameEdit.setOnClickListener{
            // Set the focus to the edit text.
            binding.nicknameEdit.visibility = View.VISIBLE
            binding.nicknameText.visibility = View.GONE
            binding.nicknameText.text = binding.nicknameEdit.text.toString()
            // Show the keyboard.
            imm.showSoftInput(binding.nicknameEdit, 0)
        }

        //when the current nickname is clicked, text can be edited
        binding.nicknameText.setOnClickListener{
            // Set the focus to the edit text.
            binding.nicknameEdit.visibility = View.VISIBLE
            binding.nicknameText.visibility = View.GONE
            binding.nicknameText.text = binding.nicknameEdit.text.toString()
            // Show the keyboard.
            imm.showSoftInput(binding.nicknameEdit, 0)
        }

        //when background is clicked, the current name will be shown
        binding.constraintLayout.setOnClickListener{
            binding.nicknameEdit.visibility = View.INVISIBLE
            binding.nicknameText.text = binding.nicknameEdit.text.toString()
            binding.nicknameText.visibility = View.VISIBLE
            // Hide the keyboard.
            imm.hideSoftInputFromWindow(binding.playButton.windowToken, 0)
        }

        binding.playButton.setOnClickListener { //Entered name is final and proceed to the game
            binding.nicknameText.text = binding.nicknameEdit.text.toString()
            binding.infoget?.nickname = binding.nicknameText.text.toString()
            /*val i = Intent(this, GameFragment::class.java)

            val myString = binding.nicknameText.text.toString()
            i.putExtra("nickname", myString)
            startActivity(i)*/
            view?.findNavController()?.navigate(R.id.action_name3_to_gameFragment)
            // Hide the keyboard.
            imm.hideSoftInputFromWindow(binding.playButton.windowToken, 0)
        }

        return binding.root
    }


//REFERENCES
    //https://www.techotopia.com/index.php/Android_Explicit_Intents_%E2%80%93_A_Kotlin_Example
}
