package com.example.homeworkgridrecylcerview.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homeworkgridrecylcerview.R
import com.example.homeworkgridrecylcerview.data.DataSource
import com.example.homeworkgridrecylcerview.databinding.ActivityUserBinding
import com.example.homeworkgridrecylcerview.model.User

class UserActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserBinding
    private val adapter = UserAdapter()
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecycler()
    }

    private fun setRecycler() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
        adapter.userList = DataSource.users
        adapter.editClick = { user, position ->
            this.position = position
            navigateToEditScreenForResult(user)
        }
        adapter.addClick = { isVip ->
            navigateToAddScreenForResult(isVip)
        }

    }

    private fun navigateToAddScreenForResult(isVip : Boolean){
        val intent = Intent(this, UserAddActivity::class.java).apply {
            putExtra(VIP_STATUS, isVip)
        }
        resultLauncher.launch(intent)
    }

    private fun navigateToEditScreenForResult(user: User) {
        val intent = Intent(this, UserEditActivity::class.java).apply {
            putExtra(USER_KEY, user)
        }
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val user = result.data?.getParcelableExtra<User>(UserEditActivity.KEY_EDITED_USER)
                    setDataFromResultEdit(user)
                }
                Activity.RESULT_FIRST_USER -> {
                    val user = result.data?.getParcelableExtra<User>(UserAddActivity.ADDED_USER)
                    setDataFromResultAdd(user)
                }
                else -> {
                    Toast.makeText(this, getString(R.string.no_result), Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun setDataFromResultEdit(user: User?) {
        if (user != null) {
            adapter.userList[position] = user
        }
        adapter.notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataFromResultAdd(user: User?) {
        if (user != null) {
            adapter.userList.add(user)
        }
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val USER_KEY = "USER"
        const val VIP_STATUS = "VIP"
    }

}