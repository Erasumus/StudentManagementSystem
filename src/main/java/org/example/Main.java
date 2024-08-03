package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static StudentCommandHandler STUDENT_COMMAND_HANDLER
            = new StudentCommandHandler();

    public static void main(String[] args) {
        while (true){
            printMessage();
            Command command = readCommand();
            if(command.getAction() == Action.EXIT){
                return;
            } else if(command.getAction() == Action.ERROR){
                System.out.println("нет такой команды");
                continue;
            } else {
                STUDENT_COMMAND_HANDLER.processCommand(command);
            }
        }
    }

    public static Command readCommand(){
        Scanner scanner = new Scanner(System.in);
        try {
            String actionCode = scanner.nextLine();
            Integer code = Integer.valueOf(actionCode);
            Action action = Action.fromCode(code);
            if(action.getRequireIsAdditionalData()) {
                String data = scanner.nextLine();
                return new Command(action, data);
            } else {
                return new Command(action);
            }
        } catch (Exception e){
            System.out.println("Не верное число кода");
            return new Command(Action.ERROR);
        }
    }

    private static void printMessage() {
        System.out.println("-----------------------");
        System.out.println("0. Выход");
        System.out.println("1. Создание данных");
        System.out.println("2. Обновление данных");
        System.out.println("3. Удаление данных");
        System.out.println("4. Вывод статистика по курсам");
        System.out.println("6. Поиск по фамилии");
        System.out.println("-----------------------");
    }
}