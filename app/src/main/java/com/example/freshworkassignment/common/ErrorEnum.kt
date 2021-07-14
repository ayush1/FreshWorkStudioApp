package com.example.freshworkassignment.common

enum class ErrorEnum (val msg : String) {
    UNABLE_TO_FETCH_DATA("Something went wrong..."),
    INTERNET_CONNECTION("You don't have internet connection"),
    NO_DATA_FOUND("You haven't marked any favorites")
}