package com.websarva.wings.android.pagingkotlin.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.websarva.wings.android.pagingkotlin.data.remote.fetchPhoneList
import com.websarva.wings.android.pagingkotlin.data.remote.fetchPhoneListSize

class PhonePagingSource : PagingSource<Int, Phone>() {
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Phone> {
		var returnVal: LoadResult<Int, Phone>
		try {
			val phoneListSize = fetchPhoneListSize()
			val startKey = params.key ?: 1
			var endKey = startKey + params.loadSize - 1
			if(endKey > phoneListSize) {
				endKey = phoneListSize
			}
			val fetchedPhoneList = fetchPhoneList(startKey, endKey)
			val prevKey = if(startKey - params.loadSize <= 0) {
				null
			}
			else {
				startKey - params.loadSize
			}
			val nextKey = if(endKey + 1 >= phoneListSize) {
				null
			}
			else {
				endKey + 1
			}
			returnVal = LoadResult.Page(fetchedPhoneList, prevKey, nextKey)
		}
		catch(ex: Exception) {
			returnVal = LoadResult.Error(ex)
		}
		return returnVal
	}

	override fun getRefreshKey(state: PagingState<Int, Phone>): Int? {
		var returnVal: Int? = null;
		val anchorPosition = state.anchorPosition
		if(anchorPosition != null) {
			val phone = state.closestItemToPosition(anchorPosition)
			if(phone != null) {
				val returnKey = phone.id - state.config.pageSize / 2
				if(returnKey > 0) {
					returnVal = returnKey.toInt()
				}
			}
		}
		return returnVal
	}
}
