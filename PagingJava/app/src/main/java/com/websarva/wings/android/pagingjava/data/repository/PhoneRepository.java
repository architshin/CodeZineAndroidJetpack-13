package com.websarva.wings.android.pagingjava.data.repository;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import com.websarva.wings.android.pagingjava.data.local.Phone;
import com.websarva.wings.android.pagingjava.data.local.PhonePagingSource;

public class PhoneRepository {
	private static final int ITEMS_PER_PAGE = 30;

	public Pager<Integer, Phone> getAllPhoneListPager() {
		PagingConfig pagingConfig = new PagingConfig(ITEMS_PER_PAGE);
		Pager<Integer, Phone> phoneListPager = new Pager<>(pagingConfig, () -> new PhonePagingSource());
		return phoneListPager;
	}
}
