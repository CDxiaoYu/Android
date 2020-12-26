package com.yuanfen.myapplication

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.media.SoundPool
import android.graphics.drawable.AnimationDrawable

class SoundPoolUtil @SuppressLint("NewApi") //这里初始化SoundPool的方法是安卓5.0以后提供的新方式
private constructor(context: Context) {


    private val soundPool: SoundPool
    fun play(number: Int) {

        //播放音频
        soundPool.play(number, 1f, 1f, 0, 0, 1f)
    }

    companion object {
        private var soundPoolUtil: SoundPoolUtil? = null

        //单例模式
        fun getInstance(context: Context): SoundPoolUtil? {
            if (soundPoolUtil == null) soundPoolUtil = SoundPoolUtil(context)
            return soundPoolUtil
        }
    }

    init {
//        soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
        soundPool = SoundPool.Builder().build()
        //加载音频文件
        soundPool.load(context,com.yuanfen.myapplication.R.raw.music, 1)
    }
}