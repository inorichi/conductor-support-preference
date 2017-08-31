Library to create preference screens using [Conductor](https://github.com/bluelinelabs/Conductor) controllers instead of fragments.

## Including the library

You can include this library in your project with [JitPack](https://jitpack.io).

```groovy
dependencies {
    compile 'com.github.inorichi:conductor-support-preference:-SNAPSHOT'
}
```

## Usage

Add `<item name="preferenceTheme">@style/PreferenceThemeOverlay.Material</item>` to your theme.

Create a class that inherits from `PreferenceController` and include your preferences in the `onCreatePreferences` method, 
either by inflating an xml with `addPreferencesFromResource` or manually creating them, though you will need to provide
a `ContextThemeWrapper` if you use the latter and want to have a material theme.

Finally, use `Router::pushController` to show your preference controller.
