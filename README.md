###  [🍍 Console](https://console.mytiki.com) &nbsp; ⏐ &nbsp; [📚 Docs](https://docs.mytiki.com)

# TIKI SDK [Android] —build the new data economy


A package for adding TIKI's decentralized infrastructure to **Android** projects. Add tokenized data ownership, consent, and rewards to your app in minutes.

### 🎬 How to get started ➝

Go to [console.mytiki.com](https://console.mytiki.com) and get your API key. It is required for backing up the user transactions.

#### Depend on compiled SDK
To use TIKI SDK Android, depend on the compiled SDK. This avoids unnecessary dependencies for production code.

It can be done by manually downloading the TIKI SDK Android AAR file or use Maven Central dependency.

**Maven Central**
1. Add Maven Central to your Project's build.gradle file

PROJECT_ROOT/build.gradle
``` 
repositories {
    mavenCentral()
}
```

2. Add the dependency in your App's build.gradle file

PROJECT_ROOT/app/build.gradle
```
dependencies {
    implementation("com.mytiki.tiki_sdk_android:0.0.1")
}
```
**TIKI SDK Android AAR**

1. Download the latest AAR from our [releases page](https://github.com/tiki/tiki-sdk-android/releases).
2. Open your project in Android Studio.
3. Go to File -> Project Structure.
4. Open "Dependencies" in the left panel.
5. Click in the + sign in the right panel.
<img width="374" alt="Captura de Tela 2022-11-30 às 11 14 13" src="https://user-images.githubusercontent.com/8357343/204835312-49507cac-cb81-4964-af83-32e9e69716c3.png">
6. Choose JAR/AAR dependency.
<img width="381" alt="Captura de Tela 2022-11-30 às 11 14 26" src="https://user-images.githubusercontent.com/8357343/204835467-031cfa9b-8fe6-452f-ab5c-b6df1ede9d32.png">

7. Choose your app module (default is "app").
8. Provide the path to the TIKI SDK Android AAR file.
9. Apply and sync project

#### Build the SDK

*Disclaimer: This method IS NOT recommended for SDK regular use.* 

The TIKI SDK Android uses TIKI SDK Flutter to access the TIKI SDK Dart which controls the blockchain operations.

To build TIKI SDK Android, make sure that you have [Flutter](https://docs.flutter.dev/get-started/install) installed and correctly configured in your OS. 

The TIKI SDK Flutter is built together with TIKI SDK Android.

1. Clone [TIKI SDK Flutter](https://github.com/tiki/tiki-sdk-flutter) in the same folder where TIKI SDK Android is. Both SDKs should be siblings in the folder.
2. Run `flutter pub get` in TIKI SDK Flutter root. It will create the `.android` folder.
3. Change `compileSdkVersion` and `targetSdkVersion` to 33 in `.android/build.gradle`, `.android/app/build.gradle`, and `.android/Flutter/build.gradle`.
4. Change `minSdkVersion` to 21 in `.android/build.gradle`, `.android/app/build.gradle`, and `.android/Flutter/build.gradle`.
5. In TIKI SDK Android root run `./gradlew assemble`.
6. AAR file will be available at <TIKI SDK Android ROOT>/app/build/outputs/aar.
7. Depend on this AAR file as described in "Depend on compiled SDK" section

#### Initialize the SDK:

Kotlin
```
    ...
    import com.mytiki.tiki_sdk_android.TikiSdk
    ...
    val tikiSdk = TikiSdk(<apiKey>, <origin>, <context)
```

Java

```
    ...
    import com.mytiki.tiki_sdk_android.TikiSdk
    ...
    TikiSdk tikiSdk = TikiSdk(<apiKey>, <origin>, <context)
```

- **[API Reference ➝](https://docs.mytiki.com/reference/tiki-sdk-android)**

#### Basic Architecture

The TIKI SDK Android uses Flutter to embed the TIKI SDK Dart library into Android native code.

Upon initialization, it loads and initializes Android Flutter Engine that embeds a Dart VM which runs TIKI SDK Flutter.

The Flutter Platform Channels are used for communication between Dart and Android code.

- **[Dart Docs ➝](https://pub.dev/documentation/tiki_sdk_flutter/latest/)**
 