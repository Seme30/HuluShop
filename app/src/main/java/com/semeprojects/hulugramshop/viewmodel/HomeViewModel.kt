package com.semeprojects.hulugramshop.viewmodel

import androidx.lifecycle.ViewModel
import com.semeprojects.hulugramshop.data.network.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {


    init {

    }
}