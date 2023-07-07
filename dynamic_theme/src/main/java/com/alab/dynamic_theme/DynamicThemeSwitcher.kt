package com.alab.dynamic_theme

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView
import com.alab.dynamic_theme.databinding.DynamicThemeSwitcherBinding


/**
 * Represents a theme switch.
 */
class DynamicThemeSwitcher @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val _binding =
        DynamicThemeSwitcherBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        _binding.dynamicThemeSwitcher.apply {
            Utils.getDynamicThemeActivity(context)?.let {
                DynamicThemeManager.manager.themeLiveData.observe(it) {
                    setThemeSwitcherState(this)
                }
            }

            setOnClickListener {
                themeSwitch(this)
            }
            setThemeSwitcherState(this)

            onAnimation(
                onStart = { animation, isReverse ->

                },
                onStop = { animation, isReverse ->
                    setThemeSwitcherState(this)
                }
            )
        }
    }

    private fun setThemeSwitcherState(themeSwitcherLottie: LottieAnimationView) {
        themeSwitcherLottie.progress =
            if (DynamicThemeManager.getInstance<DynamicTheme>()
                    .getCurrentTheme().type == DynamicThemeType.LIGHT
            ) 1f else 0f
    }

    private fun themeSwitch(themeSwitcherLottie: LottieAnimationView) {
        themeSwitcherLottie.speed =
            if (DynamicThemeManager.getInstance<DynamicTheme>()
                    .getCurrentTheme().type == DynamicThemeType.LIGHT
            ) {
                DynamicThemeManager.manager.changeTheme(
                    themeSwitcherLottie
                )
                -1f
            } else {
                DynamicThemeManager.manager.reverseChangeTheme(
                    themeSwitcherLottie
                )
                1f
            }
        themeSwitcherLottie.playAnimation()
    }

    private fun LottieAnimationView.onAnimation(
        onStart: (animation: Animator, isReverse: Boolean) -> Unit,
        onStop: (animation: Animator, isReverse: Boolean) -> Unit
    ) {
        addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator, isReverse: Boolean) {
                super.onAnimationStart(animation, isReverse)
                onStart(animation, isReverse)
            }

            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                onStop(animation, isReverse)
            }

            override fun onAnimationEnd(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
        })
    }
}