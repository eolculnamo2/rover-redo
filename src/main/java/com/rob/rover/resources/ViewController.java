package com.rob.rover.resources;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;

@Controller
public class ViewController {

  @GET
  public String homePage() {
    return "index";
  }
}
