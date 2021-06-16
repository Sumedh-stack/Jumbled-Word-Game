package com.wordsforfun.words

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter2(var context: Context, data: ArrayList<Subject2>): RecyclerView.Adapter<Adapter2.ViewHolder>()
{

    var data:ArrayList<Subject2>
    init{
        this.data=data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout= LayoutInflater.from(context).inflate(R.layout.itemsveg,parent,false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=data[position].title
        holder.id.text=data[position].id
        holder.desc.text=data[position].desc


    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(item: View): RecyclerView.ViewHolder(item)
    {
        internal var title: TextView
        internal var id: TextView
        internal var desc: TextView

        init {
            title=item.findViewById(R.id.words)
            id=item.findViewById(R.id.id)
            desc=item.findViewById(R.id.meaning)

        }

    }

}
