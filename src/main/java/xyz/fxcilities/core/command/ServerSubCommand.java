package xyz.fxcilities.core.command;

import java.util.List;

/**
 * Represents a sub command for a {@link ServerCommand}
 *
 * <pre>{@code
 * public class MySubCommand extends ServerSubCommand {
 *     public MySubCommand(ServerCommand parent) {
 *         super(parent, "world", "my fun subcommand", "/hello world", List.of("planet", "earth"));
 *     }
 *
 *     @Override
 *     public void onCommand() {
 *         say("Hello world!");
 *     }
 * }
 * }</pre>
 */
public abstract class ServerSubCommand {
  public final ServerCommand parent;
  public final String label, description, usage;
  public final List<String> aliases;

  /**
   * Creates a sub command. Registers when initialization of command.
   *
   * @param parent The command this subcommand belongs to
   * @param label The name of the sub command
   * @param description Description of the sub command. Has no use unless used manually
   * @param usage Usage of the sub command. Has no use unless used manually
   * @param aliases A list of aliases for the subcommand. Must be in all lower case!
   * @see #onCommand
   */
  public ServerSubCommand(
      ServerCommand parent, String label, String description, String usage, List<String> aliases) {
    this.parent = parent;
    this.label = label;
    this.description = description;
    this.usage = usage;
    this.aliases = aliases;

    parent.registerSub(this);
  }

  public abstract void onCommand();

  /**
   * @see ServerCommand#say(boolean, String)
   */
  protected void say(boolean withPrefix, String message) {
    parent.say(withPrefix, message);
  }

  /**
   * @see ServerCommand#say(String)
   */
  protected void say(String message) {
    parent.say(message);
  }
}
