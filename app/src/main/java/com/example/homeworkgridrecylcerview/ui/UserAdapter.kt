package com.example.homeworkgridrecylcerview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkgridrecylcerview.databinding.ItemUserBinding
import com.example.homeworkgridrecylcerview.databinding.ItemUserVipBinding
import com.example.homeworkgridrecylcerview.model.User

typealias onEditCLick = (user: User, position: Int) -> Unit
typealias onAddClick = (isVip : Boolean) -> Unit

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userList = mutableListOf<User>()

    lateinit var editClick: onEditCLick
    lateinit var addClick: onAddClick

    override fun getItemViewType(position: Int) = if (userList[position].vipStatus) VIP_USER else USER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == VIP_USER) {
            ItemUserVipViewHolder(
                ItemUserVipBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        } else {
            ItemUserViewHolder(
                ItemUserBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemUserVipViewHolder -> {
                holder.setListeners()
                holder.bind()
            }
            is ItemUserViewHolder -> {
                holder.setListeners()
                holder.bind()
            }
        }
    }

    override fun getItemCount() = userList.size

    inner class ItemUserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var curUser: User

        fun bind() {
            curUser = userList[adapterPosition]
            binding.imgView.setImageResource(curUser.image)
            binding.nameTxtView.text = curUser.name
            binding.lastNameTxtView.text = curUser.lastName
        }

        fun setListeners() {
            binding.deleteImgBtn.setOnClickListener {
                delete()
            }

            binding.editImgBtn.setOnClickListener {
                editClick.invoke(userList[adapterPosition], adapterPosition)
            }

            binding.addImgBtn.setOnClickListener {
                addClick.invoke(userList[adapterPosition].vipStatus)
            }
        }

        private fun delete() {
            userList.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }
    }

    inner class ItemUserVipViewHolder(private val binding: ItemUserVipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var curUser: User

        fun bind() {
            curUser = userList[adapterPosition]
            binding.imgView.setImageResource(curUser.image)
            binding.nameTxtView.text = curUser.name
            binding.lastNameTxtView.text = curUser.lastName
        }

        fun setListeners() {
            binding.deleteImgBtn.setOnClickListener {
                delete()
            }

            binding.editImgBtn.setOnClickListener {
                editClick.invoke(userList[adapterPosition], adapterPosition)
            }

            binding.addImgBtn.setOnClickListener {
                addClick.invoke(userList[adapterPosition].vipStatus)
            }
        }

        private fun delete() {
            userList.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }
    }

    companion object {
        const val USER = 2
        const val VIP_USER = 1
    }

}