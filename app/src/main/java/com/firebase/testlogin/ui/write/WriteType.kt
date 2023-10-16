package com.firebase.testlogin.ui.write

enum class WriteType {
    TEMPLATE, ADD;

    companion object {
        fun from(name: String?): WriteType? {
            return WriteType.values().find {
                it.name.uppercase() == name?.uppercase()
            }
        }
    }


}