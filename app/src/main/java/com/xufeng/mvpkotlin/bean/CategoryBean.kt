package com.xufeng.mvpkotlin.bean

import java.io.Serializable

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
data class CategoryBean(val id: Long,
                        val name: String,
                        val description: String,
                        val bgPicture: String,
                        val bgColor: String,
                        val headerImage: String) : Serializable