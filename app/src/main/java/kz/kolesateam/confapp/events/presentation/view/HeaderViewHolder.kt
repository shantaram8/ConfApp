package kz.kolesateam.confapp.events.presentation.view

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.HeaderItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem

class HeaderViewHolder(itemView: View) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val userNameTextView: TextView =
        itemView.findViewById(R.id.header_layout_greeting_user_text_view)


    override fun onBind(
        data: UpcomingEventListItem
    ) {
        val userName: String = (data as? HeaderItem)?.userName ?: return
        userNameTextView.text = itemView.resources.getString(R.string.hello_text_fmt, userName)
    }
}