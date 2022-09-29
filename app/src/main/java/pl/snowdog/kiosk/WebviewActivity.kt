package pl.snowdog.kiosk

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class WebviewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var reloadOnConnected: ReloadOnConnected
    private lateinit var adminComponentName: ComponentName
    private lateinit var policyManager: DevicePolicyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val defaultUrl = "https://on-system.net"

        val sharedPref = getSharedPreferences(getString(R.string.storage_key), Context.MODE_PRIVATE)?: return
        val url = sharedPref.getString(getString(R.string.url_key), defaultUrl)
        showInFullScreen(findViewById(R.id.root))
        initVars()
        setupWebView(url?:defaultUrl)
        listenToConnectionChange()
    }

    override fun onResume() {
        super.onResume()
        setupKiosk()
    }

    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack())
            webView.goBack()
        else
            super.onBackPressed()
    }

    override fun onDestroy() {
        if (::reloadOnConnected.isInitialized)
            reloadOnConnected.onActivityDestroy()
        super.onDestroy()
    }

    private fun setupKiosk() {
        if (policyManager.isDeviceOwnerApp(packageName)) {
            setupAutoStart()
            stayAwake()
            policyManager.setLockTaskPackages(adminComponentName, arrayOf(packageName))
        }
        startLockTask()
    }

    private fun setupAutoStart() {
        val intentFilter = IntentFilter(Intent.ACTION_MAIN)
        intentFilter.addCategory(Intent.CATEGORY_HOME)
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        policyManager.addPersistentPreferredActivity(
            adminComponentName,
            intentFilter, ComponentName(packageName, WebviewActivity::class.java.name)
        )
        policyManager.setKeyguardDisabled(adminComponentName, true)
    }

    private fun stayAwake() {
        policyManager.setGlobalSetting(
            adminComponentName,
            Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
            (BatteryManager.BATTERY_PLUGGED_AC
                    or BatteryManager.BATTERY_PLUGGED_USB
                    or BatteryManager.BATTERY_PLUGGED_WIRELESS).toString()
        )
    }

    private fun listenToConnectionChange() = reloadOnConnected.onActivityCreate(this)

    private fun setupWebView(url: String) {
        webView.webViewClient = WebViewClient()
        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            useWideViewPort = true
            userAgentString =  "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"
            loadWithOverviewMode = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            allowFileAccess = true
            allowContentAccess = true
        }
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.isScrollbarFadingEnabled = false
        webView.loadUrl(url)
    }

    private fun initVars() {
        webView = findViewById(R.id.webView)
        reloadOnConnected = ReloadOnConnected(webView)
        adminComponentName = MyDeviceAdminReceiver.getComponentName(this)
        policyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
    }

    private fun showInFullScreen(rootView: View) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, rootView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }


}