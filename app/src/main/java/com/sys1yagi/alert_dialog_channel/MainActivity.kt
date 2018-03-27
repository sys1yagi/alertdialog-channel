package com.sys1yagi.alert_dialog_channel

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener(::onClickButton)
    }

    private fun onClickButton(view: View) {
        launch(UI) {
            val result = AlertDialogChannel().show(this@MainActivity).receive()
            if (result == AlertDialogChannel.Result.OK) {
                Toast.makeText(this@MainActivity, "OK", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Cancel", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
