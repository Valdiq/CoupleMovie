package org.example.webserver.controller;

import com.amazonaws.services.s3.model.Bucket;
import lombok.RequiredArgsConstructor;
import org.example.domain.service.AWSS3Service;
import org.example.security.roleaccess.AdminRoleAccess;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AWSS3Controller {

    private final AWSS3Service awss3Service;

    @AdminRoleAccess
    @GetMapping("/getBuckets")
    public List<Bucket> getBucketList() {
        return awss3Service.getBucketList();
    }

    @AdminRoleAccess
    @GetMapping("/getPoster")
    public void getPoster(@RequestParam("objName") String objName) throws Exception {
        awss3Service.getObjectFromBucket(objName);
    }

    @AdminRoleAccess
    @PutMapping("/uploadPoster")
    public void uploadPoster(@RequestParam("objName") String objName, @RequestParam("url") String url) {
        awss3Service.putObjectInBucket(objName, url);
    }

}
