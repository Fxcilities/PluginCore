# PluginCore
[![](https://img.shields.io/badge/dynamic/json?color=blue&label=JitPack&query=latestOk&url=https://jitpack.io/api/builds/com.github.Fxcilities/PluginCore/latest&style=for-the-badge)](https://jitpack.io/#Fxcilities/PluginCore)
[![](https://img.shields.io/badge/dynamic/json?color=blue&label=JavaDoc&query=latestOk&url=https://jitpack.io/api/builds/com.github.Fxcilities/PluginCore/latest&style=for-the-badge)](https://javadoc.jitpack.io/com/github/Fxcilities/PluginCore/latest/javadoc/index.html)
[![](https://img.shields.io/github/workflow/status/Fxcilities/PluginCore/Java%20CI%20with%20Gradle?color=blue&style=for-the-badge)](https://github.com/Fxcilities/PluginCore/actions)

A core for all my spigot plugins

## Getting started

### If you use gradle, add this.
  ```gradle
repositories {
      maven { url 'https://jitpack.io' }
}

dependencies {
      implementation 'com.github.Fxcilities:PluginCore:VERSION'
}
```
### If you use maven, add this.

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.Fxcilities</groupId>
        <artifactId>PluginCore</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

### If you use a dependency manager not listed, check the [https://jitpack.io/#Fxcilities/PluginCore](jitpack) for more examples.
### NOTE: Make sure to shadow the PluginCore dependency into your plugin build.

## Examples

### Basic plugin with a command

**MyPlugin.java**
  ```java
public final class MyPlugin extends Core {

  @Override
  public void onPluginEnable() {
    console.print("Hello world!");

    // Initialize commands
    new MyCommand();
  }

  @Override
  public void onPluginDisable() {
    console.print("Goodbye world!");
  }

  @Override
  public String getPrefix() {
    return "&bMyPlugin > &f";
  }

  @Override
  public String getPluginVersion() {
    return "v1.0";
  }

  @Override
  public String getPluginName() {
    return "MyPlugin";
  }

  @Override
  public String[] getPluginAuthors() {
    return new String[]{"Mario", "Luigi"};
  }
}
  ```
**MyCommand.java**
  ```java
  public class MyCommand extends ServerCommand {

  public MyCommand() {
    super("hello", "says hello world", "/hello", true, Arrays.asList("helloworld", "world")); // label, description, usage, playerOnly, aliases

    // Optional
    setCooldownDuration(5, TimeUnit.SECONDS); // Five second cooldown
  }

  @Override
  public void onCommand() {
    say(true, "&aHello world!"); // true to show the prefix of the plugin
  }
}
```


## Credit

- [ExpiringMap](https://github.com/jhalterman/expiringmap)
- [PlayerScoreboard](https://www.spigotmc.org/threads/scoreboard-api-1-8.160095/)
- [Some Ideas](https://github.com/kangarko/Foundation)
