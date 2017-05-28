package api.websocket;

/**
 * Created by Vileven on 28.05.17.
 */
public class HandleException extends Exception {
    public HandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleException(String message) {
        super(message);
    }
}
