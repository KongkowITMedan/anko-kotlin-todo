package com.zigic.todolist.rest.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by zigic on 21/09/17.
 */


class Task (

    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("content")
    @Expose
    var content: String = "",
    @SerializedName("editable")
    @Expose
    var editable: Boolean = false,
    @SerializedName("complete")
    @Expose
    var complete: Boolean = false

)