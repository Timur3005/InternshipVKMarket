package com.makhmutov.internshipvkmarket.presentation.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makhmutov.internshipvkmarket.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelFactory @Inject constructor(
    private val viewModelsProvider:
    @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelsProvider[modelClass]?.get() as T
    }
}