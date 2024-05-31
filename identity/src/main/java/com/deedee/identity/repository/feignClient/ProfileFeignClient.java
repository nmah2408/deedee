package com.deedee.identity.repository.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile", url = "${app.url.profile-service}")
public interface ProfileFeignClient {

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    void createProfile (@RequestBody String email);
}
