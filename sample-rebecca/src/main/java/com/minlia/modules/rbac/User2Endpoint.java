package com.minlia.modules.rbac;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.query.UserQueryRequestBody;
import com.minlia.modules.rbac.service.UserQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 8/2/17.
 */
@RestController
@RequestMapping("/api/user2")
@Slf4j
@Api(tags = "用户2", value = "用户2", description = "用户2")
public class User2Endpoint {

//    @Autowired
//    User2Dao user2Dao;
    @Autowired
    UserDao userDao;

    @Autowired
    UserQueryService userQueryService;

//    @Autowired
//    UserRepository userRepository;

    @ApiOperation(
            value = "获取一个指定ID的实体",
            notes = "获取一个指定ID的实体",
            httpMethod = "POST",
            produces = "application/json"
    )
    @PostMapping(value = "")
    public StatefulBody findOne(@PageableDefault Pageable pageable, @RequestBody ApiQueryRequestBody<UserQueryRequestBody> body) {

//        Page<User> foundUser2 = user2Dao.findAll222(pageable);


        for(int i=0;i<50;i++){
            User user=new User();
            String random= RandomStringUtils.randomAlphabetic(6);
            user.setFirstName(random);
            random= RandomStringUtils.randomAlphabetic(6);
            user.setLastName(random);
            random= RandomStringUtils.randomAlphabetic(6);
            user.setEmail(random+"@qq.com");
            userDao.insert(user);
        }

//        User userFound = userDao.findOneByUsernameOrEmailOrCellphone("admin", "admin", "admin");
        Page<User> userFound = userDao.findUseMapper22(pageable,"%x%");
//        Page<User> userFound = userDao.findUseMapper444(pageable);
        log.debug("UserFound {}", userFound);

        return SuccessResponseBody.builder().payload(userFound).build();
    }


}
