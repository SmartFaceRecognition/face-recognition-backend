package com.Han2m.portLogistics.user.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("guest")
public class Guest extends Person{

    // 07.21. 차별점을 두려면 상시 출입인원을 추가 ?? guest는 견학 or 점검 느낌이고 자주 드나드는 사람은 상시출입증을 발급하도록 (차량번호 등 추가?)
    private String ssn; // 주민번호

}
