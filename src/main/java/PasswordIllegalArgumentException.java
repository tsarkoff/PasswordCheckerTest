// Класс-агрегатор ошибок неверного аргумента сеттеров критериев пароля, наследник IllegalArgumentException
public class PasswordIllegalArgumentException extends IllegalArgumentException {
    private final String msg;

    public PasswordIllegalArgumentException(ExceptionType exceptionType, int param) {
        switch (exceptionType) {
            case E_MIN_LENGTH -> msg = String.format(
                    Colors.ANSI_RED +
                            "Длина должна быть больше или равна нулю (введено: %d)\n"
                            + Colors.ANSI_RESET, param);
            case E_MAX_REPEAT -> msg = String.format(
                    Colors.ANSI_RED +
                            "Кол-во повторений должно быть больше нуля (введено: %d)\n"
                            + Colors.ANSI_RESET, param);
            case E_NULL_PASSWORD -> msg = String.format(
                    Colors.ANSI_RED +
                            "Пароль не должен быть NULL\n"
                            + Colors.ANSI_RESET);
            default -> msg = Colors.ANSI_RED + "Неизвестная ошибка\n" + Colors.ANSI_RESET;
        }
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
