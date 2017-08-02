package com.qianyi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by qianyi on 2017/8/1.
 */
@Entity
//@org.springframework.data.mybatis.annotations.Entity(name = "person")
@Table(name = "person")
@Getter
@Setter
public class Person  extends AbstractEntity{

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @org.springframework.data.mybatis.annotations.Id
//    private Long id;
//    @Conditions({
//            @Condition,
//            @Condition(type = Part.Type.LIKE, properties = "name")
//    })
//    @Condition(type = Part.Type.LIKE)
    @JsonProperty
    private String name;

//    @Condition
@JsonProperty
    private String email;

}
