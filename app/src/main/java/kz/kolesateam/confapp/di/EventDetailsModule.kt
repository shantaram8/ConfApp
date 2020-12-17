package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events_details.data.DefaultEventDetailsRepository
import kz.kolesateam.confapp.events_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.events_details.presentation.EventDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val eventDetailsModule: Module = module {

    viewModel {
        EventDetailsViewModel(
                eventDetailsRepository = get()
        )
    }
    factory {
        DefaultEventDetailsRepository(
                eventDetailsDataSource = get()
        ) as EventDetailsRepository
    }
}