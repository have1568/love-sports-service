package com.love.sports.auth.event;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public
class EmailCheckEvent implements Event {

    @NotEmpty
    private String eventId;

    @NotEmpty
    private String code;

    @Email(message = "邮箱不能为空")
    private String email;

    @NotEmpty
    private String userId;


}
