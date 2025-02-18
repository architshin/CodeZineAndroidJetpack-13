package com.websarva.wings.android.pagingkotlin.data.remote

import com.websarva.wings.android.pagingkotlin.data.local.Phone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchPhoneList(startKey: Int, endKey: Int): MutableList<Phone> {
	return withContext(Dispatchers.IO) {
		var endKeyLocal = endKey
		if(endKey >= 334) {
			endKeyLocal = 334
		}
		val extractedPhoneList = mutableListOf<Phone>()
		for(i in startKey .. endKeyLocal) {
			val phoneNoInt = (99999999 * Math.random()).toInt()
			val phoneNo = "090" + String.format("%08d", phoneNoInt)
			val phone = Phone(i.toLong(), phoneNo)
			extractedPhoneList.add(phone)
		}
		extractedPhoneList
	}
}

suspend fun fetchPhoneListSize(): Int {
	return withContext(Dispatchers.IO) {
		334
	}
}
