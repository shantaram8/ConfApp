package kz.kolesateam.confapp.favorite_events.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners

class FavoriteEventsAdapter(
    private val favoriteEventsClickListeners: UpcomingEventsClickListeners
) : RecyclerView.Adapter<FavoriteEventsViewHolder>() {

    private val favoriteEventsList: MutableList<EventApiData> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteEventsViewHolder = FavoriteEventsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.branch_all_events_card_item,
            parent,
            false
        ),
        favoriteEventsClickListeners
    )

    override fun onBindViewHolder(holder: FavoriteEventsViewHolder, position: Int) {
        holder.onBind(favoriteEventsList[position])
    }

    override fun getItemCount(): Int = favoriteEventsList.size


    fun setList(
        favoriteEventsDataList: List<EventApiData>
    ) {
        favoriteEventsList.clear()
        favoriteEventsList.addAll(favoriteEventsDataList)
        notifyDataSetChanged()
    }


}