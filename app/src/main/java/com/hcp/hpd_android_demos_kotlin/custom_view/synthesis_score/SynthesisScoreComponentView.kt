package com.hcp.hpd_android_demos_kotlin.custom_view.synthesis_score

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.hcp.hpd_android_demos_kotlin.R

public class SynthesisScoreComponentView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var leftTextView: TextView
    private lateinit var topTextView: TextView
    private lateinit var rightTextView: TextView
    private lateinit var bottomTextView: TextView
    private lateinit var synthesisScoreView: SynthesisScoreView

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.synthesis_score_component_view, this, true)

        leftTextView = view.findViewById(R.id.tv_left)
        topTextView = view.findViewById(R.id.tv_top)
        rightTextView = view.findViewById(R.id.tv_right)
        bottomTextView = view.findViewById(R.id.tv_bottom)
        synthesisScoreView = view.findViewById(R.id.synthesis_scoreView)
    }

    fun setAverageScore(left: Float, top: Float, right: Float, bottom: Float) {
        synthesisScoreView.setAverageScore(left, top, right, bottom)
    }

    fun setScore(left: Float, top: Float, right: Float, bottom: Float) {
        leftTextView.text = "发音\nPronunciation\n${left}分"
        topTextView.text = "语法多样性及准确性\nGrammatical Range & Accuracy\n${top}分"
        rightTextView.text = "词汇多样性\nLexical Resource\n${right}分"
        bottomTextView.text = "流利性与连贯性\nFluency & Coherence\n${bottom}分"
        synthesisScoreView.setScore(left, top, right, bottom)
    }
}