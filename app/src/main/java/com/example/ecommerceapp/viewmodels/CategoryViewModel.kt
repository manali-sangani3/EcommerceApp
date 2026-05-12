package com.example.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

    // Encapsulation: mutable internally, read-only externally
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            repository.getCategoriesFlow()
                .catch {
                    println("Error in Flow")
                }
                .collect {
                    // Each time new data is emitted, this block runs
                    categories ->
                    // Update the stateflow with new data
                    _categories.value = categories
                    println("categories updated in Viewmodel")
                }
        }
    }
}