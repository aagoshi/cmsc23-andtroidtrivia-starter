/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding
import com.example.android.navigation.Name
import android.content.Intent

class GameFragment : Fragment() {

    private var numberOfClicks: Int  = 0
    private val infoget: Infoget = Infoget()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Bind this fragment class to the layout
        binding.game = this

        binding.infoget = this.infoget

        binding.nicknameText.text = binding.infoget?.nickname

        /*val nicknameData = intent.getStringExtra("nickname")
        binding.nicknameText.text = nicknameData*/
        setListeners(binding)
        return binding.root
    }

    private fun setListeners(binding: FragmentGameBinding) {

        //binding.nicknameText.text = getText()
        //2D array of textviews
        val clickableViews = arrayOf(arrayOf(binding.lightbox1, binding.lightbox2, binding.lightbox3, binding.lightbox4), arrayOf(binding.lightbox5, binding.lightbox6, binding.lightbox7, binding.lightbox8), arrayOf(binding.lightbox9, binding.lightbox10, binding.lightbox11, binding.lightbox12), arrayOf(binding.lightbox13, binding.lightbox14, binding.lightbox15, binding.lightbox16))

        //changes color if box clicked
        //for each content in 2D array
        for (i in 0..3) {
            for (j in 0..3){
                clickableViews[i][j].setOnClickListener {
                    switch(binding, clickableViews, i, j)
                }
            }
        }

        //retry when retry button clicked
       binding.retryButton.setOnClickListener{retry(clickableViews, binding)}

    }

    private fun makeColored(view: TextView){

        if (view.text == "0"){ //if off turn light on
            view.setBackgroundColor(ResourcesCompat.getColor(view.resources, R.color.boxbg, null) )
            view.text = "1"
        } else { //if on turn off
            view.setBackgroundColor(Color.BLACK)
            view.text = "0"
        }
    }


    private fun switch(binding: FragmentGameBinding, clickableViews: Array<Array<TextView>>, i : Int, j : Int) { //Updates the screen during the game based on user's actions

        //change color of views
        makeColored(clickableViews[i][j])
        if (i != 3) makeColored(clickableViews[i + 1][j])
        if (i != 0) makeColored(clickableViews[i - 1][j])
        if (j != 3) makeColored(clickableViews[i][j + 1])
        if (j != 0) makeColored(clickableViews[i][j - 1])


        //updates number of clicks
        numberOfClicks += 1
        val numClicks = "Clicks: $numberOfClicks"
        binding.clickText.text = numClicks

        if (allOut(clickableViews)){win(binding)}
        if (numberOfClicks >= 5){lose(binding)}
    }

    private fun allOut(clickableViews: Array<Array<TextView>>): Boolean {
        // if at least one light is on return false
        for (item in clickableViews) { //
            for (element in item) if(element.text == "1" ){ return false}
        }
        return true //if all off return true
    }

    private fun win(binding: FragmentGameBinding) { //The player wins
        binding.clickText.setText(R.string.win)
        view?.findNavController()?.navigate(R.id.action_gameFragment_to_gameWonFragment) // ano
    }

    private fun lose(binding: FragmentGameBinding) { //The player wins
        binding.clickText.setText(R.string.game_over)
        view?.findNavController()?.navigate(R.id.action_gameFragment_to_gameOverFragment)
    }

    private fun retry(clickableViews: Array<Array<TextView>>, binding:FragmentGameBinding){ //turn ons all lights/resets board
        for (item in clickableViews){
            for (element in item){
                element.setBackgroundColor(ResourcesCompat.getColor(element.resources, R.color.boxbg, null) )
                element.text = "1"
                numberOfClicks = 0
                val numClicks = "Clicks: $numberOfClicks"
                binding.clickText.text = numClicks
            }
        }
    }
}

//REFERENCES
//how to make 2D array: http://zetcode.com/kotlin/arrays/