package com.example.homeworkgridrecylcerview.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkgridrecylcerview.R
import com.example.homeworkgridrecylcerview.databinding.ActivityUserAddBinding
import com.example.homeworkgridrecylcerview.model.User

class UserAddActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserAddBinding
    private var imgResource = -1
    private var isVip = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setListeners()
    }

    private fun init() {
        val vipStatus = intent.getBooleanExtra(UserActivity.VIP_STATUS, false)
        isVip = vipStatus
        imgResource = if (vipStatus) R.drawable.image2 else R.drawable.image1
        setVip(vipStatus)
    }

    private fun setVip(isVip: Boolean) {
        binding.userVip.visibility = if (isVip) View.VISIBLE else View.INVISIBLE
    }

    private fun setListeners() {
        binding.addBtnView.setOnClickListener {
            if (checkValidity())
                saveAndGoToHomeScreen()
        }
    }

    private fun saveAndGoToHomeScreen() {

        val user = User(
            image = imgResource,
            name = binding.nameEditTxtView.text.toString(),
            lastName = binding.lastNameEditTxtView.text.toString(),
            vipStatus = isVip
        )
        val resultIntent = Intent().apply {
            putExtra(ADDED_USER, user)
        }
        setResult(RESULT_FIRST_USER, resultIntent)
        finish()
    }

    private fun checkValidity(): Boolean {
        return when {
            binding.nameEditTxtView.text.isNullOrEmpty() ||
                    binding.lastNameEditTxtView.text.isNullOrEmpty() -> {
                Toast.makeText(this, getString(R.string.pls_fill_fields), Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                Toast.makeText(this, getString(R.string.sucess), Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    companion object {
        const val ADDED_USER = "ADD"
    }
}