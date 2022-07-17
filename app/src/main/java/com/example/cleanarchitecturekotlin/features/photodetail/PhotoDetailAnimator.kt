package com.example.cleanarchitecturekotlin.features.photodetail

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.cleanarchitecturekotlin.core.extension.cancelTransition
import javax.inject.Inject

class PhotoDetailAnimator
@Inject constructor() {

    private val TRANSITION_DELAY = 200L
    private val TRANSITION_DURATION = 400L

    internal fun postponeEnterTransition(activity: FragmentActivity) =
        activity.postponeEnterTransition()

    internal fun fadeVisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.VISIBLE)

    internal fun fadeInvisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.INVISIBLE)

    private fun beginTransitionFor(viewContainer: ViewGroup, view: View, visibility: Int) {
        val transition = Fade()
        transition.startDelay = TRANSITION_DELAY
        transition.duration = TRANSITION_DURATION
        TransitionManager.beginDelayedTransition(viewContainer, transition)
        view.visibility = visibility
    }
}