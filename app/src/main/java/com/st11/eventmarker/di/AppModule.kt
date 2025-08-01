package com.st11.eventmarker.di

import com.st11.eventmarker.data.local.AppDatabase
import com.st11.eventmarker.repository.EventRepository
import com.st11.eventmarker.viewmodel.EventViewModel
import com.st11.eventmarker.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Singleton Repository


//    // Define ViewModel injection
//    viewModel { OnboardingViewModel(get()) }
//
//    // Define PreferencesManager injection
//    single { OnboardingPreferencesManager(get()) }
//
    single { AppDatabase.getDatabase(get()).eventDao() }
    single { EventRepository(get()) }
    viewModel {  EventViewModel(get()) }


    viewModel { NotificationViewModel() }
//
//
//
//    single { AppDatabase.getDatabase(get()).debtDao() }
//    single { DebtRepository(get()) }
////    viewModel {  DebtViewModel(get()) }
//    viewModel {
//        DebtViewModel(
//            debtRepository = get(),
//            peopleRepository = get()
//        )
//    }
//
//
//    viewModel { DebtPayViewModel(get()) }
//    single { DebtRepayRepository(get()) }
//    single { AppDatabase.getDatabase(get()).debtRepayDao() }
//
//
//    // Define ViewModel injection
//    viewModel { CurrencyViewModel(get()) }
//
//    // Define PreferencesManager injection
//    single { CurrencyPreferences(get()) }
}
