package com.hcp.hpd_android_demos_kotlin.custom_view.synthesis_score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hcp.hpd_android_demos_kotlin.R

class SynthesisScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synthesis_score)

        val synthesisScoreView = findViewById<SynthesisScoreComponentView>(R.id.synthesisScoreView)
        synthesisScoreView.setAverageScore(5.0f, 6.0f, 5.0f, 4.0f)
        synthesisScoreView.setScore(4.0f, 4.0f, 4.0f, 7.0f)
    }
}