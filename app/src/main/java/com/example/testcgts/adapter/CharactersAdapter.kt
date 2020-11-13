package com.example.testcgts.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.testcgts.R
import com.example.testcgts.models.Character
import com.example.testcgts.ui.activities.DetailHeroActivity
import com.example.testcgts.utils.IntegerVersionSignature
import com.example.testcgts.utils.interfaces.RecyclerViewListener

class CharactersAdapter(
    private var characters: List<Character>,
    private var listener: RecyclerViewListener
) : RecyclerView.Adapter<CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false)
        return CharactersViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("TEST", characters.size.toString())
        return characters.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        if (position == characters.size -1) {
            listener.onBottomReached(position)
        }
        return holder.bind(characters[position])
    }

    fun updateData(data: List<Character>) {
        characters = data
        notifyDataSetChanged()
    }

}

class CharactersViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    private var heroImg: ImageView = item.findViewById(R.id.heroImg)
    private var heroName: TextView = item.findViewById(R.id.heroName)

    fun bind(character: Character) = with(itemView) {
        Glide.with(itemView.context)
            .load(character.thumbnail!!.path + "." + character.thumbnail!!.extension)
            .asBitmap()
            .fitCenter()
            .priority(Priority.HIGH)
            .placeholder(R.drawable.not_found_picture)
            //.signature(IntegerVersionSignature(getCurrentDateInt()))
            .into(heroImg)
        heroName.text = character.name

        setOnClickListener {
            val intent = Intent(itemView.context, DetailHeroActivity::class.java)
            intent.putExtra("character", character)
            itemView.context.startActivity(intent)
        }
    }
}