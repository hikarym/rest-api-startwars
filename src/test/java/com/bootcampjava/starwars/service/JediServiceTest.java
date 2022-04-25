
package com.bootcampjava.starwars.service;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JediServiceTest {

    @Autowired
    private JediService jediService;

    @MockBean
    private JediRepositoryImpl jediRepository;


    @Test
    @DisplayName("Should return Jedi with success")
    public void findBySuccessTest() {

        // cenario
        Jedi mockJedi = new Jedi(1, "Jedi Name", 10, 1);
        Mockito.doReturn(Optional.of(mockJedi)).when(jediRepository).findById(1);

        // execucao
        Optional<Jedi> returnedJedi  = jediService.findById(1);

        // assert
        Assertions.assertTrue(returnedJedi.isPresent(), "Jedi was not found");
        Assertions.assertSame(returnedJedi.get(), mockJedi, "Jedis must be the same");
    }

    // TODO: Criar teste de erro NOT FOUND
    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {

        Mockito.doReturn(Optional.empty()).when(jediRepository).findById(1);

        Optional<Jedi> returnedJedi = jediService.findById(1);

        Assertions.assertFalse(returnedJedi.isPresent(), "Jedi nao fo encontrado como precisava ser");
    }

    // TODO: Criar um teste pro findAll();
    @Test
    @DisplayName("Test findAll")
    void testFindAll() {

        Jedi mockJedi = new Jedi(1, "Jedi 1", 10, 1);
        Jedi mockJedi2 = new Jedi(2, "Jedi 2", 15, 3);
        Mockito.doReturn(Arrays.asList(mockJedi, mockJedi2)).when(jediRepository).findAll();

        // Execute the service call
        List<Jedi> jedis = jediService.findAll();

        Assertions.assertEquals(2, jedis.size(), "Jedis de mesma forca encontrados");
    }
}
