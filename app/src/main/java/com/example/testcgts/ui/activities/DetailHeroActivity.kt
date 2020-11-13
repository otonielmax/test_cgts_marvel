package com.example.testcgts.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.testcgts.R
import com.example.testcgts.adapter.CharactersAdapter
import com.example.testcgts.adapter.ItemHorizontalAdapter
import com.example.testcgts.databinding.ActivityDetailHeroBinding
import com.example.testcgts.models.Character
import com.example.testcgts.network.NetworkBuilder
import com.example.testcgts.network.interfaces.MarvelAPIInterface
import com.example.testcgts.network.response.ResponseCommon
import com.example.testcgts.utils.Common
import com.example.testcgts.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHeroActivity : AppCompatActivity() {

    private val TAG: String = "DetailHeroActivity"
    private lateinit var binding: ActivityDetailHeroBinding

    private lateinit var character: Character

    private var adapterComic: ItemHorizontalAdapter? = null
    private var adapterSerie: ItemHorizontalAdapter? = null
    private var adapterStories: ItemHorizontalAdapter? = null
    private var adapterEvents: ItemHorizontalAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.extras!!.getSerializable("character") as Character

        initView()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun initView() {
        // Data Primary
        Glide.with(applicationContext)
            .load(character.thumbnail!!.path + "." + character.thumbnail!!.extension)
            .asBitmap()
            .fitCenter()
            .priority(Priority.HIGH)
            .placeholder(R.drawable.not_found_picture)
            //.signature(IntegerVersionSignature(getCurrentDateInt()))
            .into(binding.heroImg)
        binding.heroName.text = character.name
        binding.heroDescription.text = character.description

        // Init Recycler and Adapters
        adapterComic = ItemHorizontalAdapter(character.comics!!.items!!)
        binding.recyclerComics.setHasFixedSize(true)
        binding.recyclerComics.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        binding.recyclerComics.adapter = adapterComic

        adapterSerie = ItemHorizontalAdapter(character.series!!.items!!)
        binding.recyclerSeries.setHasFixedSize(true)
        binding.recyclerSeries.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        binding.recyclerSeries.adapter = adapterSerie

        adapterStories = ItemHorizontalAdapter(character.stories!!.items!!)
        binding.recyclerStories.setHasFixedSize(true)
        binding.recyclerStories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        binding.recyclerStories.adapter = adapterStories

        adapterEvents = ItemHorizontalAdapter(character.events!!.items!!)
        binding.recyclerEvents.setHasFixedSize(true)
        binding.recyclerEvents.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        binding.recyclerEvents.adapter = adapterEvents

        updateViewData()
    }

    private fun updateViewData() {
        if (character.comics!!.available == null || character.comics!!.available!! == 0) {
            binding.contentComics.visibility = View.GONE
        } else {
            binding.contentComics.visibility = View.VISIBLE
            binding.comicsTitle.text = character.comics!!.available!!.toString() + " Comics"
            adapterComic!!.updateData(character.comics!!.items!!)
        }

        if (character.series!!.available == null || character.series!!.available!! == 0) {
            binding.contentSeries.visibility = View.GONE
        } else {
            binding.contentSeries.visibility = View.VISIBLE
            binding.seriesTitle.text = character.series!!.available!!.toString() + " Series"
            adapterSerie!!.updateData(character.series!!.items!!)
        }

        if (character.stories!!.available == null || character.stories!!.available!! == 0) {
            binding.contentStories.visibility = View.GONE
        } else {
            binding.contentStories.visibility = View.VISIBLE
            binding.storiesTitle.text = character.stories!!.available!!.toString() + " Historias"
            adapterStories!!.updateData(character.stories!!.items!!)
        }

        if (character.events!!.available == null || character.events!!.available!! == 0) {
            binding.contentEvents.visibility = View.GONE
        } else {
            binding.contentEvents.visibility = View.VISIBLE
            binding.eventsTitle.text = character.events!!.available!!.toString() + " Eventos"
            adapterEvents!!.updateData(character.events!!.items!!)
        }
    }

    private fun getData() {
        binding.progressBar.visibility = View.VISIBLE

        val request = NetworkBuilder.buildService(MarvelAPIInterface::class.java)

        val ts = Common.generateTimestamp()
        val hash = Common.generateHashAPI(ts)

        val call = request.getCharactersByID(
            character.id!!,
            ts,
            Constants.API_PUBLIC_KEY,
            hash,
            "0"
        )

        call.enqueue(object : Callback<ResponseCommon<Character>> {
            override fun onResponse(call: Call<ResponseCommon<Character>>, response: Response<ResponseCommon<Character>>) {
                if (response.isSuccessful){
                    if (response.body()!!.data!!.results.size > 0) {
                        character = response.body()!!.data!!.results[0]

                        updateViewData()
                    }
                }
                binding.progressBar.visibility = View.GONE
            }
            override fun onFailure(call: Call<ResponseCommon<Character>>, t: Throwable) {
                Log.e(TAG, t.toString())
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}