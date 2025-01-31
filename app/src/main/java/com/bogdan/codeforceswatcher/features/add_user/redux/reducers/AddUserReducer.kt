package com.bogdan.codeforceswatcher.features.add_user.redux.reducers

import com.bogdan.codeforceswatcher.features.add_user.redux.actions.AddUserActions
import com.bogdan.codeforceswatcher.features.add_user.redux.requests.AddUserRequests
import com.bogdan.codeforceswatcher.features.add_user.redux.states.AddUserState
import com.bogdan.codeforceswatcher.redux.states.AppState
import org.rekotlin.Action

fun addUserReducer(action: Action, state: AppState): AddUserState {
    var newState = state.addUserState

    when (action) {
        is AddUserRequests.AddUser -> {
            newState = newState.copy(status = AddUserState.Status.PENDING)
        }

        is AddUserRequests.AddUser.Success -> {
            newState = newState.copy(status = AddUserState.Status.DONE)
        }

        is AddUserRequests.AddUser.Failure -> {
            newState = newState.copy(status = AddUserState.Status.IDLE)
        }

        is AddUserActions.ClearAddUserState -> {
            newState = newState.copy(status = AddUserState.Status.IDLE)
        }
    }

    return newState
}
