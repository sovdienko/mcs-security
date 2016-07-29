package io.ovd.mcs.security.auth.model;


import javax.persistence.*;

/**
 * Created by Sergey.Ovdienko on 28.07.2016.
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
