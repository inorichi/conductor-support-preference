Library to create preference screens using [Conductor](https://github.com/bluelinelabs/Conductor) controllers instead of fragments.

## Including the library

You can include this library in your project with [JitPack](https://jitpack.io).

```groovy
dependencies {
    // For the old support library (it should work with 28.0.0 too)
    implementation 'com.github.inorichi:conductor-support-preference:27.0.2'

    // For AndroidX (it'll be promoted to 1.0.0 once proven stable)
    implementation 'com.github.inorichi:conductor-support-preference:faaf807'
}
```

## Usage

If you're using the support library's artifacts, add `<item name="preferenceTheme">@style/PreferenceThemeOverlay.Material</item>` to your theme. AndroidX artifacts don't require any additional configuration.

Create a class that inherits from `PreferenceController` and include your preferences in the `onCreatePreferences` method, 
either by inflating an xml with `addPreferencesFromResource` or manually creating them, though you will need to provide
a `ContextThemeWrapper` if you use the latter and want to have a material theme.

Finally, use `Router::pushController` to show your preference controller.
