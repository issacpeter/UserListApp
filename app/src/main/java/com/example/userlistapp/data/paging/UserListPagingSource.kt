package com.example.userlistapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.userlistapp.data.api.UserService
import com.example.userlistapp.data.model.User
import com.example.userlistapp.data.model.UserListResponse
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0
private const val NETWORK_PAGE_SIZE = 10

class UserListPagingSource(
    private val service: UserService
) : PagingSource<Int, User>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getUsers(
                limit = params.loadSize,
                skip = pageIndex
            )
            val users = response.users
            val nextKey =
                if (users.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    Log.e("@@@TAG", "load: "+pageIndex+" "+params.loadSize+" "+ NETWORK_PAGE_SIZE)
                    pageIndex + users.size
                }
            LoadResult.Page(
                data = users,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}