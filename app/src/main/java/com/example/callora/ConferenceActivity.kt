package com.example.callora


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment


class ConferenceActivity : AppCompatActivity() {

    lateinit var meetingID: String
    lateinit var username: String
    lateinit var meetingIDTextView: TextView
    lateinit var shareBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conference)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        meetingIDTextView = findViewById(R.id.meeting_id_textview)
        shareBtn = findViewById(R.id.share_btn_imageview)

        meetingID = intent.getStringExtra("MEETING_ID").toString()
        username = intent.getStringExtra("USERNAME").toString()

        meetingIDTextView.setText("MEETING ID : " + meetingID)
        addFragment()

        shareBtn.setOnClickListener() {
            var shareMessage = "Join meeting in Callora \n Meeting ID : " + meetingID
            var shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareMessage)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "share via"))
        }

    }

    fun addFragment() {
        val appID: Long = AppConstants.appID
        val appSign: String = AppConstants.appSign

        var conferenceID: String = meetingID
        var userID: String = username
        var userName: String = username

        val config = ZegoUIKitPrebuiltVideoConferenceConfig()
        val fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(
            appID,
            appSign,
            userID,
            userName,
            conferenceID,
            config
        )

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }
}