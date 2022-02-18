package com.xiangxue.composemvvm.main

import com.xiangxue.composemvvm.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String, var messageCount: Int = 0) {
    object Home : NavigationItem("home", R.drawable.ic_home, "头条")
    object Movies : NavigationItem("movies", R.drawable.ic_movie, "电影")
    object Books : NavigationItem("books", R.drawable.ic_book, "书籍")
    object Profile : NavigationItem("profile", R.drawable.ic_profile, "我的", messageCount = 10)
}