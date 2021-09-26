package com.davilav.wigilabstest.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davilav.wigilabstest.data.repository.movie.MovieRepositoryImpl
import com.davilav.wigilabstest.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var movieViewModel: MovieViewModel

    @Mock
    var movieRepositoryImpl: MovieRepositoryImpl = Mockito.mock(MovieRepositoryImpl::class.java)

    @Before
    fun onSetup() {
        MockitoAnnotations.initMocks(this)
        movieViewModel = MovieViewModel(movieRepositoryImpl)
    }

}