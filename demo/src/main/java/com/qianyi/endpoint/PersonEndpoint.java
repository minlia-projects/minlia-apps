package com.qianyi.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.endpoint.AbstractApiEndpoint;
import com.minlia.cloud.query.body.ApiSearchRequestBody;
import com.minlia.cloud.query.condition.QueryCondition;
import com.minlia.cloud.query.specification.jpa.JpaSpecifications;
import com.qianyi.body.PersonSearchRequestBody;
import com.qianyi.dao.PersonDao;
import com.qianyi.domain.Person;
import com.qianyi.repository.PersonRepository;
import com.qianyi.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by will on 8/2/17.
 */
@RestController
@RequestMapping("/api/v1/person")
@Slf4j
@Api(tags = "用户", value = "用户", description = "用户")
public class PersonEndpoint extends AbstractApiEndpoint<PersonService, Person, Long> {

    public PersonEndpoint() {
        super();
    }

    @Autowired
    JpaSpecifications jpaSpecifications;


    @Autowired
    PersonDao personDao;


    @Autowired
    PersonRepository personRepository;




    @ApiOperation(
            value = "获取一个指定ID的实体",
            notes = "获取一个指定ID的实体",
            httpMethod = "GET",
            produces = "application/json"
    )
    @GetMapping(value = "/{id}")
    public StatefulBody findOne(@PathVariable Long id) {
        Person found = personDao.findById(id);

        return SuccessResponseBody.builder().payload(found).build();
//        return super.findOne(id);
    }


    @PostMapping
    public StatefulBody test(@PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<PersonSearchRequestBody> body) {

        Person person = new Person();
        person.setName("xxxx");
        person.setEmail("abc.xxx@dkjfd.com");
        //使用jpa保存
        personRepository.save(person);


        //使用mybatis 查询
        Person found = personDao.findById(1l);


        log.debug("PERSON_DAO {}", personDao);
        log.debug("PERSON_REPOSITORY {}", personRepository);


//        ApiSearchRequestBody<PersonSearchRequestBody> body = new ApiSearchRequestBody<>();
        body.getConditions().add(new QueryCondition("name", QueryOperator.like, "x"));
        body.getConditions().add(new QueryCondition("email", QueryOperator.like, "x"));
//        List pageableFound=personDao.findAll(querySpecifications.buildSpecification(body));


        Page<Person> page = personRepository.findAll(jpaSpecifications.buildSpecification(body), pageable);
//        for (Person p : page.getContent()) {
//            log.debug("getEmail {}", p.getEmail());
//            log.debug("getName {}", p.getName());
//        }
        return SuccessResponseBody.builder().payload(page).build();
    }


    @PostMapping(value = "delete/{id}")
    public StatefulBody delete(@PathVariable Long id) {
        personRepository.delete(id);
//        personDao.deleteById(id);
        return SuccessResponseBody.builder().payload(null).build();
    }
}
