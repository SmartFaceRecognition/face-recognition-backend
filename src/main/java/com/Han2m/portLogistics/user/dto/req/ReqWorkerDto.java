package com.Han2m.portLogistics.user.dto.req;

import com.Han2m.portLogistics.user.domain.Worker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqWorkerDto {

    @Schema(description = "국적", example = "대한민국")
    private String nationality;
    @Schema(description = "이름", example = "김철수")
    private String name;
    @Schema(description = "성별 남자는 true,여자는 false", example = "true")
    private Boolean sex;
    @Schema(description = "생년월일 yymmdd 형식", example = "990402")
    private String birth;
    @Schema(description = "전화번호", example = "01012345678")
    private String phone;
    @Schema(description = "직책", example = "과장")
    private String position;
    @Schema(description = "허가된 부두", example = "[1,2,3]")
    private List<Long> wharfs;

    public Worker toEntity(){
         return Worker.builder().
                 nationality(nationality).
                 name(name).
                 position(position).
                 birth(birth).
                 sex(sex).
                 phone(phone).
                 build();
    }

}
