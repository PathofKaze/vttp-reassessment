package com.vttp.reassessment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vttp.reassessment.model.Order;
import com.vttp.reassessment.model.Response;
import com.vttp.reassessment.service.OrderService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
@RestController
@RequestMapping(path="/api")
public class PostingController {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private OrderService orderSvc;


    @PostMapping(path="/order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postPosting(
    @RequestPart MultipartFile userfile, 
    @RequestPart String name, 
    @RequestPart String email,
    @RequestPart String phone,
    @RequestPart String title,
    @RequestPart String description,
    @RequestParam MultipartFile image 
    
    ) {

    JsonObject result;
    String posting_id = UUID.randomUUID().toString().substring(0, 8);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(userfile.getContentType());
    //metadata.setContentLength(buff.length);
    Map userCustomMetadata  = new HashMap();
    userCustomMetadata.put("uploader", name);
    userCustomMetadata.put("fileSize", userfile.getSize());
    metadata.setUserMetadata(userCustomMetadata);

    try {
        PutObjectRequest putReq = new PutObjectRequest("chumbucket", 
                "%s/images/%s".formatted(name, posting_id), image.getInputStream(), metadata);
        putReq.setCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(putReq);

        result = Json.createObjectBuilder()
            .add("ObjId", posting_id)
            .build();
        return ResponseEntity.ok(result.toString());

    } catch (Exception e) {
        e.printStackTrace();

        result = Json.createObjectBuilder()
            .add("error", e.getMessage())
            .build();
    }

        Order order = new Posting();
            order.setPostingId(posting_id);
            order.setPostingDate(sdf.format(new Date()));
            order.setName(name);
            order.setEmail(email);
            order.setPhone(phone);
            order.setTitle(title);
            order.setDescription(description);
            order.setImage("https://chumbucket.sgp1.digitaloceanspaces.com/assessment/%s".formatted(posting_id));
    
            orderSvc.cachePosting(order);

    return ResponseEntity.status(500).body(result.toString());
    }

    @PutMapping(path="/posting/{id}")
    public ResponseEntity<String> confirmPost(@PathVariable String id) {

        Order p;
        Response resp;

        p = orderSvc.getPostingFromCache(id);
        
        if (p == null) {
            resp = new Response();
            resp.setCode(404);
            resp.setMessage("Posting ID %s not found".formatted(id));
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resp.toJson().toString());
        } else {
            orderSvc.getPostingFromCache(id);
            try {
                boolean insert = orderSvc.insertPosting(p.getPosting_id(), p.getSdf().toString(), p.getName()
                    , p.getEmail(), p.getPhone(), p.getTitle(), p.getDescription(), p.getImage());
                
                if (insert != true) 
                throw new IllegalArgumentException("Unable to add posting!");
                } catch (Exception ex) {
                    ex.getStackTrace();
                    throw ex;
                }
            resp = new Response();
            resp.setCode(200);
            resp.setMessage("Accepted %s".formatted(p.getPostingId()));
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(resp.toJson().toString());
        }

    }
    
}
