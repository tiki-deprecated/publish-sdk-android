# TIKI SDK (iOS) —Consumer Data Licensing
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

The TIKI SDK for Android makes it easy to add consumer data licensing to your Android applications. It's the client-side component that your users will interact with to accept (or decline) data licensing offers. TIKI's SDK creates immutable, digitally signed license records using cryptographic hashing, forming an audit trail. Programmatically consume records and enforce terms client or server-side using developer-friendly data structures and [APIs](https://mytiki.com/reference/getting-started).

This library includes both configurable pre-built UI flows/elements and native low-level APIs for building custom experiences.

**Get started with our 📚 [SDK docs](https://mytiki.com/docs/sdk-overview), or jump right into the 📘 [API reference](https://tiki-sdk-android.docs.mytiki.com).**

## Installing

If you haven't already, add Maven Central to your Project's `<PROJECT_ROOT>/build.gradle`.

```Gradle
repositories {
  mavenCentral()
}
```

Add the dependency in your App's build.gradle file (`<PROJECT_ROOT>/app/build.gradle`) and make sure to grab the latest version. We release often.

```Gradle
implementation("com.mytiki:tiki-sdk-android:2.1.2")
```

That's it. And yes, it's really that easy.

## Initialization
Initialize the TIKI SDK in minutes with the TIKI pre-built UI and a custom data offer —just 1 builder function ([interactive example](https://mytiki.com/recipes/sdk-pre-built-ui-setup)).

```
TikiSdk
    .theme
        .primaryTextColor(Color.rgb(28, 0, 0))
        .secondaryTextColor(Color.argb((255 * 0.6).toInt(), 28, 0, 0))
        .primaryBackgroundColor(Color.WHITE)
        .secondaryBackgroundColor(Color.rgb(245, 245, 245))
        .accentColor(Color.rgb(0, 179, 112))
        .and()
    .offer
        .description("Trade your IDFA (kind of like a serial # for your phone) for a discount.")
        .reward(ResourcesCompat.getDrawable(resources, R.drawable.offer_img, null)!!)
        .bullet("Learn how our ads perform ", true)
        .bullet("Reach you on other platforms", false)
        .bullet("Sold to other companies", false)
        .terms(this, "terms.md")
        .ptr("db2fd320-aed0-498e-af19-0be1d9630c63")
        .tag(TitleTag.DEVICE_ID)
        .use(listOf(LicenseUsecase.ATTRIBUTION))
        .permission(Permission.TRACKING)
        .add()
    .init(this, "<your-publishing-id>", "<your-user-id>")
    .await()
```

Read about styling, selecting metadata, and designing your offer in our [📚 SDK docs →](https://mytiki.com/docs/sdk-overview).

## UI Flows

The SDK includes 2 pre-built flows: `present()` and `settings()`. Use `present()` to display to the user a new data licensing offer.

```
TikiSdk.present(this);
```

Use `settings()` to render a ...settings screen 😲 where users can change their mind and opt-out of an existing license agreement.

```
TikiSdk.settings(this);
```

# Contributing

- Use [GitHub Issues](https://github.com/tiki/tiki-sdk-android/issues) to report any bugs you find or to request enhancements.
- If you'd like to get in touch with our team or other active contributors, pop in our 👾 [Discord](https://discord.gg/tiki).
- Please use [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) if you intend to add code to this project.

## Project Structure
_This project leverages and bundles the Flutter Engine to create a bidirectional  ._

- `/app/src/main`: The primary implementation source for the library.
   - `/ui`: Declarative UI flows and elements (SwiftUI)
   - `/core` Bi-directional communication with the bundled [tiki-sdk-dart](https://github.com/tiki/tiki-sdk-dart) library using the [Flutter Engine](https://github.com/flutter/engine).
   - `/assets` & `/res`: Bundled assets such as graphics and fonts
- `/test`: Unit tests. Must run entirely in-mem without `/core`
- `/integration_tests`: Requires a device or simulator, open as a Gradle Project to run.
- `/example_app`: Simple example app showing how to get started configuring and adding the SDK to a basic Android app.

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://www.linkedin.com/in/ricardolg/"><img src="https://avatars.githubusercontent.com/u/8357343?v=4?s=100" width="100px;" alt="Ricardo Gonçalves"/><br /><sub><b>Ricardo Gonçalves</b></sub></a><br /><a href="https://github.com/tiki/tiki-sdk-android/commits?author=ricardobrg" title="Code">💻</a> <a href="https://github.com/tiki/tiki-sdk-android/commits?author=ricardobrg" title="Documentation">📖</a> <a href="#maintenance-ricardobrg" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="http://mytiki.com"><img src="https://avatars.githubusercontent.com/u/3769672?v=4?s=100" width="100px;" alt="Mike Audi"/><br /><sub><b>Mike Audi</b></sub></a><br /><a href="https://github.com/tiki/tiki-sdk-android/pulls?q=is%3Apr+reviewed-by%3Amike-audi" title="Reviewed Pull Requests">👀</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!