package com.petershaan.fungiinfo_mobileapp.customView

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class ResizableVideoView : VideoView {

    private var mVideoWidth: Int = 0
    private var mVideoHeight: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = getDefaultSize(mVideoHeight, heightMeasureSpec)
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mVideoWidth * height > width * mVideoHeight) {
                height = width * mVideoHeight / mVideoWidth
            } else if (mVideoWidth * height < width * mVideoHeight) {
                width = height * mVideoWidth / mVideoHeight
            }
        }
        setMeasuredDimension(width, height)
    }

    fun setVideoSize(width: Int, height: Int) {
        mVideoWidth = width
        mVideoHeight = height
    }
}