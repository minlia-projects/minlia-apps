package com.qianyi.dao;

import com.qianyi.domain.Person;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

/**
 * Created by will on 8/2/17.
 */
//@Mapper
public interface PersonDao extends MybatisRepository<Person,Long> {

    public Person findById(Long id);

    public int deleteById(Long id);



}
