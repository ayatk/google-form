package com.ayatk.googleform

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.content.systemService
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private val compositeDisposable = CompositeDisposable()

  private val inputMethodManager by lazy {
    this.systemService<InputMethodManager>()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    RxTextView.textChanges(form)
      .map { it.isNotBlank() }
      .subscribe({ submit.isEnabled = it })

    submit.setOnClickListener {
      FormClient.service
        .postForm(form.text.toString())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
          {
            form.clearFocus()
            inputMethodManager.hideSoftInputFromWindow(
              root.windowToken,
              InputMethodManager.HIDE_NOT_ALWAYS
            )
            if (it.code() == 200) {
              form.text.clear()
              Snackbar.make(root, "ありがとうございました", Snackbar.LENGTH_SHORT).show()
            } else {
              Snackbar.make(root, "送信できませんでした", Snackbar.LENGTH_SHORT).show()
            }
          },
          {
            form.clearFocus()
            inputMethodManager.hideSoftInputFromWindow(
              root.windowToken,
              InputMethodManager.HIDE_NOT_ALWAYS
            )
            Log.e("ほげ", "つらい", it)
            Snackbar.make(root, "送信できませんでした", Snackbar.LENGTH_SHORT).show()
          }
        )
        .addTo(compositeDisposable)
    }
  }

  override fun onDestroy() {
    compositeDisposable.clear()
    super.onDestroy()
  }
}
