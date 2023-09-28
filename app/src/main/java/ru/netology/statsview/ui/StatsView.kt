package ru.netology.statsview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.netology.statsview.R
import ru.netology.statsview.utils.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var radius = 0F
    private var center = PointF(0F, 0F)
    private var oval = RectF(0F, 0F, 0F, 0F)
    private var lineWidth = AndroidUtils.dp(context, 5).toFloat()
    private var fontSize = AndroidUtils.dp(context, 20).toFloat()
    private var colors = emptyList<Int>()
    private var emptyColor = 0

    init {
        context.withStyledAttributes(attrs, R.styleable.StatsView) {
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth)
            fontSize = getDimension(R.styleable.StatsView_fontSize, fontSize)
            colors = listOf(
                getColor(R.styleable.StatsView_colors1, randomColor()),
                getColor(R.styleable.StatsView_colors2, randomColor()),
                getColor(R.styleable.StatsView_colors3, randomColor()),
                getColor(R.styleable.StatsView_colors4, randomColor()),
            )
            emptyColor = getColor(R.styleable.StatsView_colorEmpty, randomColor())
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = this@StatsView.fontSize
    }

    var allData = 0F
        set(value) {
            field = value
            invalidate()
        }

    var data: List<Float> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius, center.y - radius,
            center.x + radius, center.y + radius,
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()) {
            return
        }

        var startFrom = -90F
        var textData = 0F
        data.forEachIndexed { index, datum ->
            val pers = datum / allData
            textData += pers
            val angle = 360F * pers
            paint.color = colors.getOrElse(index) { randomColor() }
            canvas.drawArc(oval, startFrom, angle, false, paint)
            startFrom += angle
        }

        if(data.sum() != allData) {
            val empty = (allData-data.sum()) / allData
            val angle = 360F * empty
            paint.color = emptyColor
            canvas.drawArc(oval, startFrom, angle, false, paint)
        }

        paint.color = colors.first()
        canvas.drawPoint(center.x, center.y - radius, paint)

        canvas.drawText(
            "%.2f%%".format(textData * 100),
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint,
        )
    }

    private fun randomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}
