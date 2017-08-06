package com.qianyi.service;

import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.body.BatisApiSearchRequestBody;
import com.minlia.cloud.query.specification.jpa.body.JpaApiSearchRequestBody;
import com.minlia.cloud.service.AbstractService;
import com.qianyi.dao.PersonDao;
import com.qianyi.domain.Person;
import com.qianyi.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by will on 8/2/17.
 */
@Service
@Slf4j
public class PersonServiceImpl extends AbstractService<PersonRepository, PersonDao, Person, Long> implements PersonService {


    @Override
    public List<Person> findListByBody(BatisApiSearchRequestBody batisApiSearchRequestBody) {
        return null;
    }

    @Override
    public Page<Person> findPageByBody(JpaApiSearchRequestBody jpaApiSearchRequestBody, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Person> findPage(Pageable pageable, SpecificationDetail<Person> specificationDetail) {
        return null;
    }

    @Override
    public Page<Person> findBasePage(Pageable pageable, SpecificationDetail<Person> specificationDetail, boolean b) {
        return null;
    }

    @Override
    public Page<Person> findBasePage(Pageable pageable, SpecificationDetail<Person> specificationDetail, Boolean aBoolean, String s, String s1) {
        return null;
    }
}
