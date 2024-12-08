package org.example.webserver.controller;

import com.amazonaws.services.s3.model.Bucket;
import lombok.RequiredArgsConstructor;
import org.example.domain.service.AWSS3Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AWSS3Controller {

    private final AWSS3Service awss3Service;

    @GetMapping("/getBuckets")
    public List<Bucket> getBucketList() {
        return awss3Service.getBucketList();
    }

    @GetMapping("/getPoster")
    public void getPoster(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName) throws Exception {
        awss3Service.getObjectFromBucket(bucketName, objName);
    }

    @PutMapping("/uploadPoster")
    public void uploadPoster(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName, @RequestParam("url") String url) {
        awss3Service.putObjectInBucket(bucketName, objName, url);
    }

}
