package com.example.shop.services;

import com.example.shop.pojo.Human;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {

    private static final Human[] HUMANS = new Human[]{
            new Human("Ivan", 25),
            new Human("Sergey", 45),
            new Human("Petr", 18),
            new Human("Valentina", 77),
            new Human("Lyudmila", 20),
            new Human("Arina", 37),
            new Human("Vyacheslav", 60),
            new Human("Yuri", 29),
            new Human("Tatyana", 16)
    };

    public List<Human> getHumanByAge(Integer from, Integer to) {

        List<Human> humans = new ArrayList<>();

        if (from != null && to != null) {
            for (Human human : HUMANS) {
                if (human.getAge() >= from && human.getAge() <= to) {
                    humans.add(human);
                }
            }
        } else if (to == null && from != null) {
            for (Human human : HUMANS) {
                if (human.getAge() >= from) {
                    humans.add(human);
                }
            }
        } else if (to != null && from == null) {
            for (Human human : HUMANS)
                if (human.getAge() <= to) {
                    humans.add(human);
                }
        } else {
            humans.addAll(Arrays.asList(HUMANS));
        }
        return humans;
    }


}
