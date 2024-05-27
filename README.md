# ComposeOTT Sample
Welcome to the OTT App for Android, a sample project demonstrating a simple Over-The-Top (OTT) media service application with two primary screens. This app showcases content in a grid layout and includes a search functionality for users to find specific content. Below are the details and features of the app.

## Description 
* UI 
   * [Compose](https://developer.android.com/jetpack/compose) declarative UI framework

* Tech/Tools
    * [Kotlin](https://kotlinlang.org/) 100% coverage
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose) 
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for navigation between composables
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
        * [Paging 3](https://developer.android.com/jetpack/compose](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) 
    * [Coil](https://github.com/coil-kt/coil) for image loading
    
* Modern Architecture
    * Single activity architecture (with [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)) that defines navigation graphs
    * MVVM
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))

## Features
## Main Screen
__Grid Layout:__ Displays content in a grid layout that supports both portrait and landscape modes.\
__Collapsible Toolbar:__ Includes a toolbar with a back button and a search icon.\
__Local Data Fetching:__ Fetches data locally from a JSON format file.\
__Pagination:__ Implements pagination to load data incrementally as the user scrolls.\
<img width="309" alt="Screenshot 2024-05-27 at 6 15 20 PM" src="https://github.com/amanvsh/ComposeOTT/assets/6770540/6a8b1ba3-b1f5-4c72-bcd5-b34027f0f89c">

## Search Screen
__Search Functionality:__ Allows users to search for content using an EditText field.\
__Search Behavior:__ Initiates a search request after the user has entered at least 3 characters.\
__Grid Display:__ Displays search results in a grid layout that supports both portrait and landscape modes.\
__Clear Search:__ Features a cross button on the top right to clear the search input.\
<img width="301" alt="Screenshot 2024-05-27 at 6 43 43 PM" src="https://github.com/amanvsh/ComposeOTT/assets/6770540/637a390d-b9ae-4711-9826-d2f22eaca47c">



