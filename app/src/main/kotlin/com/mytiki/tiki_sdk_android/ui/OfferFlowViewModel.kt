package com.mytiki.tiki_sdk_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mytiki.tiki_sdk_android.TikiSdk

class OfferFlowViewModel : ViewModel() {
    val offer: Offer = TikiSdk.offers.values.first()

    val step: LiveData<OfferFlowStep>
        get() = _step

    private var _step: MutableLiveData<OfferFlowStep> = MutableLiveData(OfferFlowStep.PROMPT)
}