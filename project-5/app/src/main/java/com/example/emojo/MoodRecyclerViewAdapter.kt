package com.example.emojo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class MoodRecyclerViewAdapter(private val entries: MutableList<MoodEntry>) : RecyclerView.Adapter<MoodRecyclerViewAdapter.EntryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mood_entry, parent, false)
        return EntryViewHolder(view)
    }


    inner class EntryViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: MoodEntry? = null
        val mEntryDate: TextView = mView.findViewById<View>(R.id.date) as TextView
        val mEntryEmoji: ImageView = mView.findViewById<View>(R.id.emoji) as ImageView



        override fun toString(): String {
            return mEntryDate.toString()
        }
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]

        holder.mItem = entry
        holder.mEntryDate.text = entry.date



        when (entry.select)
            {
                0 -> holder.mEntryEmoji.setImageResource(R.drawable.sad3)
                1 -> holder.mEntryEmoji.setImageResource(R.drawable.sad2)
                2 -> holder.mEntryEmoji.setImageResource(R.drawable.sad)
                3 -> holder.mEntryEmoji.setImageResource(R.drawable.mid)
                4 -> holder.mEntryEmoji.setImageResource(R.drawable.smile)
                5 -> holder.mEntryEmoji.setImageResource(R.drawable.smile2)
                6 -> holder.mEntryEmoji.setImageResource(R.drawable.smile3)
            }


    }

    override fun getItemCount(): Int {
        return entries.size
    }


}

