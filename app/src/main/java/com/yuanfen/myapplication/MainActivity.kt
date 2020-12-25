package com.yuanfen.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rollButton: Button = findViewById(R.id.roll)

        rollButton.setOnClickListener{
            rollDice()
        }
        rollDice()
    }

    private fun rollDice(){
        var array = IntArray(6);
        for(i in 0..5){
            array[i] = (1..6).random();
        }
        var diceImage1 : ImageView = findViewById(R.id.dice1)
        var diceImage2 : ImageView = findViewById(R.id.dice2)
        var diceImage3 : ImageView = findViewById(R.id.dice3)
        var diceImage4 : ImageView = findViewById(R.id.dice4)
        var diceImage5 : ImageView = findViewById(R.id.dice5)
        var diceImage6 : ImageView = findViewById(R.id.dice6)
        change(diceImage1,array[0]);
        change(diceImage2,array[1]);
        change(diceImage3,array[2]);
        change(diceImage4,array[3]);
        change(diceImage5,array[4]);
        change(diceImage6,array[5]);

    }

    private fun change(imageView: ImageView,int: Int){
        val drawableResource = when (int) {
            1 -> R.drawable.dice1
            2 -> R.drawable.dice2
            3 -> R.drawable.dice3
            4 -> R.drawable.dice4
            5 -> R.drawable.dice5
            else -> R.drawable.dice6
        }
        imageView.setImageResource(drawableResource)
    }
}