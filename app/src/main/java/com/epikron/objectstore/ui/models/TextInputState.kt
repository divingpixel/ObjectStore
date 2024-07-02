package com.epikron.objectstore.ui.models

data class TextInputState(
    val text: String,
    val isError: Boolean
) {
    companion object{
        val preview = TextInputState (
            text = "Hello world",
            isError = false,
        )
        val default = TextInputState (
            text = "",
            isError = false,
        )
    }
}