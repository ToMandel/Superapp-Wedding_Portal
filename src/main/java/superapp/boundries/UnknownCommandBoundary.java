package superapp.boundries;

public class UnknownCommandBoundary {

    private String commandName;
    private String errorMessage;

    public UnknownCommandBoundary() {
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
