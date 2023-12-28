# **Description**
Android App for creating exercises / tracking workouts.

## **Testing**
Homer Simpson account is seeded with some data for testing purposes.

Login in with:

Email: homer@simpson.com
Password: secret

## **Youtube Presentation**
<https://youtu.be/4Fe66WMwpJQ>

## **Backend URL**
<https://myfitnesstrainer-backend.onrender.com>

## **Personal Statement**
[Mobile Assignment 2 Personal Statement.docx](https://github.com/Maxi937/myPersonalTrainer-MobileApp/files/13779757/Mobile.Assignment.2.Personal.Statement.docx)

## **UML**
![Uml](https://github.com/Maxi937/myPersonalTrainer-MobileApp/assets/70072337/c3309515-6c19-481b-b32a-2e7377472008)


## **Features**
- Swipe to delete exercise
- Add exercises / complete workouts / create workouts
- Jetpack Compose migration
- Jetpack Navigation component
- Account Manager
- Theme swapping and state save
- Filtering Workouts
- Searching Workouts and exercises

## **Plugins/Resources**
- Kotlin to Json Plugin
- Material Card
- Parelize / Parcelable
- Jetpack Compose

## **Sources**

| Sources                                                                                                                           | Reason                                                                                                                                 |
|-----------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| <https://www.kodeco.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/5-async-await>                                         | general async / await                                                                                                                  |
| <https://stackoverflow.com/questions/6543811/intent-putextra-list>                                                                | putExtra - pass args - Parcelable                                                                                                      |
| <https://www.youtube.com/watch?v=t6Sql3WMAnk&t=1025s>                                                                             | How to use / setup retrofit                                                                                                            |
| <https://medium.com/@sribanavasi/best-practice-for-handling-api-calls-using-retrofit-in-android-studio-36fb5e53c08>               | Wrap retrofit in NetworkResult object                                                                                                  |
| <https://blog.devgenius.io/android-generic-recyclerview-adapter-67eb8f826cad>                                                     | Setting up a generic adapter that takes a generic as a type                                                                            |
| <https://jonas-rodehorst.dev/posts/how-to-structure-your-jetpack-compose-project>                                                 | How to Structure MVVM pattern with composables                                                                                         | 
| <https://blog.devgenius.io/exploring-clean-mvvm-architecture-in-android-using-kotlin-coroutines-room-hilt-retrofit-8656e0042b10>  | Structure MVVM pattern article                                                                                                         |
| <https://github.com/philipplackner/MVVMTodoApp/>                                                                                  | MVVM Structure - Routes with navcontroller                                                                                             |
| <https://stackoverflow.com/questions/44764800/accountauthenticatoractivity-for-appcompat>                                         | response result from authenticatorActivity not being received to called - this was reason why, had to implement funcs in my App Compat |
| <https://stackoverflow.com/questions/69642441/how-to-share-a-viewmodel-between-navgraph-components-only>                          | Share view model between navigation graph                                                                                              |
| <https://stackoverflow.com/questions/75891052/how-to-set-title-and-buttons-in-topappbar-of-child-screens-in-android-jetpack-co>   | How to get the current route (wouldnt trigger without recomp) for actions | <https://stackoverflow.com/questions/72367260/material-swipe-to-dismiss-in-jetpack-compose-with-a-column-instead-of-a-lazycolu> | swipe to delete - had same key issue | 

## **Known Issues**
- Backend can flub giving a date back to the app for the last performed date, some sort of dependency issue with moment.js - date is being saved correctly on db.
- The Server is hosted on render.com and starts cold so first login will probably fail unless a request has already been made to the server, should be fine afterward - sometimes the login page will open twice.
- sets are defaulted to a fixed amount and dont update yet.
