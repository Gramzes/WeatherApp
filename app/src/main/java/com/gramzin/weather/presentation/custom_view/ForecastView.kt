package com.gramzin.weather.presentation.custom_view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.os.Parcel
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import com.gramzin.weather.R
import com.gramzin.weather.domain.PartOfDay
import com.gramzin.weather.domain.model.HourlyWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

class ForecastView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private var forecast = listOf<HourlyWeather>()
    private var weatherUiItems = listOf<WeatherUiItem>()

    private val periodSize = resources.getDimension(R.dimen.period_size)
    private val chartHeight = resources.getDimension(R.dimen.chart_height)
    private val tempOffset = resources.getDimension(R.dimen.temp_offset)
    private val iconTopMargin = resources.getDimension(R.dimen.icon_top_margin)
    private val iconSize = resources.getDimension(R.dimen.icon_size)
    private val tempTopMargin = resources.getDimension(R.dimen.temp_top_margin)
    private val tempTextHeight = resources.getDimension(R.dimen.temp_value_text)
    private val timeTopMargin = resources.getDimension(R.dimen.time_top_margin)
    private val timeTextSize = resources.getDimension(R.dimen.time_value_text)
    private val horizontalOffset = resources.getDimension(R.dimen.horizontal_offset)

    private val contentWidth
        get() = (forecast.size - 1).coerceAtLeast(0) * periodSize + horizontalOffset * 2

    private val contentHeight = chartHeight + tempTextHeight + tempOffset + iconTopMargin +
            iconSize + tempTopMargin + timeTopMargin + timeTextSize

    private val path = Path()
    private var transformation = Transformations()

    private val chartPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        pathEffect = CornerPathEffect(periodSize)
        color = Color.WHITE
    }

    private val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val tempPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = tempTextHeight
        color = Color.WHITE
    }

    private val timePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = timeTextSize
        color = Color.WHITE
    }
    private fun Paint.getTextBaselineByCenter(center: Float) = center - (descent() + ascent()) / 2

    private val scroller = Scroller(context, null, true)
    private val scrollAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener {
            if (!scroller.isFinished) {
                scroller.computeScrollOffset()
                Log.d("TEST", "${scroller.currX} ${transformation.translation}")
                setTranslation(-scroller.currX.toFloat())
            } else {
                cancel()
            }
        }
    }

    private val gestureListener = object: GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent): Boolean {
            super.onDown(e)
            return true
        }
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            super.onScroll(e1, e2, distanceX, distanceY)
            if (abs(e2.x - e1.x) > abs(e2.y - e1.y)) {
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            val pointerId = e2.getPointerId(0)
            if (e1.getPointerId(0) == pointerId){
                addTranslation(-distanceX)
            }
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            scroller.fling(
                (-transformation.translation).toInt(),
                0,
                -velocityX.toInt(),
                0,
                0,
                (contentWidth - width).toInt(),
                0,
                0
            )
            scrollAnimator.start()
            return true
        }
    }

    private val gestureDetector = GestureDetector(context, gestureListener, handler)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)

        val width = when(MeasureSpec.getMode(widthMeasureSpec)){
            MeasureSpec.AT_MOST -> contentWidth.coerceAtMost(widthMeasureSpec.toFloat())
            MeasureSpec.EXACTLY -> widthSpecSize
            MeasureSpec.UNSPECIFIED -> contentWidth
            else -> error("")
        }

        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        val height = when(MeasureSpec.getMode(heightMeasureSpec)){
            MeasureSpec.AT_MOST -> contentHeight.coerceAtMost(heightSpecSize.toFloat())
            MeasureSpec.EXACTLY -> heightSpecSize
            MeasureSpec.UNSPECIFIED -> contentHeight
            else -> error("")
        }

        setMeasuredDimension(width.toInt(), height.toInt())
    }


    override fun onDraw(canvas: Canvas) = with(canvas) {
        drawPath(path, chartPaint)
        drawWeatherItems()
    }

    private fun Canvas.drawWeatherItems(){
        weatherUiItems.forEach{ weather ->
            weather.iconBitmap?.let {
                drawBitmap(it, weather.iconLeftX + transformation.translation,
                    weather.iconTopY, iconPaint)
            }
            drawText(weather.tempText, weather.tempLeftX + transformation.translation,
                weather.tempBaseline, tempPaint)

            drawText(weather.timeText, weather.timeLeftX + transformation.translation,
                weather.timeBaseline, timePaint)
        }
    }

    fun setForecast(forecast: List<HourlyWeather>){
        if (this.forecast != forecast) {
            this.forecast = forecast

            weatherUiItems = List(forecast.size) {
                WeatherUiItem(it)
            }
            updateChart(0f)
            requestLayout()
            invalidate()
        }
    }

    private fun updateChart(translation: Float){
        if (forecast.isNotEmpty()) {
            val maxTemp = forecast.maxBy { it.temp }.temp
            val minTemp = forecast.minBy { it.temp }.temp

            path.reset()
            path.moveTo(0f, 0f)
            forecast.forEachIndexed { index, weather ->
                val y = ((maxTemp - weather.temp) / (maxTemp - minTemp).toFloat()) * chartHeight
                if (index == 0) {
                    path.moveTo(0 + translation, y + tempTextHeight + tempOffset)
                }
                weatherUiItems[index].setTempTop(y)
                path.lineTo(index * periodSize + horizontalOffset + translation, y + tempTextHeight + tempOffset)
            }
            val y = ((maxTemp - forecast.last().temp) / (maxTemp - minTemp).toFloat()) * chartHeight
            path.lineTo(forecast.lastIndex * periodSize + 2 * horizontalOffset + translation, y + tempTextHeight + tempOffset)
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun addTranslation(offset: Float){
        Log.d("TEST", "add $offset")
        transformation.addTranslation(offset)
        updateChart(transformation.translation)
        invalidate()
    }

    private fun setTranslation(translation: Float){
        transformation.setTranslation(translation)
        updateChart(transformation.translation)
        invalidate()
    }

    override fun onSaveInstanceState(): Parcelable {
        return SavedState(super.onSaveInstanceState()).also(transformation::onSaveInstanceState)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            transformation.onRestoreInstanceState(state)
            setTranslation(transformation.translation)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private class SavedState : BaseSavedState {
        var translationX: Float = 0f

        // Коснтруктор для сохранения стейта
        constructor(superState: Parcelable?) : super(superState)

        // Коснтруктор для восстановления стейта
        constructor(source: Parcel?) : super(source) {
            source?.apply {
                translationX = readFloat()
            }
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(translationX)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

    private inner class WeatherUiItem(
        private val itemIndex: Int) {

        var iconBitmap: Bitmap? = null
            private set
        var tempText = ""
            private set
        var timeText = ""
            private set

        var iconLeftX = 0f
            private set
        var iconTopY = 0f
            private set

        var tempLeftX = 0f
            private set
        var tempBaseline = 0f
            private set
        var timeLeftX = 0f
            private set
        var timeBaseline = 0f
            private set

        init{
            val weatherItem = forecast[itemIndex]

            val iconResId = when(weatherItem.partOfDay){
                PartOfDay.Day -> weatherItem.weatherType.dayIconResId
                PartOfDay.Night -> weatherItem.weatherType.nightIconResId
            }
            val srcBitmap = BitmapFactory.decodeResource(resources, iconResId)
            val newHeight = (srcBitmap.height / srcBitmap.width.toFloat()) * iconSize
            iconBitmap = Bitmap
                .createScaledBitmap(srcBitmap, iconSize.toInt(), newHeight.toInt(), true)

            tempText = "${weatherItem.temp}°С"
            timeText = dateTimeFormatter.format(Date(weatherItem.time))

            val centerX = itemIndex * periodSize + horizontalOffset
            iconLeftX = centerX - iconSize / 2
            iconTopY = chartHeight + tempTextHeight + tempOffset + iconTopMargin

            tempLeftX = centerX - tempPaint.measureText(tempText) / 2

            timeLeftX = centerX - timePaint.measureText(timeText) / 2
            val timeTopY = iconTopY + iconSize + timeTopMargin
            timeBaseline = tempPaint
                .getTextBaselineByCenter(timeTopY + timeTextSize / 2f)
        }

        fun setTempTop(y: Float){
            tempBaseline = tempPaint.getTextBaselineByCenter(y + tempTextHeight / 2f)
        }
    }

    private inner class Transformations(){
        var translation = 0f
            private set

        fun setTranslation(newTranslation: Float){
            translation = newTranslation.coerceIn(width-contentWidth, 0f)
        }

        fun addTranslation(offset: Float){
            translation = (translation + offset).coerceIn(width-contentWidth, 0f)
        }

        fun onSaveInstanceState(state: SavedState){
            state.translationX = translation
        }

        fun onRestoreInstanceState(state: SavedState){
            translation = state.translationX
        }
    }

    companion object {
        val dateTimeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}