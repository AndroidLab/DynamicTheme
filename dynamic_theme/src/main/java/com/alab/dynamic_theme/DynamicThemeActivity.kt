package com.alab.dynamic_theme

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt


/**
 * Represents the basic activity for a dynamic theme.
 */
abstract class DynamicThemeActivity<T : DynamicTheme> : AppCompatActivity() {
    private lateinit var root: View
    private lateinit var frontFakeThemeImageView: SimpleImageView
    private lateinit var behindFakeThemeImageView: SimpleImageView
    private lateinit var decorView: FrameLayout

    private var anim: Animator? = null
    private var themeAnimationListener: IThemeAnimationListener? = null


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initViews()
        super.setContentView(root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        super.setContentView(root)
    }

    override fun onResume() {
        super.onResume()
        syncTheme(DynamicThemeManager.getInstance<T>().getCurrentTheme())
        setStatusBarBackgroundColor()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, decorView, false))
    }

    override fun setContentView(view: View?) {
        decorView.removeAllViews()
        decorView.addView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        decorView.removeAllViews()
        decorView.addView(view, params)
    }

    private fun initViews() {
        root = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            addView(SimpleImageView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                visibility = View.GONE
                behindFakeThemeImageView = this
            })

            addView(FrameLayout(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                decorView = this
                id = DynamicThemeManager.ROOT_ID
            })

            addView(SimpleImageView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                visibility = View.GONE
                frontFakeThemeImageView = this
            })
        }
    }


    fun changeTheme(
        sourceCoordinate: Coordinate,
        animDuration: Long = 600,
        isReverse: Boolean = false
    ) {
        if (frontFakeThemeImageView.visibility == View.VISIBLE ||
            behindFakeThemeImageView.visibility == View.VISIBLE ||
            isRunningChangeThemeAnimation()
        ) {
            return
        }

        val w = decorView.measuredWidth
        val h = decorView.measuredHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        decorView.draw(canvas)

        val newThemeType =
            if (DynamicThemeManager.getInstance<T>()
                    .getCurrentTheme().type == DynamicThemeType.LIGHT
            )
                DynamicThemeType.DARK
            else
                DynamicThemeType.LIGHT

        val newTheme = when (newThemeType) {
            DynamicThemeType.LIGHT -> DynamicThemeManager.getInstance<T>().lightDarkThemes.first
            DynamicThemeType.DARK -> DynamicThemeManager.getInstance<T>().lightDarkThemes.second
        }

        DynamicThemeManager.getInstance<T>().themePreferences.dynamicThemeType = newTheme.type
        DynamicThemeManager.getInstance<T>().setCurrentTheme(newTheme)

        syncTheme(newTheme)
        setStatusBarBackgroundColor()

        val finalRadius = sqrt((w * w + h * h).toDouble()).toFloat()
        if (isReverse) {
            frontFakeThemeImageView.bitmap = bitmap
            frontFakeThemeImageView.visibility = View.VISIBLE
            anim = ViewAnimationUtils.createCircularReveal(
                frontFakeThemeImageView,
                sourceCoordinate.x,
                sourceCoordinate.y,
                finalRadius,
                0f
            )
        } else {
            behindFakeThemeImageView.bitmap = bitmap
            behindFakeThemeImageView.visibility = View.VISIBLE
            anim = ViewAnimationUtils.createCircularReveal(
                decorView,
                sourceCoordinate.x,
                sourceCoordinate.y,
                0f,
                finalRadius
            )
        }

        anim?.duration = animDuration

        anim?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                themeAnimationListener?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                behindFakeThemeImageView.bitmap = null
                frontFakeThemeImageView.bitmap = null
                frontFakeThemeImageView.visibility = View.GONE
                behindFakeThemeImageView.visibility = View.GONE
                themeAnimationListener?.onAnimationEnd(animation)
            }

            override fun onAnimationCancel(animation: Animator) {
                themeAnimationListener?.onAnimationCancel(animation)
            }

            override fun onAnimationRepeat(animation: Animator) {
                themeAnimationListener?.onAnimationRepeat(animation)
            }
        })

        anim?.start()
    }

    private fun setStatusBarBackgroundColor() {
        if (DynamicThemeManager.manager.getSyncStatusBarIconsColorWithTheme()) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val decorView = window.decorView

            val dynamicThemeType =
                if (DynamicThemeManager.manager.getSyncStatusBarIconsColorWithThemeReverse()) {
                    DynamicThemeType.LIGHT
                } else {
                    DynamicThemeType.DARK
                }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    if (DynamicThemeManager.manager.getCurrentTheme().type == dynamicThemeType) {
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    } else {
                        0
                    }, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                var flags = decorView.systemUiVisibility
                if (DynamicThemeManager.manager.getCurrentTheme().type == dynamicThemeType) {
                    if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == 0) {
                        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        decorView.systemUiVisibility = flags
                    }
                } else {
                    if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != 0) {
                        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                        decorView.systemUiVisibility = flags
                    }
                }
            }
        }
    }

    /**
     * Returns the theme change flag.
     */
    fun isRunningChangeThemeAnimation(): Boolean {
        return anim?.isRunning == true
    }

    fun setThemeAnimationListener(listener: IThemeAnimationListener) {
        this.themeAnimationListener = listener
    }

    /**
     * Calling theme synchronization.
     */
    abstract fun syncTheme(appTheme: T)
}