package com.ecore.squad.request.user;

import com.ecore.squad.model.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

@FeignClient(name = "user", url = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com")
public interface UserService {

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<User> findAllUser();

    @GetMapping(path = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<User> findUserById(@PathVariable(name = "userId") String userId);
}
