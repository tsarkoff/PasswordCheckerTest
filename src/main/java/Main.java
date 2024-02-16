import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        PasswordChecker passwordChecker = new PasswordChecker();

        // Ввод минимальной длины, выброс эксепшна PasswordIllegalArgumentException
        try {
            System.out.print("Введите минимальную длину пароля: ");
            passwordChecker.setMinLength(Integer.parseInt(scanner.nextLine()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Ввод максимальных повторений, выброс эксепшна PasswordIllegalArgumentException
        try {
            System.out.print("Введите макс. допустимое количество повторений символа подряд: ");
            passwordChecker.setMaxRepeats(Integer.parseInt(scanner.nextLine()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            System.out.print("Введите пароль или end: ");
            input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }

            // Валидация пароля, выброс эксепшна IllegalStateException
            try {
                System.out.println(passwordChecker.verify(input) ? "Подходит!" : "Не подходит!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        System.out.println("Программа завершена");
        scanner.close();
    }
}
