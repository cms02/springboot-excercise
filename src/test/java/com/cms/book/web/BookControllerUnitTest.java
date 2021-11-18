package com.cms.book.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cms.book.domain.Book;
import com.cms.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


/**
 * 단위 테스트( Controller 관련 로직만 띄우기, Filter , ControllerAdvice ...) 
 * @WebMvcTest 실제 Controller, Filter, ControllerAdvice 를 IoC 컨테이너에 띄움
 * @MockBean IoC환경에 bean 등록됨
 */
@Slf4j
@WebMvcTest
public class BookControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean //IoC환경에 bean 등록됨
	private BookService bookService;
	
	//BDDMockito 패턴 given, when, then
	@Test
	public void save_테스트() throws Exception{
		// given (테스트를 하기 위한 준비)
		Book book = new Book(null, "스프링 따라하기","cms");
		String content = new ObjectMapper().writeValueAsString(book);
		when(bookService.저장하기(book)).thenReturn(new Book(1L,"스프링 따라하기","cms"));
		
		//when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/book")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(content)
						.accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then (검증)
		resultAction
			.andExpect(status().isCreated()) //201
			.andExpect(jsonPath("$.title").value("스프링 따라하기"))
			.andDo(MockMvcResultHandlers.print());
			
			
		
	}


}
