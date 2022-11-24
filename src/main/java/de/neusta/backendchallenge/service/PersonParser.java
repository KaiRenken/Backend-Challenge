package de.neusta.backendchallenge.service;

import de.neusta.backendchallenge.domain.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonParser {

    public Person parse(String userInfo) {
        String[] splittetUserInfo = userInfo.trim().split(" ");
        if (splittetUserInfo.length == 1 && splittetUserInfo[0].equals("")) {
            return new Person("", "", "", "", "");
        }

        if (splittetUserInfo.length < 3) {
            throw new IllegalArgumentException("Person is not complete");
        }

        List<String> infoSplit = new ArrayList<>(Arrays.asList(splittetUserInfo));

        String ldapUser = infoSplit.get(infoSplit.size() - 1);
        if (!(ldapUser.endsWith(")") && ldapUser.startsWith("("))) {
            throw new IllegalArgumentException(("Person is not complete"));
        }

        ldapUser = ldapUser.replace(")", "").replace("(", "");
        infoSplit.remove(infoSplit.size() - 1);

        String lastName = infoSplit.get(infoSplit.size() - 1);
        infoSplit.remove(infoSplit.size() - 1);

        String titel;
        if (infoSplit.get(0).contains(".")) {
            if (!infoSplit.get(0).equals("Dr.")) {
                throw new IllegalArgumentException("Title is not correct");
            }

            titel = "Dr.";
            infoSplit.remove(0);
        } else {
            titel = "";
        }

        if (infoSplit.isEmpty()){
            throw new IllegalArgumentException("Person is not complete");
        }

        String nameAddition = constructNameAddition(infoSplit.get(infoSplit.size() - 1));
        if (!nameAddition.equals("")) {
            infoSplit.remove(infoSplit.size() - 1);
        }

        StringBuilder firstName = new StringBuilder();

        for (String name : infoSplit) {
            firstName.append(" ").append(name);
            firstName = new StringBuilder(firstName.toString().trim());
        }
        return new Person(firstName.toString(), lastName, titel, nameAddition, ldapUser);
    }

    private String constructNameAddition(String s) {
        if (s.equals("von") || s.equals("de") || s.equals("van")) {
            return s;
        }
        return "";
    }
}

