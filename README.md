# TIKI SDK [Android] —build the new data economy

### [📚 Docs](https://docs.mytiki.com) &nbsp;&nbsp;[💬 Discord](https://discord.gg/tiki)

A package for adding TIKI's decentralized infrastructure to **Android** projects. Add tokenized data ownership, consent, and rewards to your app in minutes.

### 🎬 How to get started ➝

Go to [console.mytiki.com](https://console.mytiki.com) and get your API key. It is required for backing up the user transactions.

Add Maven Central to your Project's build.gradle file

PROJECT_ROOT/build.gradle
``` 
repositories {
    mavenCentral()
}
```

Add the dependency in your App's build.gradle file

PROJECT_ROOT/app/build.gradle
```
dependencies {
    implementation("com.mytiki.tiki_sdk_android:0.0.1")
}
```

Initialize the SDK:

```
    val tikiSdk = TikiSdk(<apiKey>, <origin>, <context)
```

- **[API Reference ➝](https://docs.mytiki.com/reference/tiki-sdk-android)**

#### Basic Architecture

The TIKI SDK Android uses Flutter to embed the TIKI SDK Dart library into Android native code.

Upon initialization, it loads and initializes Android Flutter Engine that embeds a Dart VM which runs TIKI SDK Flutter.

The Flutter Platform Channels are used for comunication between Dart and Android code.

- **[Dart Docs ➝](https://pub.dev/documentation/tiki_sdk_flutter/latest/)**
