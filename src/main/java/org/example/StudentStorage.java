package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class StudentStorage {
    private Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();
    private Long currentId = 0L;

    /**
     * Создание данных о студенте
     * @param student данные о студенте
     * @return сгенерированный уникаольный индентификатор студента
     */
    public Long createStudent(Student student) {
        Long id = nextId();
        studentStorageMap.put(id, student);
        studentSurnameStorage.studentCreate(id, student.getSurname());
        return id;
    }

    /**
     * Обновление данных о студенте
     * @param id индетификатор студента
     * @param student данные студента
     * @return true если данные были обновлены
     */
    public Boolean updateStudent(Long id, Student student) {
        if(!studentStorageMap.containsKey(id)) {
            return false;
        } else {
            String newStudent = student.getSurname();
            String oldSurname = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdated(id, oldSurname, newStudent);
            studentStorageMap.put(id, student);
            return true;
        }
    }

    /**
     * Удаляет данные о студенте
     * @param id идентификатор студента
     * @return возвращает true, если был удален, false если не найден по id
     */
    public Boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if(removed != null) {
            String surname = removed.getSurname();
            studentSurnameStorage.studentDeleted(id, surname);
        }
        return removed != null;
    }

    public void search(String surname){
        if (surname == null || surname.trim().isEmpty()) {
            printAll();
            return;
        }

        String[] surnames = surname.split(",");
        if (surnames.length == 1) {
            searchBySurname(surnames[0].trim());
        } else if (surnames.length == 2) {
            searchBySurnameRange(surnames[0].trim(), surnames[1].trim());
        } else {
            System.out.println("Некорректный ввод. Введите одну фамилию или две фамилии через запятую.");
        }
    }

    private void searchBySurname(String surname) {
        Set<Long> studentIds = studentSurnameStorage.getStudentBySurnameLessOrEqualThan(surname);
        if(studentIds.isEmpty()){
            System.out.println("Студенты с фамилие " + surname + "не найдены");
        } else {
            for (Long studentId : studentIds){
                Student student = studentStorageMap.get(studentId);
                if(student.getSurname().equals(surname)){
                    System.out.println(student);
                }
            }
        }
    }

    private void searchBySurnameRange(String startSurname, String endSurname) {
        Set<Long> studentIds = studentSurnameStorage.getStudentBySurnameInRange(startSurname, endSurname);
        if (studentIds.isEmpty()) {
            System.out.println("Студенты с фамилиями в диапазоне от " + startSurname + " до " + endSurname + " не найдены.");
        } else {
            for (Long studentId : studentIds) {
                Student student = studentStorageMap.get(studentId);
                if (student.getSurname().compareTo(startSurname) >= 0 && student.getSurname().compareTo(endSurname) <= 0) {
                    System.out.println(student);
                }
            }
        }
    }

    public Long nextId(){
        currentId = currentId + 1;
        return currentId;
    }

    public void printAll(){
        System.out.println(studentStorageMap);
    }

    public void printMap(Map<String, Long> data){
        data.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey() + " - " + e.getValue());
        });
    }

    public Map<String, Long> getCountByCourse() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        Student::getCourse,
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }

    public Map<String, Long> getCountByCity() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.groupingBy(
                  Student::getCity,
                  Collectors.counting()
                ));
        return res;
    }



        /*Map<String, Long> res = new HashMap<>();
        for(Student student : studentStorageMap.values()) {
            String key = student.getCourse();
            Long count = res.getOrDefault(key,0L);
            count++;
            res.put(key, count);
        }
        return res; }*/
}
