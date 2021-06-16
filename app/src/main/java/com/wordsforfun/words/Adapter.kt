package com.wordsforfun.words


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(var context:Context, data: ArrayList<Subject>):RecyclerView.Adapter<Adapter.ViewHolder>()
{

    var data:ArrayList<Subject>
    init{
        this.data=data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout=LayoutInflater.from(context).inflate(R.layout.item_words,parent,false)
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
    class ViewHolder(item: View):RecyclerView.ViewHolder(item)
    {
        internal var title:TextView
        internal var id:TextView
        internal var desc:TextView

        init {
            title=item.findViewById(R.id.words)
            id=item.findViewById(R.id.id)
            desc=item.findViewById(R.id.meaning)

        }

    }

}
