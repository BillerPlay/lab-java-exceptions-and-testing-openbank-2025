package com.ironhack.intro_to_spring_boot;

import com.ironhack.intro_to_spring_boot.controllers.PostController;
import com.ironhack.intro_to_spring_boot.database.AllEmployeesAndPatients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
public class PostControllerTests {
    @Autowired
    private MockMvc mockMvc;


}
