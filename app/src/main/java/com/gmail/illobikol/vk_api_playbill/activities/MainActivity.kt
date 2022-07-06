package com.gmail.illobikol.vk_api_playbill.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.gmail.illobikol.vk_api_playbill.R
import com.gmail.illobikol.vk_api_playbill.presenters.LoginPresenter
import com.gmail.illobikol.vk_api_playbill.views.LoginView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope


class MainActivity : MvpAppCompatActivity(), LoginView {

    private val TAG: String = MainActivity::class.java.simpleName
    private lateinit var mTxtHello: TextView
    private  lateinit var  mBtnEnter: Button
    private  lateinit var  mCpvWait: CircularProgressView

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        mTxtHello = findViewById(R.id.txt_login_hello)
        mBtnEnter = findViewById(R.id.btn_login_enter)
        mCpvWait = findViewById(R.id.cpv_login)

        if (VK.isLoggedIn()) {
            startActivity(Intent(this, EventsActivity::class.java))
            finish()
        }

        mBtnEnter.setOnClickListener {
            VK.login(this@MainActivity, listOf(VKScope.GROUPS))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(!loginPresenter.loginVk(requestCode = requestCode, resultCode = resultCode, data = data)){
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startLoading() {
        mBtnEnter.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mBtnEnter.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun openEvents() {
        startActivity(Intent(applicationContext, EventsActivity::class.java))
        finish()
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }
}