import java.io.*;
import java.util.*;

abstract class Train implements Serializable {
    private String trainNumber;
    private String startingStation;
    private String destinationStation;
    private double price;
    private String date;
    private String time;
    public Train(String trainNumber, String startingStation, String destinationStation, double price, String date, String time) {
        this.trainNumber = trainNumber;
        this.startingStation = startingStation;
        this.destinationStation = destinationStation;
        this.price = price;
        this.date = date;
        this.time = time;
    }
    public Train(String date){
        this.date = date;
    }
    public String getTrainNumber() {
        return trainNumber;
    }
    public String getTime(){
        return time;
    }
    public String getDate(){
        return date;
    }
    public String getStartingStation() {
        return startingStation;
    }
    public String getDestinationStation() {
        return destinationStation;
    }
    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "Поезд:" +
                " Номер поезда '" + trainNumber + '\'' +
                ", Начальная станция '" + startingStation + '\'' +
                ", Конечная станция '" + destinationStation + '\'' +
                ", Цена " + price + '\'' + "Время отправления "+ time + '\'' +" Дата "+ date;
    }
    @Override
    public boolean equals(Object a) {
        if (this == a) return true;
        if (a == null || getClass() != a.getClass()) return false;
        Train train = (Train) a;
        return  Double.compare(train.price, price) == 0 &&
                Objects.equals(trainNumber, train.trainNumber) &&
                Objects.equals(startingStation, train.startingStation) &&
                Objects.equals(destinationStation, train.destinationStation) &&
                Objects.equals(time,train.time) &&
                Objects.equals(date, train.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(trainNumber, startingStation, destinationStation, price, time, date);
    }
}
class ExpressTrain extends Train {
    private String type;
    public ExpressTrain(String trainNumber, String startingStation, String destinationStation, double price, String type, String date, String time) {
        super(trainNumber, startingStation, destinationStation, price, date, time);
        this.type = type;
    }
    @Override
    public String toString() {
        return "Скоростной Поезд:" +
                " Номер поезда '" + getTrainNumber() + '\'' +
                ", Начальная станция '" + getStartingStation() + '\'' +
                ", Конечная станция'" + getDestinationStation() + '\'' +
                ", Цена " + getPrice() +
                ", Тип поезда '" + type + '\'' + "Время отправления "+ getTime() + '\'' +" Дата "+ getDate();
    }
}
class LocalTrain extends Train {
    private String type;
    public LocalTrain(String trainNumber, String startingStation, String destinationStation, double price, String type, String date, String time) {
        super(trainNumber, startingStation, destinationStation, price, date, time);
        this.type = type;
    }
    @Override
    public String toString() {
        return  "Обычный Поезд:" +
                " Номер поезда '" + getTrainNumber() + '\'' +
                ", Начальная станция '" + getStartingStation() + '\'' +
                ", Конечная станция'" + getDestinationStation() + '\'' +
                ", Цена " + getPrice() +
                ", Тип поезда '" + type + "Время отправления "+ getTime() + '\'' +" Дата "+ getDate();
    }
}
class PassengerRequest {
    private String destinationStation;
    private String date;
    private String time;
    public PassengerRequest(String destinationStation, String date, String time) {
        this.destinationStation = destinationStation;
        this.date = date;
        this.time = time;
    }
    public String getDestinationStation() {
        return destinationStation;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    @Override
    public String toString() {
        return "Запрос пассажира" +
                " Конечная станция '" + destinationStation + '\'' +
                ", Дата '" + date + '\'' +
                ", Время '" + time + '\'';
    }
}
class PaymentInvoice {
    private double amount;
    public PaymentInvoice(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }
    @Override
    public String toString() {
        return "Cчёт на оплату:" +
                "Сумма к оплате: " + amount;
    }
}
abstract class Person {
    private String username;
    private String password;
    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Administrator extends Person  {

    public Administrator(String username, String password) {
        super(username, password);
    }

    public boolean authenticate(String password) {
        return getPassword().equals(password);
    }
}

class User extends Person {

