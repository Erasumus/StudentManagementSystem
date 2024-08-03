package org.example;

import java.util.Map;

public class StudentCommandHandler {
    private StudentStorage studentStorage = new StudentStorage();

    public void processCommand(Command command) {
        Action action = command.getAction();
        switch (action) {
            case CREATE -> processCreateCommand(command);
            case DELETE -> processDeleteCommand(command);
            case UPDATE -> processUpdateCommand(command);
            case STATS_BY_COURSE -> processStatsByCourseCommand(command);
            case STATS_BY_CITY -> processStatsByCityCommand(command);
            case SEARCH -> processSearchCommand(command);
            default -> System.out.println("Действие: "
                    + command.getAction().name()
                    + ", данные: " + command.getData());
        }

        System.out.println("Обработка команды. Действие: "
                + command.getAction().name()
                + ", данные: " + command.getData());
    }

    private void processSearchCommand(Command command){
        String surname = command.getData();
        studentStorage.search(surname);
    }

    private void processStatsByCourseCommand(Command command){
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }

    private void processStatsByCityCommand(Command command){
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }

    private void processCreateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");
        if (dataArray.length != 5) {
            System.out.println("Некорректные данные. Ожидается [Фамилия,Имя,Курс,Город,Возраст]");
            return;
        }

        try {
            Student student = new Student();
            student.setSurname(dataArray[0].trim());
            student.setName(dataArray[1].trim());
            student.setCourse(dataArray[2].trim());
            student.setCity(dataArray[3].trim());
            student.setAge(Integer.valueOf(dataArray[4].trim()));

            studentStorage.createStudent(student);
            studentStorage.printAll();
        } catch (Exception e) {
            System.out.println("Ошибка обработки данных. Убедитесь, что возраст - это число.");
        }
    }

    private void processUpdateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");
        if (dataArray.length != 6) {
            System.out.println("Некорректные данные. Ожидается [ID,Фамилия,Имя,Курс,Город,Возраст]");
            return;
        }

        try {
            Long id = Long.valueOf(dataArray[0].trim());
            Student student = new Student();
            student.setSurname(dataArray[1].trim());
            student.setName(dataArray[2].trim());
            student.setCourse(dataArray[3].trim());
            student.setCity(dataArray[4].trim());
            student.setAge(Integer.valueOf(dataArray[5].trim()));

            studentStorage.updateStudent(id, student);
            studentStorage.printAll();
        } catch (Exception e) {
            System.out.println("Ошибка обработки данных. Убедитесь, что ID и возраст - это числа.");
        }
    }

    public void processDeleteCommand(Command command) {
        String data = command.getData();
        try {
            Long id = Long.valueOf(data.trim());

            studentStorage.deleteStudent(id);
            studentStorage.printAll();
        } catch (Exception e) {
            System.out.println("Ошибка обработки данных. Убедитесь, что ID - это число.");
        }
    }


}
