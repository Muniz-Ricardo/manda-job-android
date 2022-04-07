package br.com.nouva.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.nouva.core.data.KeyUser

class SharedViewModelCustom : ViewModel() {

    private val _keyUser: MutableLiveData<KeyUser> = MutableLiveData()
    val keyUser: LiveData<KeyUser> get() = _keyUser

    fun setKeyUser(keyUser: KeyUser) {
        _keyUser.value = keyUser
    }
}