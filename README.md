  # PluginCore
  [![](https://jitpack.io/v/Fxcilities/PluginCore.svg)](https://jitpack.io/#Fxcilities/PluginCore)
  [![](https://img.shields.io/github/workflow/status/Fxcilities/PluginCore/Java%20CI%20with%20Gradle)](https://github.com/Fxcilities/PluginCore/actions)

  A core for all my spigot plugins

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
  - [Some Ideas](https://github.com/kangarko/Foundation) (idk why i made this repo, i couldve just used this one thats already made)
