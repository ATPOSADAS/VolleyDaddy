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

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mobiledev.volleydaddy.R
import com.mobiledev.volleydaddy.data.DataSource
import com.mobiledev.volleydaddy.model.RotationCard

/**
 * Adapter for the [RecyclerView] in [DetailActivity].
 */
class RotationAdapter(private val rotationId: String, context: Context?) :
    RecyclerView.Adapter<RotationAdapter.RotationViewHolder>() {

    class RotationViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val rotation_image = view.findViewById<ImageView>(R.id.card_image)
        val rotation_text = view.findViewById<TextView>(R.id.card_text)
    }

    private val rotations: List<RotationCard> = DataSource.rotationCards

    override fun getItemCount(): Int = rotations.size

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RotationViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        // Setup custom accessibility delegate to set the text read
//        layout.accessibilityDelegate = RotationListAdapter

        return RotationViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: RotationViewHolder, position: Int) {
        val rotation = rotations[position]

        holder.rotation_image.setImageResource(rotation.imageResourceId)
        holder.rotation_text.text = rotation.name

        holder.view.setOnClickListener {
            //"You clicked on \"${rotation.name}\"!"

//            Toast.makeText(this@RotationAdapter, "You clicked on \"${rotation.name}\"!", Toast.LENGTH_SHORT).show()
        }

    }
}