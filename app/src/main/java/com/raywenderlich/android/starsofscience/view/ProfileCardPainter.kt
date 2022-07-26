package com.raywenderlich.android.starsofscience.view

import android.graphics.*
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.raywenderlich.android.starsofscience.CustomPainter
import com.raywenderlich.android.starsofscience.Painter
import com.raywenderlich.android.starsofscience.R
import com.raywenderlich.android.starsofscience.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class ProfileCardPainter(
    @ColorInt private val color : Int,
    private val avatarRadius: Float,

    private val avatarMargin: Float
): Painter {
    override fun paint(canvas: Canvas) {
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        val shapeBounds = RectFFactory.fromLTWH(0f, 0f, width, height - avatarRadius)

        val paint = Paint()
        paint.color = color

        canvas.drawRect(shapeBounds, paint)

        val centerAvatar = PointF(shapeBounds.centerX(), shapeBounds.bottom)
//2
        val avatarBounds = RectFFactory.fromCircle(center = centerAvatar, radius = avatarRadius).inflate(avatarMargin)

        drawBackGround(canvas, shapeBounds, avatarBounds)

        //1
        val curvedShapeBounds = RectFFactory.fromLTRB(
            shapeBounds.left,
            shapeBounds.top + shapeBounds.height() * 0.35f,
            shapeBounds.right,
            shapeBounds.bottom
        )
//2
        drawCurvedShape(canvas, curvedShapeBounds, avatarBounds)


    }

    private fun drawBackGround(canvas: Canvas, bounds: RectF, avatarBounds: RectF) {
        //1
        val paint = Paint()
        paint.color = color

        //2
        val backgroundPath = Path().apply {
            // 3
            moveTo(bounds.left, bounds.top)
            // 4
            lineTo(bounds.bottomLeft.x, bounds.bottomLeft.y)
            // 5
            lineTo(avatarBounds.centerLeft.x, avatarBounds.centerLeft.y)
            // 6
            arcTo(avatarBounds, -180f, 180f, false)
            // 7
            lineTo(bounds.bottomRight.x, bounds.bottomRight.y)
            // 8
            lineTo(bounds.topRight.x, bounds.topRight.y)
            // 9
            close()
        }
        canvas.drawPath(backgroundPath, paint);

    }

    private fun drawCurvedShape(canvas: Canvas, bounds: RectF, avatarBounds: RectF) {
        //1
        val paint = Paint()
        paint.color = color.darkerShade()

        //2
        val handlePoint = PointF(bounds.left + (bounds.width() * 0.25f), bounds.top)

        //3
        val curvePath = Path().apply {
            //4
            moveTo(bounds.bottomLeft.x, bounds.bottomLeft.y)
            //5
            lineTo(avatarBounds.centerLeft.x, avatarBounds.centerLeft.y)
            //6
            arcTo(avatarBounds, -180f, 180f, false)
            //7
            lineTo(bounds.bottomRight.x, bounds.bottomRight.y)
            //8
            lineTo(bounds.topRight.x, bounds.topRight.y)
            //9
            quadTo(handlePoint.x, handlePoint.y, bounds.bottomLeft.x, bounds.bottomLeft.y)
            //10
            close()
        }
        paint.color = color.darkerShade()

        paint.shader = createGradient(bounds)


        //11
        canvas.drawPath(curvePath, paint)
    }

    private fun createGradient(bounds: RectF): LinearGradient {
        //1
        val colors = intArrayOf(color.darkerShade(), color, color.darkerShade())
        //2
        val stops = floatArrayOf(0.0f, 0.3f, 1.0f)
        //3
        return LinearGradient(
            bounds.centerLeft.x, bounds.centerLeft.y,
            bounds.centerRight.x, bounds.centerRight.y,
            colors,
            stops,
            Shader.TileMode.REPEAT
        )
    }


}