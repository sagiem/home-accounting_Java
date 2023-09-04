package ru.sagiem.whattobuy.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@Sql("/family_controller/test_data.sql")
@Transactional
@ExtendWith(MockitoExtension.class)
public class FamilyRestControllerTest {

    @Mock
    FamilyRepository familyRepository;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    FamilyRestController controller;

//    @Test
//    @DisplayName("Описание")
//    void FamilyAllList_ReturnsValidResponseEntity(){
//        // given
//        var family = List.of(new Family(UUID.randomUUID(), 1, "семья", new User()))
//        doReturn(family).when(this.familyRepository).findAll();
//
//        //when
//        var responseEntity = this.controller.FamilyAllList();
//
//        //then
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
//        assertEquals(family, responseEntity.getBody());
//
//    }
}
