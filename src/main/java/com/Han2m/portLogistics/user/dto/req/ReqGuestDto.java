package com.Han2m.portLogistics.user.dto.req;

import com.Han2m.portLogistics.user.domain.Guest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqGuestDto{

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

    @Schema(description = "등록일 yyyy-mm-dd 형식", example = "2023-12-12")
    private LocalDate date;

    @Schema(description = "방문사유", example = "견학")
    private String reason;

    @Schema(description = "목적", example = "체험")
    private String goal;

    @Schema(description = "담당자 ID", example = "1")
    private Long workerId;

    @Schema(description = "허가된 부두", example = "[1,2,3]")
    private List<Long> wharfs;

    public Guest toEntity(){
        return  Guest.builder().
                nationality(nationality).
                name(name).
                birth(birth).
                sex(sex).
                phone(phone).
                reason(reason).
                goal(goal).
                date(date).
                isWorker(false).
                build();
    }

}