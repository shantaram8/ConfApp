package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val userName = itemView.findViewById<TextView>(R.id.header_layout_greeting_user_text_view)

    fun onBind(userName: String) {
        this.userName.text = userName
    }
}