package com.example.testcgts.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testcgts.R
import com.example.testcgts.adapter.CharactersAdapter
import com.example.testcgts.databinding.ActivityMainBinding
import com.example.testcgts.models.Character
import com.example.testcgts.network.NetworkBuilder
import com.example.testcgts.network.interfaces.MarvelAPIInterface
import com.example.testcgts.network.request.RequestCommon
import com.example.testcgts.network.response.ResponseCommon
import com.example.testcgts.utils.Common.generateHashAPI
import com.example.testcgts.utils.Common.generateTimestamp
import com.example.testcgts.utils.Common.md5
import com.example.testcgts.utils.Constants.API_PUBLIC_KEY
import com.example.testcgts.utils.interfaces.RecyclerViewListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RecyclerViewListener {

    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    private var adapter: CharactersAdapter? = null
    private var items: MutableList<Character> = arrayListOf()
    private lateinit var responseAPI: ResponseCommon<Character>
    private var offset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CharactersAdapter(items, this)

        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        binding.progressBar.visibility = View.VISIBLE

        val request = NetworkBuilder.buildService(MarvelAPIInterface::class.java)

        val ts = generateTimestamp()
        val hash = generateHashAPI(ts)

        val call = request.getCharacters(
            ts,
            API_PUBLIC_KEY,
            hash,
            offset.toString()
        )

        call.enqueue(object : Callback<ResponseCommon<Character>> {
            override fun onResponse(call: Call<ResponseCommon<Character>>, response: Response<ResponseCommon<Character>>) {
                if (response.isSuccessful){
                    responseAPI = response.body()!!

                    validateList(responseAPI.data!!.results)

                    adapter!!.updateData(items)
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

    fun validateList(list: MutableList<Character>) {
        for (item in list) {
            if (!items.contains(item)) {
                items.add(item)
            }
        }
    }

    override fun onTopReached(position: Int) {
    }

    override fun onBottomReached(position: Int) {
        offset = position
        getData()
    }
}