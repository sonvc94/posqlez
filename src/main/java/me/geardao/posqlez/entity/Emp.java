package me.geardao.posqlez.entity;

import lombok.Data;
import me.geardao.posqlez.json.Column;
import me.geardao.posqlez.json.JsonField;

@Data
public class Emp {

    @JsonField(value = "id")
    @Column(name = "id")
    private String id;

    @JsonField(value = "name")
    @Column(name = "name")
    private String name;

    @JsonField(value = "Age")
    @Column(name = "age")
    private int age;

    @JsonField(value = "age")
    @Column(name = "salary")
    private Integer j;

}
