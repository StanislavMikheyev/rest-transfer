package com.github.stanislav.mikheyev.resttransfer.currency;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDto {
    private Integer id;
    private String name;
    private String code;
}
