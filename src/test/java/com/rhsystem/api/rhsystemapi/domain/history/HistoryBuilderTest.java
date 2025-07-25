package com.rhsystem.api.rhsystemapi.domain.history;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhsystem.api.rhsystemapi.domain.history.event.EntityPersistenceType;

import java.util.Arrays;
import java.util.Collection;

class Address {
    @TrackChange(label = "Rua")
    private final String street;
    @TrackChange(label = "Cidade")
    private final String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }
}

class Person {
    @TrackChange(label = "Nome")
    private final String name;
    @TrackChange(label = "E‑mail")
    private final String email;
    @TrackChange(label = "Endereço")
    private final Address address;
    @TrackChange(label = "Telefones")
    private final Collection<String> phones;

    public Person(String name, String email, Address address, Collection<String> phones) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Collection<String> getPhones() {
        return phones;
    }
}


class HistoryBuilderTest {

    public static void main(String[] args) throws Exception {
        Address oldAddr = new Address("Av. Brasil, 100", "Rio");
        Address newAddr = new Address("Av. Brasil, 100", "São Paulo");

        Person oldPerson = new Person(
                "Alice", "alice@ex.com",
                oldAddr,
                Arrays.asList("1111-1111", "2222-2222")
        );

        Person newPerson = new Person(
                "Alice B.", "",                // mudou nome e apagou e‑mail
                newAddr,                       // mudou cidade
                Arrays.asList("2222-2222", "3333-3333")  // removeu um telefone e criou outro
        );

        // 2) gera o histórico (passando null como user só para teste)
        History h = HistoryBuilder.build(
                EntityPersistenceType.UPDATE,
                newPerson, oldPerson,
                null
        );


        System.out.println(new ObjectMapper().writeValueAsString(h.getInfo()));
    }


}