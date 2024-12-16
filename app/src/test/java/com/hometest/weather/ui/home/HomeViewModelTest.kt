package com.hometest.weather.ui.home

import android.content.SharedPreferences
import app.cash.turbine.test
import com.hometest.weather.data.common.Resource
import com.hometest.weather.data.remote.dto.*
import com.hometest.weather.data.repository.TemperatureUnitRepository
import com.hometest.weather.domain.usecases.GetWeatherUseCase
import com.hometest.weather.domain.utils.TemperatureUnit
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // Mock dependencies
    private val getWeatherUseCase: GetWeatherUseCase = mockk()
    private val temperatureUnitRepository: TemperatureUnitRepository = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk()

    // Test dispatcher
    private val testDispatcher = StandardTestDispatcher()

    // Subject under test
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Mocking sharedPreferences behavior
        every { sharedPreferences.getString("last_search", null) } returns null
        every { sharedPreferences.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.apply() } just Runs

        // Mock temperature unit repository behavior
        every { temperatureUnitRepository.getTemperatureUnit() } returns TemperatureUnit.CELSIUS

        // Initialize the ViewModel
        viewModel = HomeViewModel(
            getWeatherUseCase = getWeatherUseCase,
            temperatureUnitRepository = temperatureUnitRepository,
            sharedPreferences = sharedPreferences
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit loading and success states when weather data is fetched successfully`() = runTest {
        val weatherResponse = createDummyWeatherResponse()
        coEvery { getWeatherUseCase("Pune") } returns flowOf(Resource.Loading(), Resource.Success(weatherResponse))

        viewModel.getWeather("Pune")

        viewModel.homeState.test {
            assertTrue(awaitItem().isLoading)
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertTrue(successState.weatherState is Resource.Success)
            assertEquals(weatherResponse, (successState.weatherState as Resource.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit loading and error states when HttpException is thrown`() = runTest {
        val exception = HttpException(Response.error<Any>(400, "".toResponseBody(null)))
        coEvery { getWeatherUseCase("Pune") } returns flowOf(Resource.Loading(), Resource.Error(exception.message()))

        viewModel.getWeather("Pune")

        viewModel.homeState.test {
            assertTrue(awaitItem().isLoading)
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals("Response.error()", errorState.error)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `should emit loading and error states when IOException is thrown`() = runTest {
        coEvery { getWeatherUseCase("Pune") } returns flowOf(
            Resource.Loading(),
            Resource.Error("Couldn't reach server. Check your internet connection.")
        )

        viewModel.getWeather("Pune")

        viewModel.homeState.test {
            assertTrue(awaitItem().isLoading)
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals("Couldn't reach server. Check your internet connection.", errorState.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should toggle temperature unit and update detail state`() = runTest {
        justRun { temperatureUnitRepository.saveTemperatureUnit(any()) }

        // Initial unit is Celsius
        assertEquals(TemperatureUnit.CELSIUS, viewModel.temperatureUnit.value)

        viewModel.toggleTemperatureUnit()

        // Verify unit is toggled to Fahrenheit
        assertEquals(TemperatureUnit.FAHRENHEIT, viewModel.temperatureUnit.value)
        verify { temperatureUnitRepository.saveTemperatureUnit(TemperatureUnit.FAHRENHEIT) }
    }

    private fun createDummyWeatherResponse() = WeatherResponse(
        location = Location(
            name = "Pune",
            region = "Maharashtra",
            country = "India",
            latitude = 18.5204f,
            longitude = 73.8567f
        ),
        current = CurrentWeather(
            temperature = 25.0f,
            condition = WeatherCondition(description = "Sunny", iconUrl = "", conditionCode = 1000),
            humidity = 60,
            uvIndex = 5.0f,
            feelsLike = 27.0f
        )
    )
}
