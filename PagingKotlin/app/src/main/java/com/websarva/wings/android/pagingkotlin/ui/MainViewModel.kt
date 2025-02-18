package com.websarva.wings.android.pagingkotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.websarva.wings.android.pagingkotlin.data.local.Phone
import com.websarva.wings.android.pagingkotlin.data.repository.PhoneRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
	val phoneListFlow: Flow<PagingData<Phone>>
	private val _phoneRepository: PhoneRepository

	init {
		_phoneRepository  = PhoneRepository()
		val phoneListPager = _phoneRepository.getAllPhoneListPager()
		phoneListFlow = phoneListPager.flow.cachedIn(viewModelScope)
	}
}
