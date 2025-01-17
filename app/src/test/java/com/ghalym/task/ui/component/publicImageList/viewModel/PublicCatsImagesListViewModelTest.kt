package com.ghalym.task.ui.component.publicImageList.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ghalym.task.data.remote.RemoteResult

import com.ghalym.task.data.Repository
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.network.NoInternetException
import com.ghalym.task.network.RxSingleSchedulers
import com.ghalym.task.util.Constants
import com.ghalym.task.util.LiveDataTestUtil
import io.reactivex.Single
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.StringContains
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.lang.Exception

class PublicCatsImagesListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository
    private lateinit var viewModel: PublicCatsImagesListViewModel

    @Mock
    lateinit var observer: Observer<RemoteResult<Any>>


    @Before
    @Throws(java.lang.Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PublicCatsImagesListViewModel(repository, RxSingleSchedulers.TEST_SCHEDULER)
        viewModel.getLiveData().observeForever(observer)
    }

    @Test
    fun `check LiveData is NotNull`() {
        Mockito.`when`(repository.getAllPublicImages("1"))
            .thenReturn(null)
        Assert.assertNotNull(viewModel.getLiveData())
        Assert.assertTrue(viewModel.getLiveData().hasObservers())
    }

    @Test
    fun `Fetch Data from server with Success result`() {
        // arrange
        val expectedListView = ArrayList<CatImage>()

        Mockito.`when`(repository.getAllPublicImages("1")).thenReturn(Single.just(expectedListView))

        //act
        viewModel.getAllPublicImages()

        // verify
        verify(observer).onChanged(RemoteResult.InProgress)
        val observedLiveDataListView =
            (LiveDataTestUtil.getValue(viewModel.getLiveData()) as RemoteResult.Success).data
        Assert.assertEquals(expectedListView, observedLiveDataListView)
    }

    @Test
    fun `Fetch data with No Internet Connection exception happened`() {
        // arrange
        Mockito.`when`(repository.getAllPublicImages("1"))
            .thenReturn(Single.error(NoInternetException(Constants.NO_INTERNET_CONNECTION_MSG)))

        // act
        try {
            viewModel.getAllPublicImages()
        } catch (e: NoInternetException) {

            //verify
            assertThat(e.message, StringContains(Constants.NO_INTERNET_CONNECTION_MSG))
        }

    }

    @Test
    fun `Fetch data with general exception happened`() {
        // arrange
        Mockito.`when`(repository.getAllPublicImages("1"))
            .thenReturn(Single.error(Exception(Constants.GENERAL_ERROR_MSG)))
        try {

            // act
            viewModel.getAllPublicImages()
        } catch (e: Exception) {
            //verify
            assertThat(e.message, StringContains(Constants.GENERAL_ERROR_MSG))
        }

    }


    @Test
    fun `Check live data pagination`() {

        // arrange
        val expectedResult=2

        //act
        viewModel.changeImagesPage()

        //verify
        val returnedValue = LiveDataTestUtil.getValue(viewModel.getPageLiveData())
        Assert.assertEquals(expectedResult, returnedValue)
    }


}