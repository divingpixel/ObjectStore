# Object Store

## Tech stack:

**Kotlin** - as required, futureproof and recommended by Google for Android development

**Hilt** - for dependency injection, somewhat better than Dagger on which is based on, easier to implement, less boilerplate

**Room Database** - for data persistence, the logical choice for this kind of task, because it’s tailored for use with Android framework, it’s fast and the API is easy to implement

**Jetpack Coroutines** - as the native solution for asynchronous and reactive programming, integrated in Kotlin language, fast and efficient resource-wise

**Jetpack Compose** - the latest UI paradigm for Android, that allows easy creation of reactive layouts, and also, in case of multiplatform development, requires minimum effort to be deployed on different operating systems

**Jetpack Navigation** - used to simplify the UI architecture, so that the app can be a single activity and by using compose, also no fragments where needed

**Jetpack ViewModel** - to implement the MVVM architecture, and the easiest way to implement separation of concerns between the app’s different parts (in a different paradigm, activities or fragments) 

The app has been modularised, based on CLEAN architecture recommandations of layer separation, but at the same time minimising the boilerplate, and creating only the layers/modules that are strictly necessary.

As seen in this document : https://developer.android.com/topic/architecture, the domain layer was considered optional, but, when needed it can be easily implemented.

Also I took in account the SOLID principles, but improvements could be done further, especially regarding Single Responsability for the ViewModels, that could have been split further to handle the dialogs, but taking in consideration the time limit, I’ve decided to have them handle that logic also. The other principles are also respected, but in case of Open CLose and Interface Segregation no use case was necessary.
