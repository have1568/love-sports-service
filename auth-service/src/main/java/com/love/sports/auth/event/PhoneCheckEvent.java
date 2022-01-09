package com.love.sports.auth.event;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class PhoneCheckEvent implements Event {

    @NotEmpty
    private String eventId;

    @NotEmpty
    private String code;

    @NotEmpty(message = "手机号不能为空")
    private String phoneNumber;

    @NotEmpty
    private String userId;

}
