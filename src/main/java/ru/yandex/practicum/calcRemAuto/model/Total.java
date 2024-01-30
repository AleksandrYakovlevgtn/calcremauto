package ru.yandex.practicum.calcRemAuto.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Total {
     Double malyr = 0.0;
     Double armatyrchik = 0.0;
     Double kuzovchik = 0.0;
     Double master = 0.0;
     Double total = 0.0;
}
