package com.foodcraft.adapter

import android.annotation.SuppressLint
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

class RecipeAdapter(
    private var recipes: List<RecipeModel>,
    private val onItemClick: (RecipeModel) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var originalRecipes: List<RecipeModel> = recipes

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewRecipeTitle: TextView = itemView.findViewById(R.id.textViewRecipeTitle)
        private val imageRecipe: ImageView = itemView.findViewById(R.id.imageRecipe)

        fun bind(recipe: RecipeModel) {
            textViewRecipeTitle.text = recipe.name
            Log.d("RecipeAdapter", "Image URL: ${recipe.imageUrl}")
            Glide.with(itemView.context)
                .load(recipe.imageUrl.trim())
                .into(imageRecipe)


            itemView.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateRecipes(newRecipes: List<RecipeModel>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_recipes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        val queries = query.split(",").map { it.trim().lowercase() }.filter { it.isNotEmpty() }


        recipes = if (queries.isEmpty()) {
            originalRecipes
        } else {
            originalRecipes.filter { recipe ->
                queries.any { query ->
                    recipe.recipeIngredient.any { ingredient ->
                        ingredient.lowercase().contains(query)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

}
