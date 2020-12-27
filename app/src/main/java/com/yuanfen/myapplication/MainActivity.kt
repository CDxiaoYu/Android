package com.yuanfen.myapplication

import android.graphics.drawable.AnimationDrawable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var open: Button = findViewById(R.id.open)
        var instance = SoundPoolUtil.getInstance(this)

        //初始化色盅摇动功能
        var box: ImageView = findViewById(R.id.box)
        box.setBackgroundResource(R.drawable.roll)
        var animaition : AnimationDrawable = box.getBackground() as AnimationDrawable;

        //使初始有background
        animaition.start();
        animaition.stop();

        var rollButton: Button = findViewById(R.id.roll)

        //点击roll事件
        rollButton.setOnClickListener{

            roll();
//            rollButton.setClickable(false)
//            open.setClickable(false)
//            box.setVisibility(View.VISIBLE);
//            //进入生成随机功能
//
//            rollDice();
//            if (instance != null) {
//                instance.play(1)
//
//            };
//            //摇动色子
//            animaition.start();
//            //延迟使色子停止
//            var handler = Handler();
//            handler.postDelayed({
//                animaition.stop()
//                rollButton.setClickable(true)
//                open.setClickable(true)
//                                },1900)


        }


        open.setOnClickListener{
            if (box.isShown()) {
                box.setVisibility(View.INVISIBLE);
                open.setText("关");
            } else {
                box.setVisibility(View.VISIBLE);
                open.setText("开");
            }
        }



        var shakeinterface :   ShakeInterface


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


    class ShakeInterface : com.yuanfen.myapplication.ShakeInterface.OnShakeListener {
        override fun onShake() {

        }
    }

    private fun roll(){
        var rollButton: Button = findViewById(R.id.roll)
        var open: Button = findViewById(R.id.open)
        var box: ImageView = findViewById(R.id.box)
        var instance = SoundPoolUtil.getInstance(this)
        var animaition : AnimationDrawable = box.getBackground() as AnimationDrawable;

        rollButton.setClickable(false)
        open.setClickable(false)
        box.setVisibility(View.VISIBLE);
        //进入生成随机功能

        rollDice();
        if (instance != null) {
            instance.play(1)

        };
        //摇动色子
        animaition.start();
        //延迟使色子停止
        var handler = Handler();
        handler.postDelayed({
            animaition.stop()
            rollButton.setClickable(true)
            open.setClickable(true)
        },1900)

    }


}


