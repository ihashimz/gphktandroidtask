# Kotlin Course Management Sample

A historical Android practice application organized around course management, user sign-in/sign-up screens and a local persistence layer.

## Architecture

- `data/entities` and `data/dao`: entities and data-access boundaries.
- `data/local/GPHDatabase.kt`: local database.
- `data/repository`: user and course repositories.
- `ui`: ViewModels and fragments for login, signup, home, course listing and adding courses.
- `ui/adapters/CoursesAdapter.kt`: course list presentation.

## Development

Open the repository in Android Studio and inspect its Gradle/SDK versions before building:

```sh
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

This is an older local application sample, not a production authentication service. The historical build has not been rerun during this documentation update. For current backend architecture examples, see [Catalog Sync](https://github.com/ihashimz/nestjs-catalog-sync) and [Order Workflow](https://github.com/ihashimz/nestjs-order-workflow).
