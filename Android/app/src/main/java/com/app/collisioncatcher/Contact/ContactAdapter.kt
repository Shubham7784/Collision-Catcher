package com.app.collisioncatcher.Contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.collisioncatcher.Acitivities.Contact
import com.app.collisioncatcher.Entity.Member
import com.app.collisioncatcher.R

class ContactAdapter(private val contacts: ArrayList<Member>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.contactName)
        val number: TextView = itemView.findViewById(R.id.contactNumber)
        val relation : TextView = itemView.findViewById(R.id.contactRelation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name
        holder.number.text = contact.phoneNo
        holder.relation.text = contact.relation
    }
}
