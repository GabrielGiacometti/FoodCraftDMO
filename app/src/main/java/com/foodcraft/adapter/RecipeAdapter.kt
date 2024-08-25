package com.foodcraft.adapter

import android.util.Log
import com.foodcraft.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodcraft.model.RecipeModel

class RecipeAdapter(private val recipes: List<RecipeModel>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewRecipeTitle: TextView = itemView.findViewById(R.id.textViewRecipeTitle)
        private val imageRecipe: ImageView = itemView.findViewById(R.id.imageRecipe)

        fun bind(recipe: RecipeModel) {
            textViewRecipeTitle.text = recipe.name
            Log.d("RecipeAdapter", "Image URL: ${recipe.imageUrl}")
            Glide.with(itemView.context)
                .load(recipe.imageUrl)
                .into(imageRecipe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_recipes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size
}