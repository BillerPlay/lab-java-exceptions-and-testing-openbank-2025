package com.ironhack.intro_to_spring_boot;

import com.ironhack.intro_to_spring_boot.controllers.GetController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GetController.class)
class GetControllerTests {
    @Autowired
    private MockMvc mockMvc;
}
