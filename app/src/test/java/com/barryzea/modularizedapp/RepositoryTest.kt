package com.barryzea.modularizedapp

import com.barryzea.data.database.db.MyDatabase
import com.barryzea.data.repository.MainRepository
import com.barryzea.models.model.ImageEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@HiltAndroidTest
class RepositoryTest {

    @get:Rule(order=0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: MainRepository

    @Inject
    lateinit var myDatabase: MyDatabase

    @Before
    fun setup(){
        hiltRule.inject()
    }

   @Test
   fun save_register()= runTest{
       val newRegister = ImageEntity(1,"","test description")
       val idInserted = repository.saveRegister(newRegister)
       assertEquals(idInserted,newRegister.id)
   }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}