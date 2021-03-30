package com.hcp.hpd_android_demos_kotlin.custom_view.synthesis_score

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout

class SynthesisScoreView : FrameLayout {

    private lateinit var paint: Paint

    private var mWidth: Float = 0.0f
    private var mHeight: Float = 0.0f
    private var maxScore: Float = 9.0f

    // 图形背景线条颜色
    private var backViewLineColor: Int = Color.parseColor("#82a0f4")

    // 平均分线条颜色
    private var averageScoreLineColor: Int = Color.parseColor("#ffffff")

    // 平均分填充颜色
    private var averageScoreFillColor: Int = Color.parseColor("#ffffff")

    // 真实分线条颜色
    private var scoreLineColor: Int = Color.parseColor("#FF3366")

    // 真实分填充颜色
    private var scoreFillColor: Int = Color.parseColor("#FF3366")

    private var hasAverageScore = false
    private var averageScoreLeft: Float = 0.0f
    private var averageScoreTop: Float = 0.0f
    private var averageScoreRight: Float = 0.0f
    private var averageScoreBottom: Float = 0.0f

    private var hasScore = false
    private var scoreLeft: Float = 0.0f
    private var scoreTop: Float = 0.0f
    private var scoreRight: Float = 0.0f
    private var scoreBottom: Float = 0.0f

    companion object {
        private const val TAG = "SynthesisScoreView"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.TRANSPARENT)
        paint = Paint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        Log.i(TAG, "onSizeChanged: w=$w,h=$h,ow=$oldw,oh=$oldh")
        mWidth = w.toFloat()
        mHeight = h.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.reset()
        paint.reset()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 9.0f
        paint.color = backViewLineColor
        val path = Path()
        path.moveTo(0.0f, mWidth / 2)
        path.lineTo(mWidth / 2, 0.0f)
        path.lineTo(mWidth, mWidth / 2)
        path.lineTo(mWidth / 2, mWidth)
        path.lineTo(0.0f, mWidth / 2)
        canvas?.drawPath(path, paint)

        paint.reset()
        path.reset()
        paint.strokeWidth = 4.0f
        paint.style = Paint.Style.STROKE
        paint.color = backViewLineColor
        path.moveTo(0.0f, mWidth / 2)
        path.lineTo(mWidth, mWidth / 2)
        path.moveTo(mWidth / 2, 0.0f)
        path.lineTo(mWidth / 2, mWidth)
        canvas?.drawPath(path, paint)

        path.reset()
        for (i in 1..3) {
            path.reset()
            paint.reset()
            paint.strokeWidth = 5.0f
            paint.style = Paint.Style.STROKE
            paint.color = backViewLineColor
            paint.pathEffect = DashPathEffect(floatArrayOf(15.0f, 15.0f), 0.0f)
            val addition = i * 2 * mWidth / (maxScore * 2)
            path.moveTo(0 + addition, mWidth / 2)
            path.lineTo(mWidth / 2, addition)
            path.lineTo(mWidth - addition, mWidth / 2)
            path.lineTo(mWidth / 2, mWidth - addition)
            path.lineTo(addition, mWidth / 2)
            canvas?.drawPath(path, paint)
        }

        if (hasAverageScore) {

            // 填充
            path.reset()
            paint.reset()
            paint.strokeWidth = 0.0f
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.color = averageScoreLineColor
            paint.alpha = 77

            val space = mWidth / (maxScore * 2)
            val leftX = (maxScore - averageScoreLeft) * space
            val topY = (maxScore - averageScoreTop) * space
            val rightX = mWidth - (maxScore - averageScoreRight) * space
            val bottomY = mWidth - (maxScore - averageScoreBottom) * space

            path.moveTo(leftX, mWidth / 2)
            path.lineTo(mWidth / 2, topY)
            path.lineTo(rightX, mWidth / 2)
            path.lineTo(mWidth / 2, bottomY)
            path.lineTo(leftX, mWidth / 2)

            canvas?.drawPath(path, paint)

            // 边框
            path.reset()
            paint.reset()
            paint.strokeWidth = 3.0f
            paint.style = Paint.Style.STROKE
            paint.color = averageScoreLineColor

            path.moveTo(leftX, mWidth / 2)
            path.lineTo(mWidth / 2, topY)
            path.lineTo(rightX, mWidth / 2)
            path.lineTo(mWidth / 2, bottomY)
            path.lineTo(leftX, mWidth / 2)

            canvas?.drawPath(path, paint)
        }

        if (hasScore) {

            // 填充
            path.reset()
            paint.reset()
            paint.strokeWidth = 0.0f
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.color = scoreLineColor
            paint.alpha = 77

            val space = mWidth / (maxScore * 2)
            val leftX = (maxScore - scoreLeft) * space
            val topY = (maxScore - scoreTop) * space
            val rightX = mWidth - (maxScore - scoreRight) * space
            val bottomY = mWidth - (maxScore - scoreBottom) * space

            path.moveTo(leftX, mWidth / 2)
            path.lineTo(mWidth / 2, topY)
            path.lineTo(rightX, mWidth / 2)
            path.lineTo(mWidth / 2, bottomY)
            path.lineTo(leftX, mWidth / 2)

            canvas?.drawPath(path, paint)

            // 边框
            path.reset()
            paint.reset()
            paint.strokeWidth = 3.0f
            paint.style = Paint.Style.STROKE
            paint.color = scoreLineColor

            path.moveTo(leftX, mWidth / 2)
            path.lineTo(mWidth / 2, topY)
            path.lineTo(rightX, mWidth / 2)
            path.lineTo(mWidth / 2, bottomY)
            path.lineTo(leftX, mWidth / 2)

            canvas?.drawPath(path, paint)
        }
    }

    fun setAverageScore(left: Float, top: Float, right: Float, bottom: Float) {

        averageScoreLeft = left
        averageScoreTop = top
        averageScoreRight = right
        averageScoreBottom = bottom

        hasAverageScore = true
        invalidate()
    }

    fun setScore(left: Float, top: Float, right: Float, bottom: Float) {

        scoreLeft = left
        scoreTop = top
        scoreRight = right
        scoreBottom = bottom

        hasScore = true
        invalidate()
    }
}