package com.gmail.illobikol.vk_api_playbill.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gmail.illobikol.vk_api_playbill.R
import com.gmail.illobikol.vk_api_playbill.activities.EventInfoActivity
import com.gmail.illobikol.vk_api_playbill.models.VKEventModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mSourceList: ArrayList<VKEventModel> = ArrayList()
    private var mEventsList: ArrayList<VKEventModel> = ArrayList()
    private var context: Context? = null

    fun EventsAdapter(context: Context?) {
        this.context = context
    }
    fun setupEvents(eventList:List<VKEventModel>){
        mSourceList.clear()
        mSourceList.addAll(eventList)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date())
        filter(query = "",currentDate,currentDate)
    }

    fun filter(query: String, dateStart: String, dateStop: String) {
        mEventsList.clear()
        val startDate = SimpleDateFormat("dd.MM.yyyy").parse(dateStart).time.toLong()/1000
        val stopDate = SimpleDateFormat("dd.MM.yyyy").parse(dateStop).time.toLong()/1000
        mSourceList.forEach {
            if (it.name.contains(query, ignoreCase = true)) {
                if(it.finish_date == ""){
                    if(it.start_date.toLong() in startDate..stopDate){
                        mEventsList.add(it)
                    }
                }else if(it.start_date.toLong() in startDate..stopDate||it.finish_date.toLong() in startDate..stopDate||
                    it.start_date.toLong()<=startDate&&it.finish_date.toLong()>=stopDate){
                    mEventsList.add(it)
                }

            }
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView = layoutInflater.inflate(R.layout.cell_event, parent, false)
        return EventsViewHolder(itemView = itemView)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EventsViewHolder){
            holder.bind(vkEventModel = mEventsList[position])
        }
        holder.itemView.setOnClickListener {
            Log.e("CELL",mEventsList[position].name)
            var mTxtDateTime: TextView = holder.itemView.findViewById(R.id.event_txt_date_time)
            val intent = Intent(holder.itemView.context, EventInfoActivity::class.java).apply {
                putExtra("NAME",mEventsList[position].name)
                putExtra("PHOTO",mEventsList[position].photo_200)
                putExtra("DATE_START", mTxtDateTime.text)
                putExtra("DESCRIPTION",mEventsList[position].description)
                putExtra("ID",mEventsList[position].id.toString())
            }
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return mEventsList.count()
    }

    class EventsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var mCivAvatar: CircleImageView = itemView.findViewById(R.id.event_civ_avatar)
        private var mTxtEventName: TextView = itemView.findViewById(R.id.event_txt_name)
        private var mTxtDateTime: TextView = itemView.findViewById(R.id.event_txt_date_time)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bind(vkEventModel: VKEventModel){

            vkEventModel.photo_100?.let{ url->
                Picasso.get().load(url)
                    .into(mCivAvatar)
            }
            if(vkEventModel.finish_date!="" && vkEventModel.start_date!=vkEventModel.finish_date){
                mTxtDateTime.text = "С ${getDateTime(vkEventModel.start_date)} до ${getDateTime(vkEventModel.finish_date)}"
            }else if(vkEventModel.finish_date!="" && vkEventModel.start_date==vkEventModel.finish_date){
                mTxtDateTime.text = "С ${getDateTime(vkEventModel.start_date)} до ${getTime(vkEventModel.finish_date)}"
            } else{
                mTxtDateTime.text = "${getDateTime(vkEventModel.start_date)}"
            }
            mTxtEventName.text = vkEventModel.name
        }

        private fun getDateTime(s: String): String? {
            try {
                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
                val netDate = Date((s).toLong() * 1000)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

        private fun getTime(s: String): String? {
            try {
                val sdf = SimpleDateFormat("HH:mm")
                val netDate = Date((s).toLong() * 1000)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }
    }
}