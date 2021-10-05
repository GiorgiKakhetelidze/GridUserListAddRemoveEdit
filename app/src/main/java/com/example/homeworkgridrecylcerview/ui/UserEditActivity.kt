package com.example.homeworkgridrecylcerview.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkgridrecylcerview.databinding.ActivityUserEditBinding
import com.example.homeworkgridrecylcerview.model.User

class UserEditActivity : AppCompatActivity() {


    lateinit var binding: ActivityUserEditBinding
    private var imgResource: Int? = -1
    private var vipStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setListeners()
    }

    private fun init() {
        val data = intent.getParcelableExtra<User>(UserActivity.USER_KEY)
        data?.vipStatus?.let { setVip(it) }
        vipStatus = data?.vipStatus == true
        imgResource = data?.image
        binding.nameEditTxtView.setText(data?.name)
        binding.lastNameEditTxtView.setText(data?.lastName)
    }

    private fun setVip(isVip: Boolean) {
        if (isVip) binding.userVip.visibility = View.VISIBLE else binding.userVip.visibility =
            View.GONE
    }

    private fun setListeners() {
        binding.saveBtnView.setOnClickListener {
            saveAndGoToProfile()
        }
    }

    private fun saveAndGoToProfile() {
        val user = imgResource?.let {
            User(
                image = it,
                name = binding.nameEditTxtView.text.toString(),
                lastName = binding.lastNameEditTxtView.text.toString(),
                vipStatus = vipStatus
            )
        }
        val resultIntent = Intent()
        resultIntent.putExtra(KEY_EDITED_USER, user)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val KEY_EDITED_USER = "EDITED_USER"
    }
}