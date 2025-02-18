package com.websarva.wings.android.pagingjava.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.websarva.wings.android.pagingjava.data.local.Phone;
import com.websarva.wings.android.pagingjava.data.repository.PhoneRepository;

import kotlinx.coroutines.CoroutineScope;

public class MainViewModel extends ViewModel {
	private PhoneRepository _phoneRepository;
	private LiveData<PagingData<Phone>> _phoneListLiveData;

	public MainViewModel() {
		_phoneRepository = new PhoneRepository();
		Pager<Integer, Phone> phoneListPager = _phoneRepository.getAllPhoneListPager();
		CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(MainViewModel.this);
		_phoneListLiveData = PagingLiveData.cachedIn(PagingLiveData.getLiveData(phoneListPager), viewModelScope);
	}

	public LiveData<PagingData<Phone>> getPhoneListLiveData() {
		return _phoneListLiveData;
	}
}
