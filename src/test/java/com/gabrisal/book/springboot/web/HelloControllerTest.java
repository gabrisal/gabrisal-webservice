package com.gabrisal.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HelloControllerTest {

    /*
    * 1. @RunWith(springRunner.class)
    *   - 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴
    *   - SpringRunner라는 스프링 실행자 사용
    *   - 스프링부트 테스트와 JUnit 사이의 연결자 역할
    *
    * 2. @WebMvcTest
    *   - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
    *   - 선언할 경우, @Controller, @ControllerAdvice 등을 사용할 수 있다
    *   - 단, @Service, @Component, @Repository 등은 사용할 수 없다.
    *
    * 3. @AutoWired
    *   - 스프링이 관리하는 빈(Bean)을 주입받음
    *
    * 4. MockMvc
    *   - 웹 API를 테스트할 때 사용
    *   - 스프링MVC 테스트의 시작점
    *   - 이 클래스를 통해 HTTP GET, POST 등에 대한 API테스트를 할 수 있음
    * */

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 함
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())             // 결과 검증, header Status 검증(200)
                .andExpect(content().string(hello));    // 응답 본문 내용 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        /*
            1. param
                - API 테스트 시 사용될 요청 파라미터 설정
                - 단, 값은 String만 허용
                - 숫자/날짜 등의 데이터도 문자열로 변경해야 함
            2. jsonPath
                - JSON 응답값을 필드별로 검증할 수 있는 메소드
                - $를 기준으로 필드명 명시
         */
        mvc.perform(get("/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}