package br.com.mtbassi.shortlink.controller;

import br.com.mtbassi.shortlink.dto.RequestDTO;
import br.com.mtbassi.shortlink.dto.ResponseDTO;
import br.com.mtbassi.shortlink.service.ShortLinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ShortLinkControllerTest {

    private static final String BASE_URL = "/shortlink";

    @InjectMocks
    private ShortLinkController shortLinkController;

    @Mock
    private ShortLinkService shortLinkService;

    private RequestDTO requestDTO;

    private ResponseDTO responseDTO;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shortLinkController)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();

        requestDTO = new RequestDTO();
        requestDTO.setOriginalLink("https://github.com/mtbassi");

        responseDTO = ResponseDTO.builder()
                .shortLink("/shortlink/yourshortlink")
                .originalLink("https://github.com/mtbassi")
                .access(0)
                .creationDate(LocalDateTime.of(2000, Month.AUGUST, 12, 0, 0))
                .build();
    }

    @Test
    @SneakyThrows
    void shouldReturnCreatedForValidCreation() {
        when(shortLinkService.shortenLink(any())).thenReturn(responseDTO);
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequest(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.short_link").value("/shortlink/yourshortlink"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.original_link").value("https://github.com/mtbassi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.access").value(0))
                .andReturn();
        verify(shortLinkService).shortenLink(any());
        verifyNoMoreInteractions(shortLinkService);
    }

    @Test
    @SneakyThrows
    void shouldRedirectWhenProvidingShortLink() {
        when(shortLinkService.retrieveOriginalLink(anyString())).thenReturn("https://github.com/mtbassi");
        mockMvc.perform(get(BASE_URL + "/{id}", "yourshortlink"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("https://github.com/mtbassi"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "https://github.com/mtbassi"));
        verify(shortLinkService).retrieveOriginalLink(any());
        verifyNoMoreInteractions(shortLinkService);
    }

    @Test
    @SneakyThrows
    void shouldReturnGeneralInformationWhenPassingShortLinkId() {
        when(shortLinkService.info(anyString())).thenReturn(responseDTO);
        mockMvc.perform(get(BASE_URL + "/info/{id}", "yourid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRequest(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.short_link").value("/shortlink/yourshortlink"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.original_link").value("https://github.com/mtbassi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.access").value(0))
                .andReturn();
        verify(shortLinkService).info(any());
        verifyNoMoreInteractions(shortLinkService);
    }

    @SneakyThrows
    private String getRequest(Object value) {
        return new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(value);
    }

}
