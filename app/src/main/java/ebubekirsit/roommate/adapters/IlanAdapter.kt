package ebubekirsit.roommate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ebubekirsit.roommate.R
import ebubekirsit.roommate.models.IlanData

class IlanAdapter (private val ilanList: ArrayList<IlanData>) :
    RecyclerView.Adapter<IlanAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ilancard,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIlan = ilanList[position]
        holder.ilanAdres.text = currentIlan.ilanAdress
        holder.ilanTelefon.text = currentIlan.kullaniciPhoneNo
        holder.ilanMail.text = currentIlan.kullaniciEmail
    }

    override fun getItemCount(): Int {
        return ilanList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ilanAdres : TextView = itemView.findViewById(R.id.textadres)
        val ilanTelefon : TextView = itemView.findViewById(R.id.telefon)
        val ilanMail : TextView = itemView.findViewById(R.id.textemail)

    }


}