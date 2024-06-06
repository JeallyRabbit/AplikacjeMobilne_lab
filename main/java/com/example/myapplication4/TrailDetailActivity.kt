// path/filename: app/src/main/java/com/example/touristguide/TrailDetailActivity.kt
package com.example.myapplication4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TrailDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trail_detail)

        val trailId = intent.getIntExtra("TRAIL_ID", 0) // Domyślnie 0 lub inna wartość błędu

        if (savedInstanceState == null) {
            val detailFragment = TrailDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("TRAIL_ID", trailId)
                }
            }
            supportFragmentManager.beginTransaction()
                .add(R.id.detail_fragment_container, detailFragment).commit()
        }
    }
}
