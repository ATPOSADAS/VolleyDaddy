/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobiledev.volleydaddy

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobiledev.volleydaddy.data.DataSource
import com.mobiledev.volleydaddy.model.RotationCard
import com.mobiledev.volleydaddy.model.TutorialCard


/**
 * Adapter for the [RecyclerView] in [MainActivity].
 */
class TutorialListAdapater :
    RecyclerView.Adapter<TutorialListAdapater.TutorialListViewHolder>() {

    class TutorialListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tutorial_image = view.findViewById<ImageView>(R.id.card_image)
        val tutorial_text = view.findViewById<TextView>(R.id.card_text)
    }

    private val tutorials: List<TutorialCard> = DataSource.tutorialCards

    override fun getItemCount(): Int = tutorials.size

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialListViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return TutorialListViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: TutorialListViewHolder, position: Int) {
        val tutorial = tutorials[position]

        holder.tutorial_image.setImageResource(tutorial.imageResourceId)
        holder.tutorial_text.text = tutorial.name
        val context = holder.view.context

        if (tutorial.name.equals("More Information")) {
            holder.view.setOnClickListener {
                val url = "https://www.youtube.com/@ElevateYourselfOfficial"
                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(urlIntent)

            }
        } else {
            holder.view.setOnClickListener {
                val action =
                    TutorialListFragmentDirections.actionTutorialListFragmentToTutorialFragment(tutorial=tutorial.name)
                // Navigate using that action

                holder.view.findNavController().navigate(action)
            }
        }
    }
}