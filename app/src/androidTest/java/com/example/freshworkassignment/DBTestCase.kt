package com.example.freshworkassignment

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.freshworkassignment.db.AppDatabase
import com.example.freshworkassignment.db.entity.Favourites
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DBTestCase {

    private lateinit var favGif : Favourites
    private lateinit var appDatabase: AppDatabase

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java).build()
    }

    @After
    fun closeDb(){
        appDatabase.close()
    }

    //Test case - test data for insertion in DB
    @Test
    fun testCaseForInsertInDb(){
        initFavoriteGif()
        val favoriteDao = appDatabase.favouriteDao()
        favoriteDao.insert(favGif)
        val favourites = favoriteDao.getAll()
        assert(favourites.isNotEmpty())
    }

    //Test case - test data for fetching from DB
    @Test
    fun testCaseForFetchFromDb(){
        initFavoriteGif()
        val favoriteDao = appDatabase.favouriteDao()
        favoriteDao.insert(favGif)
        favoriteDao.getAll()
        assert(favoriteDao.getAll().size == 1)
    }

    //Test case - test data for delete gif
    @Test
    fun testCaseForDeleteFromDb(){
        initFavoriteGif()
        val favoriteDao = appDatabase.favouriteDao()
        favoriteDao.insert(favGif)
        favoriteDao.delete(favGif)
        assert(favoriteDao.getAll().isEmpty())
    }

    private fun initFavoriteGif() {
        favGif = Favourites("abc123def", "gif", "Testing Gif",
            "https://media1.giphy.com/media/ZYENqjb4a515zYWgNS/200.gif?cid=75a5bf084dqv0uj0k808c1ir33hknwdtkl7v9m45w08kglpa&rid=200.gif&ct=g",
            true)
    }
}