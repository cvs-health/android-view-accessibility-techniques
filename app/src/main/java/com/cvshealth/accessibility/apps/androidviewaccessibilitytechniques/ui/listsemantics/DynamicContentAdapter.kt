/*
   Copyright 2023 CVS Health and/or one of its affiliates

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.listsemantics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.ListItemDynamicContentBinding

/**
 * Adapter for a list of dynamic content about articles
 */
class DynamicContentAdapter : ListAdapter<DynamicContent, DynamicContentAdapter.DynamicContentViewHolder>(DynamicContentDiffer) {

    class DynamicContentViewHolder(
        private val itemBinding: ListItemDynamicContentBinding
        ) : RecyclerView.ViewHolder(itemBinding.root)
    {
        private var currentContent: DynamicContent? = null

        private fun setTextAndVisibility(value: String?, textView: TextView) {
            if (value.isNullOrBlank()) {
                textView.visibility = View.GONE
                textView.text = ""
            } else {
                textView.text = value
                textView.visibility = View.VISIBLE
            }
        }

        fun bind(content: DynamicContent) {
            currentContent = content
            setTextAndVisibility(content.heading, itemBinding.textHeading)
            setTextAndVisibility(content.title, itemBinding.textTitle)
            setTextAndVisibility(content.subtitle, itemBinding.textSubtitle)
            setTextAndVisibility(content.author, itemBinding.textAuthor)
            setTextAndVisibility(content.description, itemBinding.textDescription)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicContentViewHolder {
        val itemBinding = ListItemDynamicContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DynamicContentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DynamicContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

/**
 * Differ for article information objects.
 */
object DynamicContentDiffer : DiffUtil.ItemCallback<DynamicContent>() {
    override fun areItemsTheSame(oldItem: DynamicContent, newItem: DynamicContent): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: DynamicContent, newItem: DynamicContent): Boolean =
        oldItem.type == newItem.type
                && oldItem.heading == newItem.heading
                && oldItem.subtitle == newItem.subtitle
                && oldItem.author == newItem.author
                && oldItem.description == newItem.description
}