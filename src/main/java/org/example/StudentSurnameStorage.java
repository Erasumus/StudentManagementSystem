package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {
    private TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreate(Long id, String surname){
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname){
        surnamesTreeMap.get(surname).remove(id);
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname){
        studentDeleted(id, oldSurname);
        studentCreate(id, newSurname);
    }

    /**
     * Данный метод возвращает уникальные идентификаторы студентов
     * чьи фамилии меньше или равны переданной.
     * @return set
     */
    public Set<Long> getStudentBySurnameLessOrEqualThan(String surname){
        Set<Long> res = surnamesTreeMap.headMap(surname, true)
                .values()
                .stream()
                .flatMap(longs -> longs.stream())
                .collect(Collectors.toSet());
        return res;
    }

    /**
     * Данный метод возвращает уникальные идентификаторы студентов,
     * чьи фамилии находятся в заданном алфавитном диапазоне (включительно).
     *
     * @param startSurname начальная фамилия диапазона (включительно).
     * @param endSurname конечная фамилия диапазона (включительно).
     * @return set идентификаторов студентов, чьи фамилии находятся в заданном диапазоне.
     */
    public Set<Long> getStudentBySurnameInRange(String startSurname, String endSurname) {
        return surnamesTreeMap.subMap(startSurname, true, endSurname, true)
                .values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
