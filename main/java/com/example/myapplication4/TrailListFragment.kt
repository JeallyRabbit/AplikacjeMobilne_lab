// path/filename: app/src/main/java/com/example/touristguide/TrailListFragment.kt
package com.example.myapplication4

import com.example.myapplication4.model.Trail
import android.content.Intent
import android.content.Context
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.StandardCharsets
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class TrailListFragment : Fragment() {

    private lateinit var trails: List<Trail>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        loadTrails() // Loading trails from JSON
        val view = inflater.inflate(R.layout.fragment_trail_list, container, false)
        val listView = view.findViewById<ListView>(R.id.list)

        val trailNames = trails.map { it.name } // Creating trailNames map


        val listViewAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            trailNames
        )

        listView.adapter = listViewAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(activity, TrailDetailActivity::class.java).apply {

                putExtra("TRAIL_ID", trails[position].id) // Passing Trail ID
            }
            startActivity(intent)
        }

        return view
    }

    private fun loadTrails() {
        val jsonString = loadJSONFromAsset(requireContext(), "trails.json")
        val jsonArray = JSONArray(jsonString)
        val trailsList = mutableListOf<Trail>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val trail = Trail(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("description")
            )
            trailsList.add(trail)
        }
        trails = trailsList
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String? {
        val json: String? = try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }




}
