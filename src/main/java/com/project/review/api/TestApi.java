package com.project.review.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

  @GetMapping("/hello/world")
  public String helloWorld(){
    return "[GET] Hello, world!";
  }

  @PostMapping("/hello/world")
  public String posthelloWorld(){
    return "[Post] Hello, world!";
  }
  @PutMapping("/hello/world")
  public String puthelloWorld(){
    return "[Put] Hello, world!";
  }
  @DeleteMapping("/hello/world")
  public String deletehelloWorld(){
    return "[Delete] Hello, world!";
  }
}
