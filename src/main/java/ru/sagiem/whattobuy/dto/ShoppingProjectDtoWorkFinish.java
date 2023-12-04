package ru.sagiem.whattobuy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingProjectDtoWorkFinish {
    Integer inWork;
    Integer finish;
    Integer notWork;

}
