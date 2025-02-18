package com.websarva.wings.android.pagingkotlin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.websarva.wings.android.pagingkotlin.data.local.Phone
import com.websarva.wings.android.pagingkotlin.data.local.PhonePagingSource

private const val ITEMS_PER_PAGE = 50

class PhoneRepository {
	fun getAllPhoneListPager(): Pager<Int, Phone> {
		val pagingConfig = PagingConfig(ITEMS_PER_PAGE)
		val phoneListPager = Pager(pagingConfig) {PhonePagingSource()}
		return phoneListPager
	}
}
