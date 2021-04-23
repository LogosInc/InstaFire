package com.example.instafire

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instafire.databinding.ItemPostBinding
import com.example.instafire.models.Post
import java.math.BigInteger
import java.security.MessageDigest

class PostsAdapter (val context: Context, val posts: List<Post>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            val username = post.user?.username as String
            binding.tvUsername.text = post.user?.username
            binding.tvDescription.text = post.description
            Glide.with(context).load(post.imageurl).into(binding.ivPost)
            Glide.with(context).load(getProfileImageUrl(username)).into(binding.ivProfileImage)
            binding.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)
        }

        private fun getProfileImageUrl(username: String): String {
            val digest = MessageDigest.getInstance("MD5")
            val hash = digest.digest(username.toByteArray())
            val bigInt = BigInteger(hash)
            val hex = bigInt.abs().toString(16)
            return "https://www.gravatar.com/avatar/$hex?d=identicon"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size
}