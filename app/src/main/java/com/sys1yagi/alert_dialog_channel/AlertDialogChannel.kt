package com.sys1yagi.alert_dialog_channel

import android.content.Context
import android.support.v7.app.AlertDialog
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch

class AlertDialogChannel {
    enum class Result {
        OK,
        CANCEL
    }

    fun show(context: Context): ReceiveChannel<Result> {
        val channel = Channel<Result>()
        AlertDialog
                .Builder(context)
                .setTitle("OK?")
                .setMessage("Hello")
                .setPositiveButton("OK") { _, _ ->
                    launch(UI) {
                        channel.send(Result.OK)
                    }
                }
                .setNegativeButton("Cancel") { _, _ ->
                    launch(UI) {
                        channel.send(Result.CANCEL)
                    }
                }
                .create()
                .show()
        return channel
    }
}
