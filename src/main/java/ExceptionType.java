// Типы ошибок для генерации объектов класса PasswordIllegalArgumentException
public enum ExceptionType {
    E_MIN_LENGTH,
    E_MAX_REPEAT,
    E_NULL_PASSWORD;
}

// Красный цвет при выводе сообщений из Exception.getMessage()
final class Colors {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
}