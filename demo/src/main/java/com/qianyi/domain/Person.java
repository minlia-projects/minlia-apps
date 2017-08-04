package com.qianyi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mybatis.annotations.Column;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by qianyi on 2017/8/1.
 */


@Entity
//@org.springframework.data.mybatis.annotations.Entity(name = "person")
@Table(name = "person")
@Getter
@Setter

//BATIS
@org.springframework.data.mybatis.annotations.Entity(name = "person")
public class Person  extends AbstractEntity{

    @JsonProperty
    @Column(name = "name")
    private String name;

    @JsonProperty
    @Column(name = "email")
    private String email;

    @JsonProperty
    @Column(name = "people_id")
    private Long peopleId;

//    @OneToOne
//    @org.springframework.data.mybatis.annotations.OneToOne
//    private Category category;


    @org.springframework.data.annotation.Transient
    @Transient
    private List<Person> people;



}
