// path/filename: app/src/main/java/com/example/touristguide/TrailDetailFragment.kt
package com.example.myapplication4

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.view.ViewGroup
import android.widget.TextView

class TrailDetailFragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var startStopButton: Button
    private lateinit var resetButton: Button
    private var startTime = 0L
    private var timeInMilliseconds = 0L
    private var timeSwapBuff = 0L
    private var updateTime = 0L
    private var running = false
    private var handler: Handler = Handler()

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            updateTime = timeSwapBuff + timeInMilliseconds
            val secs = (updateTime / 1000).toInt()
            val mins = secs / 60
            val hrs = mins / 60
            val seconds = secs % 60
            val minutes = mins % 60
            val hours = hrs % 24
            timerTextView.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            handler.postDelayed(this, 0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_trail_detail, container, false)

        timerTextView = view.findViewById(R.id.timer_text)
        startStopButton = view.findViewById(R.id.start_stop_button)
        resetButton = view.findViewById(R.id.reset_button)

        startStopButton.setOnClickListener {
            if (running) {
                timeSwapBuff += timeInMilliseconds
                handler.removeCallbacks(runnable)
                startStopButton.text = "Start"
            } else {
                startTime = SystemClock.uptimeMillis()
                handler.postDelayed(runnable, 0)
                startStopButton.text = "Stop"
            }
            running = !running
        }

        resetButton.setOnClickListener {
            startTime = 0L
            timeInMilliseconds = 0L
            timeSwapBuff = 0L
            updateTime = 0L
            timerTextView.text = "00:00:00"
            handler.removeCallbacks(runnable)
            startStopButton.text = "Start"
            running = false
        }

        val trailId = arguments?.getInt("TRAIL_ID") ?: -1
        val detailTextView = view.findViewById<TextView>(R.id.trail_detail_text)

        detailTextView.text = "Szczegóły szlaku dla ID: $trailId"

        return view
    }
}
