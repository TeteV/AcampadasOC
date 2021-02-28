package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.frontend.R
import com.example.frontend.controller.models.WebSocketData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_home.*
import org.java_websocket.WebSocket
import org.java_websocket.client.WebSocketClient
import org.java_websocket.framing.Framedata
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.SocketFactory
import android.animation.Animator
import android.animation.ObjectAnimator

class HomeActivity : AppCompatActivity() {

    private lateinit var webSocketClient: WebSocketClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button.setOnClickListener {
            goToLoginActivity()
        }

        textViewSignIn.setOnClickListener {
            goToRegisterActivity()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun goToRegisterActivity() {
        val intent= Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }



    override fun onResume() {
        super.onResume()
        initWebSocket()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        webSocketClient.close()
    }

    private fun initWebSocket() {
        val coinbaseUri: URI? = URI(WEB_SOCKET_URL)

        createWebSocketClient(coinbaseUri)

        /*val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory*/
        val socketFactory: SocketFactory = SocketFactory.getDefault() as SocketFactory
        webSocketClient.setSocketFactory(socketFactory)
        Log.d(TAG, webSocketClient.uri.toString())
        val algo = webSocketClient.connect()

        Log.d(TAG, algo.toString())
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        Log.d(TAG, "HOLA HOLITA")
        webSocketClient = object : WebSocketClient(coinbaseUri) {

            override fun onWebsocketPing(conn: WebSocket?, f: Framedata?) {
                Log.d(TAG, "onWebsocketPing")
            }

            override fun onWebsocketPong(conn: WebSocket?, f: Framedata?) {
                Log.d(TAG, "onWebsocketPong")
            }

            override fun onWebsocketClosing(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onWebsocketClosing")
                Log.d(TAG, reason.toString())
                Log.d(TAG, remote.toString())
                Log.d(TAG, code.toString())
            }

            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen")
                subscribe()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                setUpBtcPriceText(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                Log.d(TAG, reason.toString())
                Log.d(TAG, remote.toString())
                /*unsubscribe()*/
            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }

        }
    }

    private fun subscribe() {
        /* webSocketClient.send(
             "{\n" +
                     "    \"type\": \"subscribe\",\n" +
                     "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
                     "}"
         )*/

        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"channels\": [{ \"name\": \"test\" }]\n" +
                    "}"
        )
    }

    private fun setUpBtcPriceText(message: String?) {
        message?.let {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<WebSocketData> = moshi.adapter(WebSocketData::class.java)
            val bitcoin = adapter.fromJson(message)
            runOnUiThread { pruebaWebSockets.text = message }
        }
    }

    /*private fun unsubscribe() {
        webSocketClient.send(
                "{\n" +
                        "    \"type\": \"unsubscribe\",\n" +
                        "    \"channels\": [\"ticker\"]\n" +
                        "}"
        )
    }*/

    companion object {
        /*const val WEB_SOCKET_URL = "wss://ws-feed.pro.coinbase.com"*/
        const val WEB_SOCKET_URL = "ws://192.168.56.1:6001/app/12345asdfasdf?protocol=7&client=js&version=4.3.1&flash=false"
        const val TAG = "Tiburcio"
    }

}
