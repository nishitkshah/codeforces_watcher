package com.bogdan.codeforceswatcher.network

import com.bogdan.codeforceswatcher.features.users.models.User
import com.bogdan.codeforceswatcher.network.models.Error
import com.bogdan.codeforceswatcher.network.models.UsersRequestResult
import kotlinx.coroutines.*

suspend fun getUsers(handles: String, isRatingUpdatesNeeded: Boolean): UsersRequestResult {
    try {
        val response = RestClient.getUsers(handles)
        response.body()?.users?.let { users ->
            return if (isRatingUpdatesNeeded) {
                loadRatingUpdates(users)
            } else {
                UsersRequestResult.Success(users)
            }
        } ?: return UsersRequestResult.Failure(Error.RESPONSE)
    } catch (t: Throwable) {
        return UsersRequestResult.Failure(Error.INTERNET)
    }
}

suspend fun loadRatingUpdates(userList: List<User>): UsersRequestResult {
    var countFetchedUsers = 0
    for (user in userList) {
        delay(250) // Because Codeforces blocks frequent queries
        val response = try {
            RestClient.getRating(user.handle)
        } catch (error: java.net.SocketTimeoutException) {
            null
        }
        response?.body()?.ratingChanges?.let { ratingChanges ->
            user.ratingChanges = ratingChanges
        } ?: break
        countFetchedUsers++
    }
    return if (countFetchedUsers < userList.size) {
        UsersRequestResult.Failure(Error.RESPONSE)
    } else {
        UsersRequestResult.Success(userList)
    }
}