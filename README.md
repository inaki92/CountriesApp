# CountriesApp

Technologies:
- RxJava, RxAndroid, RxKotlin
- Glide
- Koin (dependency injection)
- Room Database (persist data)
- Retrofit
- OkHttp3
- MVVM architecture
- ViewModel and LiveData
- Recycler view with cardView

Instructions:

Online
- First screen is a splash screen that displays the logo
- Second screen you have a search bar at the top with a recycler view at the bottom:
      - Click the search bar and start typing the country name and the recyclerView will be refreshing it self showing the
        countries that contains those letters.
      - Click on any country item on the recycler view will send you to another screen.
- Third screen is showing the details of the country it contains a save button, which aloows you to save the country.

Offline
- The splash screen with the logo
- The next screen will show a recycler view with all the countries that you saved in the online mode.The search bar is 
  disable in this screen.
- Clicking any object will send you to the details screen, fetching the data from the local database.

I followed the MVVM pattern using the viewModel to manage the data as well as LiveData. 
The network connection is manage vy retrofit with RxJava using the extension function on RxKotlin and the schedulers from
RxAndroid. Cache data implemented with Okhttp client.

The connection, viewmodel, and database are being injected using Koin, another dependency injection library that works easily
and smoothly on Kotlin.

Using the Jetpack components I took advantage of the Room database to store the data locally.
