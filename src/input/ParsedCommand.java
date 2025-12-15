package input;

public class ParsedCommand {
    public final Command command;
    public final Integer index;

    public ParsedCommand(Command command) {
        this(command, null);
    }

    public ParsedCommand(Command command, Integer index) {
        this.command = command;
        this.index = index;
    }
}
