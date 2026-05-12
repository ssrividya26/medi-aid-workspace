package com.cts.enrollment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="SCHEME-SERVICE")
public interface SchemeClient {

    @GetMapping("/api/schemes/{id}")
    Object getSchemeById(@PathVariable("id") Long id);
}
