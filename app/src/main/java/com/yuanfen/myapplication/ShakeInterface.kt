package com.yuanfen.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.FloatMath
import java.lang.Math.sqrt
import kotlin.math.sqrt


class ShakeInterface(context: Context) : SensorEventListener {
    //检测摇动相关变量
    private var initTime: Long = 0
    private var lastTime: Long = 0
    private var curTime: Long = 0
    private var duration: Long = 0

    /**
     * 上次检测时，各分量
     */
    private var last_x = 0.0f
    private var last_y = 0.0f
    private var last_z = 0.0f

    /**
     * 本次晃动值
     */
    private var shake = 0.0f

    /**
     * 控制时间间隔
     */
    var timeInterval = 100

    /**
     * 晃动阀值
     */
    var shakeThreshold = 3000
    var isRecoding = false
    private val mSensorManager: SensorManager?
    private val mListeners: ArrayList<OnShakeListener>

    /**
     * 定义摇晃发生时的事件处理接口，需实现onShake方法
     */
    interface OnShakeListener {
        /**
         * 当手机摇晃时被调用
         */
        fun onShake()
    }

    /**
     * 注册OnShakeListener，当摇晃时接收通知
     *
     * @param listener
     */
    fun registerOnShakeListener(listener: OnShakeListener) {
        if (mListeners.contains(listener)) return
        mListeners.add(listener)
    }

    /**
     * 移除已经注册的OnShakeListener
     *
     * @param listener
     */
    fun unregisterOnShakeListener(listener: OnShakeListener) {
        mListeners.remove(listener)
    }

    /**
     * 启动摇晃检测
     */
    fun start() {
        if (mSensorManager == null) {
            throw UnsupportedOperationException()
        }
        val sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            ?: throw UnsupportedOperationException()
        val success = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        if (!success) {
            throw UnsupportedOperationException()
        } else {
            println("注册成功")
        }
    }

    /**
     * 停止摇晃检测
     */
    fun stop() {
        mSensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //什么也不干
        println("精度发生变化")
    }

    //传感器数据变动事件
    override fun onSensorChanged(event: SensorEvent) {

        //如果没有开始录音的话可以监听是否有摇动事件，如果有摇动事件可以开始录音
        //获取加速度传感器的三个参数
        val x = event.values[SensorManager.DATA_X]
        val y = event.values[SensorManager.DATA_Y]
        val z = event.values[SensorManager.DATA_Z]
        //获取当前时刻的毫秒数
        curTime = System.currentTimeMillis()
        if (!isRecoding) {
            //100毫秒检测一次
            //System.out.println("开始变化,curtime:" + curTime +"lasttime:" + lastTime);
            if (curTime - lastTime > timeInterval) {
                duration = curTime - lastTime
                // 看是不是刚开始晃动
                if (last_x == 0.0f && last_y == 0.0f && last_z == 0.0f) {
                    //last_x、last_y、last_z同时为0时，表示刚刚开始记录
                    initTime = System.currentTimeMillis()
                } else {
                    //精确算法，各方向差值平方和开平方,单次晃动幅度
                    shake = sqrt((x - last_x) * (x - last_x) + (y - last_y) * (y - last_y) + (z - last_z) * (z - last_z)) / duration * 10000
                }
                println(shake)
                if (shake >= shakeThreshold) {
                    //此处开始执行
                    notifyListeners()
                }
                last_x = x
                last_y = y
                last_z = z
                lastTime = curTime
            }
        }
    }

    /**
     * 当摇晃事件发生时，通知所有的listener
     */
    private fun notifyListeners() {
        for (listener in mListeners) {
            println("你执行了？")
            isRecoding = true
            listener.onShake()
        }
    }

    init {
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mListeners = ArrayList()
    }
}
