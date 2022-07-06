package com.gmail.illobikol.vk_api_playbill.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.gmail.illobikol.vk_api_playbill.R
import com.gmail.illobikol.vk_api_playbill.models.*
import com.squareup.picasso.Picasso
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import de.hdodenhof.circleimageview.CircleImageView

class EventInfoActivity : AppCompatActivity() {
    private lateinit var mTxtNameInfo: TextView
    private lateinit var mTxtDateInfo: TextView
    private lateinit var mTxtPhotoInfo: CircleImageView
    private lateinit var mTxtDescriptionInfo: TextView
    private lateinit var mBtnJoin: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        supportActionBar?.hide()
        mBtnJoin = findViewById(R.id.eventInfo_btn_join)
        mTxtNameInfo = findViewById(R.id.eventInfo_txt_name)
        mTxtDateInfo = findViewById(R.id.eventInfo_txt_date)
        mTxtPhotoInfo = findViewById(R.id.eventInfo_civ_avatar)
        mTxtDescriptionInfo = findViewById(R.id.eventInfo_txt_description)

        var i = intent
        if(i!= null){
            mTxtNameInfo.text = i.getCharSequenceExtra("NAME")
            mTxtDateInfo.text = i.getCharSequenceExtra("DATE_START")
            Picasso.get().load(i.getCharSequenceExtra("PHOTO").toString()).into(mTxtPhotoInfo)
            mTxtDescriptionInfo.text = i.getCharSequenceExtra("DESCRIPTION")
        }

        mBtnJoin.setOnClickListener{

            VK.execute(VKJoinRequest(i.getCharSequenceExtra("ID").toString()),object: VKApiCallback<List<VKJoinModel>>{

                override fun success(result: List<VKJoinModel>) {
                    Toast.makeText(this@EventInfoActivity, "Вы успешно подписались на встречу", Toast.LENGTH_SHORT).show()
                    mBtnJoin.visibility = View.GONE
                }

                override fun fail(error: Exception) {
                    Toast.makeText(this@EventInfoActivity, "Вы уже подписаны на встречу", Toast.LENGTH_SHORT).show()
                    mBtnJoin.visibility = View.GONE
                }
            })       }

    }
}