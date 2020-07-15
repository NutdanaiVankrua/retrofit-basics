package com.example.retrofitbasics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dataStore = GreetingDataStore()
    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        call_service_button.setOnClickListener {
            val greetingRequest = dataStore
                .request()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        message_text_view.text = it.message
                    },
                    onError = {
                        println("lol -> {$it}")
                    }
                )
            disposeBag.add(greetingRequest)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}