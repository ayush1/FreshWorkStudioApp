package com.example.freshworkassignment.common

class Constants {

    enum class ClickEventName {
        FAVOURITE_EVENT, UPDATE_DATA_EVENT
    }

    enum class ErrorEnum (val msg : String) {
        UNABLE_TO_FETCH_DATA("Something went wrong..."),
        INTERNET_CONNECTION("You don't have internet connection"),
    }

    enum class EmptyEnum (val msg : String) {
        NO_FAVORITE_DATA_FOUND("You haven't marked any favorites"),
        NO_SEARCH_DATA_FOUND("Please try different search")
    }

    companion object {
        const val TABLE_NAME = "FavoriteTable"
    }
}