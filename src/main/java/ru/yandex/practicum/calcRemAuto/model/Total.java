package ru.yandex.practicum.calcRemAuto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Total {
    private int malyr = 0;
    private int armatyrchik = 0;
    private int kuzovchik = 0;
    private int master = 0;
    private int total = 0;
}
