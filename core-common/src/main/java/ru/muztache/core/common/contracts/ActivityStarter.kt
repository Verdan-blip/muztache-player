package ru.muztache.core.common.contracts

import android.content.Intent

interface ActivityStarter {

    fun getIntentForMainActivity(): Intent
}