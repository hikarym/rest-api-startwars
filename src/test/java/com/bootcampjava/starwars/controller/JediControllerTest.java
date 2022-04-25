package com.bootcampjava.starwars.controller;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JediControllerTest {

    @MockBean
    private JediService jediService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("GET /jedi/1 - SUCCESS")
    public void testGetJediByIdWithSuccess() throws Exception {

        // cenario
        Jedi mockJedi = new Jedi(1, "HanSolo", 10, 1);
        Mockito.doReturn(Optional.of(mockJedi)).when(jediService).findById(1);

        // execucao
        mockMvc.perform(get("/jedi/{id}", 1))

                // asserts
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("HanSolo")))
                .andExpect(jsonPath("$.strength", is(10)))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("GET /jedi/1 - Not Found")
    public void testGetJediByIdNotFound() throws Exception {

        Mockito.doReturn(Optional.empty()).when(jediService).findById(1);

        mockMvc.perform(get("/jedi/{1}", 1))
                .andExpect(status().isNotFound());

    }

    // TODO: Teste do POST com sucesso
    @Test
    @DisplayName("POST /jedi - Save jedi")
    public void testSaveJediWithSuccess() throws Exception {

        Jedi newJedi = new Jedi("HanSolo", 10);

        Jedi mockJedi = new Jedi(1, "HanSolo", 10, 1);

        Mockito.doReturn(mockJedi).when(jediService).save(Mockito.any());

        mockMvc.perform(post("/jedi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newJedi)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("HanSolo")))
                .andExpect(jsonPath("$.strength", is(10)))
                .andExpect(jsonPath("$.version", is(1)));

    }

    // TODO: Teste do PUT com sucesso
    @Test
    @DisplayName("PUT /jedi/1 - Update jedi com sucesso")
    public void testUpdateJediWithSuccess() throws Exception {

        Jedi newJedi = new Jedi("Leia", 10);

        Jedi mockJedi = new Jedi(1, "Leia", 10, 1);

        Mockito.doReturn(Optional.of(mockJedi)).when(jediService).findById(1);
        Mockito.doReturn(true).when(jediService).update(Mockito.any());

        mockMvc.perform(put("/jedi/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(newJedi)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"2\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Leia")))
                .andExpect(jsonPath("$.strength", is(10)))
                .andExpect(jsonPath("$.version", is(2)));

    }

    // TODO: Teste do PUT com uma versao igual da ja existente - deve retornar um conflito

    // TODO: Teste do PUT com erro - not found

    // TODO: Teste do delete com sucesso

    // TODO: Teste do delete com erro - deletar um id ja deletado

    // TODO: Teste do delete com erro  - internal server error




    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}