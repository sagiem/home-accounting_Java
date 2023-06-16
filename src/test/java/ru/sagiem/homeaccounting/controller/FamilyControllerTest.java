package ru.sagiem.homeaccounting.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.sagiem.homeaccounting.model.Family;
import ru.sagiem.homeaccounting.model.User;
import ru.sagiem.homeaccounting.repository.FamilyRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@Sql("/family_controller/test_data.sql")
@Transactional
@ExtendWith(MockitoExtension.class)
public class FamilyControllerTest {

    @Mock
    FamilyRepository familyRepository;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    FamilyController controller;

    @Test
    @DisplayName("Описание")
    void FamilyAllList_ReturnsValidResponseEntity(){
        // given
        var family = List.of(new Family(UUID.randomUUID(), 1, "семья", new User()))
        doReturn(family).when(this.familyRepository).findAll();

        //when
        var responseEntity = this.controller.FamilyAllList();

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(family, responseEntity.getBody());

    }
}
