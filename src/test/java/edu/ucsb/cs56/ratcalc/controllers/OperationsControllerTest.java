package edu.ucsb.cs56.ratcalc.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OperationsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAddPage_ContentType() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    public void getAddPage_hasNum1Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='num1']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

    @Test
    public void getAddPage_hasNum2Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='num2']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

    @Test
    public void getAddPage_hasDenom1Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='denom1']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

    @Test
    public void getAddPage_hasDenom2Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='denom2']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

}
