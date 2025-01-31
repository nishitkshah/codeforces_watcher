package com.bogdan.codeforceswatcher.util

import android.os.Bundle
import com.bogdan.codeforceswatcher.CwApp
import com.google.firebase.analytics.FirebaseAnalytics

enum class Refresh { USERS, CONTESTS, ACTIONS }

object Analytics {

    var isEnabled: Boolean = true

    private val instance = FirebaseAnalytics.getInstance(CwApp.app)

    fun logAddContestToCalendarEvent(contestName: String) {
        if (isEnabled) {
            val params = Bundle()
            params.putString("contest_name", contestName)
            instance.logEvent("add_contest_to_google_calendar", params)
        }
    }

    fun logRefreshingData(refresh: Refresh) {
        if (isEnabled) {
            instance.logEvent(when (refresh) {
                Refresh.USERS -> "users_list_refresh"
                Refresh.CONTESTS -> "contests_list_refresh"
                Refresh.ACTIONS -> "actions_list_refresh"
            }, Bundle())
        }
    }

    fun logUserAdded() {
        if (isEnabled) {
            instance.logEvent("user_added", Bundle())
        }
    }

    fun logShareApp() {
        if (isEnabled) {
            instance.logEvent("actions_share_app", Bundle())
        }
    }

    fun logAppShared() {
        if (isEnabled) {
            instance.logEvent("actions_app_shared", Bundle())
        }
    }

    fun logShareComment() {
        if (isEnabled) {
            instance.logEvent("action_share_comment", Bundle())
        }
    }

    fun logActionOpened() {
        if (isEnabled) {
            instance.logEvent("action_opened", Bundle())
        }
    }
}
