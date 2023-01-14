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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobiledev.volleydaddy.data.DataSource
import com.mobiledev.volleydaddy.model.RotationCard


/**
 * Adapter for the [RecyclerView] in [MainActivity].
 */
class RotationListAdapter :
    RecyclerView.Adapter<RotationListAdapter.RotationListViewHolder>() {

    class RotationListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val rotation_image = view.findViewById<ImageView>(R.id.card_image)
        val rotation_text = view.findViewById<TextView>(R.id.card_text)
    }

    private val rotations: List<RotationCard> = DataSource.rotationCards

    override fun getItemCount(): Int = rotations.size

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RotationListViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return RotationListViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: RotationListViewHolder, position: Int) {
        val rotation = rotations[position]

        holder.rotation_image.setImageResource(rotation.imageResourceId)
        holder.rotation_text.text = rotation.name

        // look into the parameter for the following command, could be the key to reusing fragments for motion layout
        // action = LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(letter = holder.button.text.toString())
        if (rotation.name.contains("Tutorial Resources", ignoreCase = true)) {
            holder.view.setOnClickListener {
                val action = RotationListFragmentDirections.actionRotationListFragmentToTutorialListFragment()
                holder.view.findNavController().navigate(action)
            }
        } else {
            holder.view.setOnClickListener {
                val action = RotationListFragmentDirections.actionRotationListFragmentToRotationsFragment(rotation=rotation.name)
                // Navigate using that action
                holder.view.findNavController().navigate(action)
            }
        }


    }
}