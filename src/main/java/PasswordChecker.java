import java.util.Arrays;

public class PasswordChecker {
    private int minLength = -1;
    private int maxRepeats = -1;

    // Пример использования класса наследника от IllegalArgumentException
    public boolean setMinLength(int minLength) {
        if (minLength < 0) {
            throw new PasswordIllegalArgumentException(ExceptionType.E_MIN_LENGTH, minLength);
        }
        this.minLength = minLength;
        return true;
    }

    // Пример использования класса наследника от IllegalArgumentException
    public boolean setMaxRepeats(int maxRepeats) {
        if (maxRepeats <= 0) {
            throw new PasswordIllegalArgumentException(ExceptionType.E_MAX_REPEAT, maxRepeats);
        }
        this.maxRepeats = maxRepeats;
        return true;
    }

    // Валидация пароля - прямое использование стандартного класса ощибки IllegalStateException
    public boolean verify(String password) {
        if (maxRepeats == -1 || minLength == -1) {
            throw new IllegalStateException(
              Colors.ANSI_RED +
              "\nОШИБКА. Невозможно проверить пароль без инициализации длины и повторений символа.\n"
              + Colors.ANSI_RESET);
        }

        if (password == null) {
            throw new PasswordIllegalArgumentException(ExceptionType.E_NULL_PASSWORD, 0);
        }

        if (password.length() < minLength) {
            return false;
        }

        int repeatsCount = 0;
        int[] repeats = new int[password.length()];
        Arrays.fill(repeats, 1);
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i + 1) == password.charAt(i)) {
                repeats[repeatsCount]++;
            } else {
                repeatsCount++;
            }
        }

        for (int repeat : repeats) {
            if (repeat > maxRepeats) {
                return false;
            }
        }

        return true;
    }
}
