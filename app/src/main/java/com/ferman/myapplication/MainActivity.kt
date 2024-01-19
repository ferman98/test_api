package com.ferman.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ferman.myapplication.databinding.ActivityMainBinding
import com.ferman.myapplication.model.Response
import com.ferman.myapplication.model.ResponseItem
import com.ferman.myapplication.model.Status

class MainActivity : AppCompatActivity() {

    val vm : MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun getData() {
        vm.getDatas(this).observe(this) {
            when (it) {
                Status.Failed -> {
                    binding.tvMain.text = "GAGAL ..."
                }
                Status.Loading -> {
                    binding.tvMain.text = "LOADING ..."
                }
                is Status.Success<*> -> {
                    (it.data as? List<ResponseItem>)?.also { res ->
                        binding.tvMain.text = res.toString()
                    }
                }
            }
        }
    }
}