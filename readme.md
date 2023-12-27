# **Description**
Android App for creating exercises / tracking workouts.

## **Testing**

Homer Simpson account is seeded with some data for testing purposes.

Login in with: 

Email: homer@simpson.com
Password: secret

## **Backend URL**
<https://myfitnesstrainer-backend.onrender.com>

## **Plugins/Resources**
- Kotlin to Json Plugin
- Material Card
- Parelize / Parcelable

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
| <https://stackoverflow.com/questions/75891052/how-to-set-title-and-buttons-in-topappbar-of-child-screens-in-android-jetpack-co>   | How to get the current route (wouldnt trigger without recomp) for actions |

## **Known Issues**
- Total Volume of sets (shows up after workout completion) value is incorrect.
- Backend is hosted on render so a first login will take some time to load - may crash the app.