    public User(String username, String password) {
        super(username, password);
    }
    public boolean authenticate(String password) {
        return getPassword().equals(password);
    }

}


public class Main {
    public static void main(String[] args) {
        File train32 = new File("train32.txt");
        try {
            train32.createNewFile();
        }
        catch(IOException e){
            System.out.println("ОШИБКА СОЗДАНИЯ");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Кто вы? 1.Администратор 2.Пользователь 3.Выход");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (roleChoice) {
                    case 1:
                        authenticateAdmin(scanner);
                        break;
                    case 2:
                        authenticateUser(scanner);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Завершение работы программы.");
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                        break;
                }
            } catch (MyExc2 e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
    static class MyExc2 extends Exception {
        public MyExc2(String message) {
            super(message);
        }
    }
    private static void authenticateAdmin(Scanner scanner) throws MyExc2 {
        Administrator admin = new Administrator("admin", "123");
        System.out.println("Администратор: Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (!admin.authenticate(password)) {
            throw new MyExc2("Неправильное имя пользователя или пароль. Доступ запрещен.");
        }

        System.out.println("Вы успешно вошли в систему как Администратор.");
        adminMenu(scanner);
    }

    private static void authenticateUser(Scanner scanner) throws MyExc2{
        User user = new User("user", "321");
        System.out.println("Пользователь: Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (!user.authenticate(password)) {
            throw new MyExc2("Неправильное имя пользователя или пароль. Доступ запрещен.");
        }

        System.out.println("Вы успешно вошли в систему как Пользователь.");
        userMenu(scanner);
    }
    private static ArrayList<Train> trains = new ArrayList<>();
    private static TreeSet<Train> sortedTrains;
    private static PassengerRequest request;
    private static void adminMenu(Scanner scanner) {
        Comparator<Train> byDepartureTimeThenTrainNumber = Comparator
                .comparing(Train::getTime)
                .thenComparing(Train::getTrainNumber);

        sortedTrains = new TreeSet<>(byDepartureTimeThenTrainNumber);

        boolean adminSession = true;
        while (adminSession) {
            System.out.println("Меню администратора:");
            System.out.println("1. Поиск поезда");
            System.out.println("2. Добавление поездов");
            System.out.println("3. Просмотр всех поездов");
            System.out.println("4. Выход");
            System.out.println("5. Добавление поездов в файл");
            System.out.println("6. Просмотр поездов в файле");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchForTrain(scanner);
                    break;
                case 2:
                    System.out.println("Введите данные для поезда:");
                    Train newTrain = promptTrainData(scanner);
                    trains.add(newTrain);
                    sortedTrains.add(newTrain);
                    break;
                case 3:
                    viewTrains();
                    break;
                case 4:
                    adminSession = false;
                    System.out.println("Выход из аккаунта администратора.");
                    break;
                case 5:
                    try{
                        FileOutputStream fos = new FileOutputStream("train32.txt");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(trains);
                        oos.close();
                        fos.close();
                        System.out.println("Успешная запись в файл");
                    }
                    catch (FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        FileInputStream fis = new FileInputStream("train32.txt");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        List<Train> trainList = (List<Train>) ois.readObject();
                        ois.close();
                        fis.close();
                        System.out.println("Данные из файла:");
                        System.out.println("Поезда: ");
                        for (int i = 0; i < trainList.size(); i++){
                            System.out.println((i+1)+" Номер: "+ trainList.get(i).getTrainNumber()+" Начальная станция: "+
                                    trainList.get(i).getStartingStation() +" Конечная станция: "+
                                    trainList.get(i).getDestinationStation()+" Цена: "+ trainList.get(i).getPrice()
                                    +" Время: "+ trainList.get(i).getTime() +" Дата: "+ trainList.get(i).getDate());
                        }
                    }
                    catch (FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    catch (ClassNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
                    break;
            }
        }
    }

    private static void viewTrains() {
        if (sortedTrains.isEmpty()) {
            System.out.println("Список поездов пуст.");
        } else {
            System.out.println("Список всех поездов:");
            for (Train train : sortedTrains) {
                System.out.println(train);
            }
        }
    }
    private static void searchForTrain(Scanner scanner) {
        System.out.println("Поиск подходящего поезда:");
        boolean found = false;
        for (Train train : trains) {
            if (train.getDestinationStation().equals(request.getDestinationStation())
                    && train.getTime().equals(request.getTime()) && train.getDate().equals(request.getDate())) {
                System.out.println("Найден подходящий поезд:");
                System.out.println(train);
                PaymentInvoice invoice = new PaymentInvoice(train.getPrice());
                System.out.println("Сумма к оплате: " + invoice.getAmount());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Подходящий поезд не найден.");
        }
    }
    private static void userMenu(Scanner scanner) {
        boolean userSession = true;
        while (userSession) {
            System.out.println("Меню пользователя:");
            System.out.println("1. Запрос");
            System.out.println("2. Выбор поезда");
            System.out.println("3. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Запрос пользователя: ");
                    request = promptPassengerRequest(scanner);
                    System.out.println(request);
                    break;
                case 2:
                    selectTrainForUser(scanner);
                    break;

                case 3:
                    userSession = false;
                    System.out.println("Выход из аккаунта пользователя.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
                    break;
            }
        }
    }

    private static void selectTrainForUser(Scanner scanner) {
        System.out.println("Доступные поезда для выбора:");
        for (int i = 0; i < trains.size(); i++) {
            System.out.println((i + 1) + ". Поезд: " + trains.get(i));
        }
        try {
            System.out.println("Выберите номер поезда:");
            int trainSelection = scanner.nextInt();
            scanner.nextLine();
            if (trainSelection < 1 || trainSelection > trains.size()) {
                throw new MyExc1("Выбран неверный номер поезда.");
            }
            Train selectedTrain = trains.get(trainSelection - 1);
            System.out.println("Вы выбрали: " + selectedTrain);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Ошибка: введено не число. Пожалуйста, попробуйте снова.");
        } catch (MyExc1 e) {
            System.out.println(e.getMessage());
        }
    }
    static class MyExc1 extends Exception {
        public MyExc1(String message) {
            super(message);
        }
    }
    private static PassengerRequest promptPassengerRequest(Scanner scanner) {
        System.out.println("Введите пункт назначения:");
        String destination = scanner.nextLine();

        System.out.println("Введите дату (гггг-мм-дд):");
        String date = scanner.nextLine();

        System.out.println("Введите время (чч:мм):");
        String time = scanner.nextLine();

        return new PassengerRequest(destination, date, time);
    }
    private static Train promptTrainData(Scanner scanner) {
        System.out.println("Введите номер поезда:");
        String trainNumber = scanner.nextLine();

        System.out.println("Введите начальную станцию:");
        String startingStation = scanner.nextLine();

        System.out.println("Введите конечную станцию:");
        String destinationStation = scanner.nextLine();

        System.out.println("Введите цену:");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Выберите тип поезда: 1.Local 2.Express");
        int choise = scanner.nextInt();
        String type = "";
        switch (choise){
            case 1:
                type = "Local"; break;
            case 2:
                type = "Express"; break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
                break;

        }
        String date = scanner.nextLine();
        System.out.println("Введите дату (гггг-мм-дд):");
        date = scanner.nextLine();

        System.out.println("Введите время (чч:мм):");
        String time = scanner.nextLine();

        if (type.equalsIgnoreCase("Express")) {
            return new ExpressTrain(trainNumber, startingStation, destinationStation, price, type, date, time);
        } else {
            return new LocalTrain(trainNumber, startingStation, destinationStation, price, type, date, time);
        }
    }
}