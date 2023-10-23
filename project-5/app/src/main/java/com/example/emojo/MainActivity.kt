package com.example.emojo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity()
{


    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("MMM. dd, yyyy", Locale.US)

    private var select = 3

    private val entries: MutableList<MoodEntry> = mutableListOf()




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var seeker = findViewById<SeekBar>(R.id.seeker)
        var date = findViewById<TextView>(R.id.tvDate)
        var emoji = findViewById<ImageView>(R.id.emoji)
        var ring = findViewById<ImageView>(R.id.ring)
        val setMood = findViewById<Button>(R.id.btnSet)


        val recyclerView: RecyclerView = findViewById(R.id.rvMoods)
        val moodAdapter = MoodRecyclerViewAdapter(entries)
        recyclerView.adapter = moodAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)  // This sets a vertical linear layout for your RecyclerView





        lifecycleScope.launch(Dispatchers.IO) {
            // Retrieve all mood entries
            MoodDatabase.getDatabase(this@MainActivity).moodDao().getAllEntries().collect { allEntriesFromDB ->
                // Update the entries list on the main thread
                withContext(Dispatchers.Main) {
                    entries.clear()
                    entries.addAll(allEntriesFromDB)
                    moodAdapter.notifyDataSetChanged()
                }
            }
        }






        setMood.setOnClickListener {
//            val moodEntry = MoodEntry(select, date.text.toString())
//            entries.add(moodEntry)
//            moodAdapter.notifyDataSetChanged()

            val moodEntry = MoodEntry(select = select, date = date.text.toString())


            // Launch a coroutine to insert the new mood entry and then retrieve all entries
            lifecycleScope.launch(Dispatchers.IO) {
                // Insert the new mood entry
                MoodDatabase.getDatabase(this@MainActivity).moodDao().insert(moodEntry)

                // Retrieve all mood entries
                MoodDatabase.getDatabase(this@MainActivity).moodDao().getAllEntries()
                    .collect { allEntries ->
                        // Update the entries list on the main thread
                        withContext(Dispatchers.Main) {
                            entries.clear()
                            entries.addAll(allEntries)
                            moodAdapter.notifyDataSetChanged()
                        }
                        moodAdapter.notifyDataSetChanged()
                    }
            }





        }



        date.setOnClickListener{
            DatePickerDialog(
                this@MainActivity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    // Update the TextView with the selected date
                    calendar.set(year, month, dayOfMonth)
                    date.text = formatter.format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }




        seeker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {

                sad_happy(progress, seeker, emoji)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)
            {

                return
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {

                return
            }

        })


    }

    fun sad_happy(progress: Int, seeker: SeekBar, emoji: ImageView)
    {
        if(progress == 0)
        {
            emoji.setImageResource(R.drawable.sad3)
            seeker.performHapticFeedback(4)
            select = 0


        }
        else if(progress == 1)
        {
            emoji.setImageResource(R.drawable.sad2)
            seeker.performHapticFeedback(4)
            select = 1
        }
        else if(progress == 2)
        {
            emoji.setImageResource(R.drawable.sad)
            seeker.performHapticFeedback(4)
            select = 2
        }
        else if (progress == 3)
        {
            emoji.setImageResource(R.drawable.mid)
            seeker.performHapticFeedback(4)
            select = 3
        }
        else if(progress == 4)
        {
            emoji.setImageResource(R.drawable.smile)
            seeker.performHapticFeedback(4)
            select = 4
        }
        else if(progress == 5)
        {
            emoji.setImageResource(R.drawable.smile2)
            seeker.performHapticFeedback(4)
            select = 5
        }
        else if(progress == 6)
        {
            emoji.setImageResource(R.drawable.smile3)
            seeker.performHapticFeedback(4)
            select = 6
        }
    }


}