package com.gmail.illobikol.vk_api_playbill.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.gmail.illobikol.vk_api_playbill.R
import com.gmail.illobikol.vk_api_playbill.adapters.EventsAdapter
import com.gmail.illobikol.vk_api_playbill.models.VKEventModel
import com.gmail.illobikol.vk_api_playbill.presenters.EventsPresenter
import com.gmail.illobikol.vk_api_playbill.views.EventsView
import java.text.SimpleDateFormat
import java.util.*

class EventsActivity : MvpAppCompatActivity(), EventsView {

    @InjectPresenter
    lateinit var eventsPresenter: EventsPresenter

    private lateinit var mRvEvents:RecyclerView
    private lateinit var mTxtNoItems:TextView
    private lateinit var mTxtStartEvent:TextView
    private lateinit var mTxtStopEvent:TextView
    private lateinit var mCpvWait: CircularProgressView
    private lateinit var mAdapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        supportActionBar?.hide()
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        mRvEvents = findViewById(R.id.recycler_events)
        mTxtNoItems = findViewById(R.id.txt_no_items)
        mTxtStartEvent = findViewById(R.id.txt_start_events)
        mTxtStopEvent = findViewById(R.id.txt_stop_events)
        mCpvWait = findViewById(R.id.cpv_events)
        eventsPresenter.loadEvents()
        mAdapter = EventsAdapter()
        mRvEvents.adapter = mAdapter
        mRvEvents.layoutManager = LinearLayoutManager(applicationContext)
        mRvEvents.setHasFixedSize(true)

        setStartDate(day,month,year)
        setStopDate(day,month,year)

        val mTxtSearch: EditText = findViewById(R.id.txt_friends_search)

        mTxtSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mAdapter.filter(p0.toString(),mTxtStartEvent.text.toString(),mTxtStopEvent.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        mTxtStopEvent.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                if(getDate(mTxtStartEvent.text.toString(),"${mDay}.${mMonth+1}.${mYear}") == true){
                setStopDate(mDay,mMonth,mYear)
                mAdapter.filter(mTxtSearch.text.toString() ,mTxtStartEvent.text.toString(),mTxtStopEvent.text.toString())}
                else{
                    Toast.makeText(this, "Дата начала ивента должна/n быть раньше даты окончания", Toast.LENGTH_SHORT).show()
                }
            },year, month,day)
            dpd.show()

        }

        mTxtStartEvent.setOnClickListener{

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                if(getDate("${mDay}.${mMonth+1}.${mYear}",mTxtStopEvent.text.toString()) == true){
                    setStartDate(mDay,mMonth,mYear)
                    mAdapter.filter(mTxtSearch.text.toString(), mTxtStartEvent.text.toString(),mTxtStopEvent.text.toString())
                }
                else{
                    Toast.makeText(this, "Дата начала ивента должна быть раньше даты окончания", Toast.LENGTH_SHORT).show()
                }
            },year, month,day)
            dpd.show()
        }
    }

    override fun showError(textResource: Int) {
        mTxtNoItems.text = getString(textResource)
    }

    override fun setupEmptyList() {
        mRvEvents.visibility = View.GONE
        mTxtNoItems.visibility = View.VISIBLE
    }

    override fun setupEventsList(eventsList: List<VKEventModel>) {
        mRvEvents.visibility = View.VISIBLE
        mTxtNoItems.visibility = View.GONE

        mAdapter.setupEvents(eventsList)

    }

    override fun startLoading(isLoading: Boolean) {
        if (isLoading) {
            mRvEvents.visibility = View.GONE
            mTxtNoItems.visibility = View.GONE
            mCpvWait.visibility = View.VISIBLE
        } else {
            mCpvWait.visibility = View.GONE
        }
    }
    fun setStopDate(day: Int,month: Int,year: Int){
        if(month >= 10){
            mTxtStopEvent.text = "$day.${month+1}.$year"
        }else{
            mTxtStopEvent.text = "$day.0${month+1}.$year"
        }

    }

    fun setStartDate(day: Int,month: Int,year: Int){
        if(month >= 10){
            mTxtStartEvent.text = "$day.${month+1}.$year"
        }else{
            mTxtStartEvent.text = "$day.0${month+1}.$year"
        }

    }
    private fun getDate(dateStart: String, dateStop:String): Boolean? {
        val startDate = SimpleDateFormat("dd.MM.yyyy").parse(dateStart).time.toLong()/1000
        val stopDate = SimpleDateFormat("dd.MM.yyyy").parse(dateStop).time.toLong()/1000
        Log.e("TAG", "${startDate} и ${stopDate}")
        return startDate.toInt() < stopDate.toInt()

    }
}