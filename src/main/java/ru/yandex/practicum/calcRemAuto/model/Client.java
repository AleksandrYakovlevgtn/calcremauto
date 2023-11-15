package ru.yandex.practicum.calcRemAuto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {
    String name;
    String foneNumber;
    String numberAuto;
    String modelAuto;
}