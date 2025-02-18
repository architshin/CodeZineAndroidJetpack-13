package com.websarva.wings.android.pagingjava.data.local;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingConfig;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Futures;
import com.websarva.wings.android.pagingjava.data.remote.PhoneListFetcher;
import com.websarva.wings.android.pagingjava.data.remote.PhoneListSizeFetcher;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class PhonePagingSource extends ListenableFuturePagingSource<Integer, Phone> {
	@NonNull
	@Override
	public ListenableFuture<LoadResult<Integer, Phone>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
		LoadResult<Integer, Phone> returnVal;
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		try {
			int startKey = 1;
			if(loadParams.getKey() != null) {
				startKey = loadParams.getKey();
			}
			PhoneListSizeFetcher phoneListSizeFetcher = new PhoneListSizeFetcher();
			Future<Integer> phoneListSizeFuture = executorService.submit(phoneListSizeFetcher);
			int phoneListSize = phoneListSizeFuture.get();
			int endKey = startKey + loadParams.getLoadSize() - 1;
			if(endKey > phoneListSize) {
				endKey = phoneListSize;
			}

			PhoneListFetcher phoneListFetcher = new PhoneListFetcher(startKey, endKey);
			Future<List<Phone>> phoneListFuture = executorService.submit(phoneListFetcher);
			List<Phone> extractedPhoneList = phoneListFuture.get();

			Integer prevKey = null;
			if(startKey - loadParams.getLoadSize() > 0) {
				prevKey = startKey - loadParams.getLoadSize();
			}

			Integer nextKey = null;
			if(endKey + 1 < phoneListSize) {
				nextKey = endKey + 1;
			}

			returnVal = new LoadResult.Page<>(extractedPhoneList, prevKey, nextKey);
		}
		catch(Exception ex) {
			returnVal = new LoadResult.Error<>(ex);
		}

		return Futures.immediateFuture(returnVal);
	}

	@Nullable
	@Override
	public Integer getRefreshKey(@NonNull PagingState<Integer, Phone> pagingState) {
		Integer returnVal = null;
		Integer anchorPosition = pagingState.getAnchorPosition();
		if(anchorPosition != null) {
			Phone phone = pagingState.closestItemToPosition(anchorPosition);
			if(phone != null) {
				PagingConfig config = pagingState.getConfig();
				int returnKey = phone.id.intValue() - config.pageSize / 2;
				if(returnKey > 0) {
					returnVal = returnKey;
				}
			}
		}
		return returnVal;
	}
}
