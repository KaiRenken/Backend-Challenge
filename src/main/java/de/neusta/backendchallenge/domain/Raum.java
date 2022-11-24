package de.neusta.backendchallenge.domain;

import java.util.List;

public class Raum {
    private final String room;
    private final List<Person> people;
    public Raum(String nummer,List<Person> bewohner) {
        this.room = nummer;
        this.people = bewohner;
    }

    public String getRoom() {
        return room;
    }

    public List<Person> getPeople() {
        return people;
    }
}
