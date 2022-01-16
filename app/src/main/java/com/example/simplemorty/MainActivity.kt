package com.example.simplemorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private lateinit var nameTextView: TextView
    private lateinit var headerImageView: ImageView
    private lateinit var genderImageView: ImageView
    private lateinit var aliveTextView: TextView
    private lateinit var originTextView: TextView
    private lateinit var speciesTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameTextView = findViewById(R.id.name_tv)
        headerImageView = findViewById(R.id.header_img)
        genderImageView = findViewById(R.id.gender_img)
        aliveTextView = findViewById(R.id.alive_tv)
        originTextView = findViewById(R.id.origin_tv)
        speciesTextView = findViewById(R.id.species_tv)

        viewModel.refreshCharacter(54)
        viewModel.characterByIdLiveData.observe(this){ response ->
            if (response == null){
                Toast.makeText(this@MainActivity,
                "Unsuccessful network call!", Toast.LENGTH_SHORT).show()
                return@observe
            }

            nameTextView.text = response.name
            aliveTextView.text = response.status
            speciesTextView.text = response.species
            originTextView.text = response.origin.name

            if (response.gender.equals("male", true)){
                genderImageView.setImageResource(R.drawable.ic_male)
            }else{
                genderImageView.setImageResource(R.drawable.ic_female)
            }

            Picasso.get().load(response.image).into(headerImageView)
        }

       /* val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

        val rickAndMortyService: RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
       */

    }
}