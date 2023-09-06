// Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
// Фамилия Имя Отчество датарождения номертелефона пол

// Форматы данных:
// фамилия, имя, отчество - строки
// датарождения - строка формата dd.mm.yyyy
// номертелефона - целое беззнаковое число без форматирования
// пол - символ латиницей f или m.

// Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, 
// обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

// Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, 
// нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. 
// Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

// Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, 
// вида

// <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

// Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

// Не забудьте закрыть соединение с файлом.

// При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class UserDataApp {

    public static void main(String[] args) {
        try {
            // Запрашиваем данные у пользователя
            String userData = getUserData();

            // Распарсиваем данные и записываем в файл
            writeUserDataToFile(userData);
            System.out.println("Данные успешно сохранены!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUserData() {
        System.out.println("Введите данные в формате: Фамилия Имя Отчество Дата_рождения Номер_телефона Пол");
        return System.console().readLine();
    }

    private static void writeUserDataToFile(String userData) throws IOException {
        String[] data = userData.split(" ");
        if (data.length != 6) {
            throw new IllegalArgumentException("Введено неверное количество данных");
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String dateOfBirth = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        validateData(lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);

        String fileName = lastName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String formattedData = String.format("%s %s %s %s %s %s", lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
            writer.write(formattedData);
            writer.newLine();
        }
    }

    private static void validateData(String lastName, String firstName, String middleName, String dateOfBirth, String phoneNumber, String gender) {
        if (!dateOfBirth.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new IllegalArgumentException("Неверный формат даты рождения");
        }

        try {
            LocalDate.parse(dateOfBirth, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверная дата рождения");
        }

        if (!phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }

        if (!gender.matches("[fm]")) {
            throw new IllegalArgumentException("Неверное значение пола");
        }
    }
}
