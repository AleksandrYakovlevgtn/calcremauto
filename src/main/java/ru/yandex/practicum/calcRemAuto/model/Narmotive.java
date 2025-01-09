package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.*;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Narmotive {
    boolean read = false; // Прочитан файл или нет

}
