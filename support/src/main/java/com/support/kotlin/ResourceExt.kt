package com.example.parth.kotlinpractice_2.support.kotlin

import android.app.Activity
import android.support.v4.content.ContextCompat

fun Int.getColor(activity: Activity): Int = ContextCompat.getColor(activity, this)