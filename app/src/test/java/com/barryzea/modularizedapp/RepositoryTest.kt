package com.barryzea.modularizedapp

import com.barryzea.data.database.db.MyDatabase
import com.barryzea.data.repository.MainRepositoryImpl
import com.barryzea.models.model.Note
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
    lateinit var repository: MainRepositoryImpl

    @Inject
    lateinit var myDatabase: MyDatabase

    @Before
    fun setup(){
        hiltRule.inject()

    }
    @Test
    fun get_all_registers() = runTest {
        val newRegister = Note(1,"","test description")

        repository.saveRegister(newRegister)

        val fetchRegisters = repository.getAllRegisters()
        assertEquals(newRegister,fetchRegisters.first())

    }
   @Test
   fun save_register()= runTest{
       val newRegister = Note(1,"","test description")
       val idInserted = repository.saveRegister(newRegister)
       assertEquals(idInserted,newRegister.idNote)
   }
    @Test
    fun update_register() = runTest {
        val newRegister = Note(1,"","test description")
        repository.saveRegister(newRegister)

        val updateRegister = Note(1,"","test update description")
        val rowUpdated = repository.updateRegister(updateRegister)
        assertEquals(rowUpdated,1)

    }
    @Test
    fun delete_register() = runTest{

        val otherRegister = Note(2,"","test for delete description")
        repository.saveRegister(otherRegister)

        val rowDeleted = repository.deleteRegister(otherRegister.idNote)

        assertEquals(rowDeleted,1)
    }

}
