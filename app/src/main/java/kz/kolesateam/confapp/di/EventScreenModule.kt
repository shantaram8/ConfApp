package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.branch_all_events.data.DefaultBranchAllEventsRepository
import kz.kolesateam.confapp.upcoming_events.data.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.branch_all_events.data.datasources.BranchAllEventsDataSource
import kz.kolesateam.confapp.upcoming_events.data.datasources.UpcomingEventsDataSource
import kz.kolesateam.confapp.branch_all_events.domain.BranchAllEventsRepository
import kz.kolesateam.confapp.upcoming_events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.branch_all_events.presentation.BranchAllEventsViewModel
import kz.kolesateam.confapp.events_details.data.DefaultEventDetailsRepository
import kz.kolesateam.confapp.events_details.data.datasources.EventDetailsDataSource
import kz.kolesateam.confapp.events_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.events_details.presentation.EventDetailsViewModel
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val API_BASE_URL = "http://37.143.8.68:2020"

val eventScreenModule: Module = module {

    viewModel {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
            favoriteEventsRepository = get(),
            notificationAlarmHelper = get()
        )
    }
    viewModel {
        BranchAllEventsViewModel(
            branchAllEventsRepository = get()
        )
    }
    viewModel {
        EventDetailsViewModel(
            eventDetailsRepository = get()
        )
    }

    single {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(UpcomingEventsDataSource::class.java)
    }
    single {
        val retrofit: Retrofit = get()

        retrofit.create(BranchAllEventsDataSource::class.java)
    }
    single {
        val retrofit: Retrofit = get()

        retrofit.create(EventDetailsDataSource::class.java)
    }

    factory {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get(),
            userNameDataSource = get(named(SHARED_PREFS_DATA_SOURCE))
        ) as UpcomingEventsRepository
    }

    factory {
        DefaultBranchAllEventsRepository(
            branchAllEventsDataSource = get()
        ) as BranchAllEventsRepository
    }

    factory {
        DefaultEventDetailsRepository(
            eventDetailsDataSource = get()
        ) as EventDetailsRepository
    }
}




