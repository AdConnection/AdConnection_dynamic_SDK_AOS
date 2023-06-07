package one.adconnection.addemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import one.adconnection.sdk.AdConnector
import one.adconnection.sdk.AdConnectorDynamicListener

class DynamicInterstitialActivity : AppCompatActivity() {

    lateinit var adConnector: AdConnector
    var adView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dynamic_interstitial)

        adConnector = AdConnector(this, "1M5D7Z40mis1")
    }

    fun onClick(v: View) {
        if (v.id == R.id.request) {
            requestDynamicBanner()
        } else if(v.id == R.id.add) {
            addDynamicBanner()
        }
    }

    // 띠배너 광고 요청
    private fun requestDynamicBanner() {
        val listener: AdConnectorDynamicListener = object : AdConnectorDynamicListener {

            override fun onReceiveAd(v: View) {
                // 광고 수신 성공시 호출됩니다.
                Log.d("ADConnection", "[Dynamic] onReceiveAd ")
                adView = v
            }

            override fun onFailedToReceiveAd(error: String?) {
                // 광고 수신 실패시 호출됩니다.
                Log.d("ADConnection", "[Dynamic] onFailedToReceiveAd : $error")
            }

            override fun onClickAd() {
                // 광고 클릭시 호출됩니다.
                Log.d("ADConnection", "[Dynamic] onClickAd ")
            }
        }

        if (adConnector != null) adConnector.requestDynamicIntestitialView(320, 480, listener)
    }

    private fun addDynamicBanner() {
        if(adView != null) {
            val vg = findViewById<View>(R.id.ad_container) as ViewGroup
            vg.addView(adView)
        }
    }

    override fun onResume() {
        super.onResume()
        if(adConnector!= null) {
            adConnector.resume(this)
        }
    }

    override fun onPause() {
        if(adConnector!= null) {
            adConnector.pause(this)
        }
        super.onPause()
    }

    override fun onDestroy() {
        if(adConnector!= null) {
            adConnector.destroy(this)
        }
        super.onDestroy()
    }
}