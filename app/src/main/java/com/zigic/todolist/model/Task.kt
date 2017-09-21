package com.zigic.todolist.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by zigic on 21/09/17.
 */


class Task {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("content")
    @Expose
    var content: String? = null
    @SerializedName("editable")
    @Expose
    var editable: Boolean? = null
    @SerializedName("complete")
    @Expose
    var complete: Boolean? = null

}