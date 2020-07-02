package com.ghalym.task.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ghalym.task.data.Repository
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.util.Constants
import io.reactivex.Single
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.junit.Rule
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.lang.Exception


@RunWith(JUnit4::class)
class RepositoryTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    @Before
    @Throws(java.lang.Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `check RemoteRepository is NotNull`() {
        assertNotNull(repository)
    }

    @Test
    fun `Fetch Data from retrofit api with Success result`() {
        // arrange
        val returnedObject = ArrayList<CatImage>()

        val expectedListView = Single.just(returnedObject)

        `when`(repository.getAllPublicImages("1")).thenReturn(expectedListView)

        //act
        val returned: Single<ArrayList<CatImage>> = repository.getAllPublicImages("1")

        // verify
        assertEquals(expectedListView, returned)
    }


    @Test
    fun `Fetch Data from retrofit api with error result`() {
        // arrange
        val expectedResult = Single.error<ArrayList<CatImage>>(Exception(Constants.GENERAL_ERROR_MSG))
        `when`(repository.getAllPublicImages("1")).thenReturn(expectedResult)

        //act
        val returned: Single<ArrayList<CatImage>> = repository.getAllPublicImages("1")

        // verify
        assertEquals(expectedResult, returned)
    }

}