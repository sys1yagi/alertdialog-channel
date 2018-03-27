# alertdialog-channel

This is an example of replacing the callback of AlertDialog with a coroutine.

```kotlin
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
                    channel.sendBlocking(Result.OK)
                }
                .setNegativeButton("Cancel") { _, _ ->
                    channel.sendBlocking(Result.CANCEL)
                }
                .create()
                .show()
        return channel
    }
}
```

then,

```kotlin
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
```
