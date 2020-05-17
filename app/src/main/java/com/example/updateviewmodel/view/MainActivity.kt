package com.example.updateviewmodel.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.updateviewmodel.R
import com.example.updateviewmodel.model.SongSearch
import com.example.updateviewmodel.databinding.ActivityMainBinding
import com.example.updateviewmodel.viewmodel.SongViewModel
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private var mDispose:Disposable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        model = SongViewModel()

        binding.rcSong.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = SongAdapter( if (model.songs.value == null) mutableListOf<SongSearch>() else model.songs.value!! )
        }
        binding.data = model
        binding.lifecycleOwner = this

        binding.btnSearch.setOnClickListener {
            mDispose?.dispose()
            mDispose = model.searchSong(binding.edtSearch.text.toString().trim())
        }

    }

    override fun onDestroy() {
        mDispose?.dispose()
        super.onDestroy()
    }
}
