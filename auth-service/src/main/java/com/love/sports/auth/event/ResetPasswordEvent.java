package com.love.sports.auth.event;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class ResetPasswordEvent implements Event {


    @NotEmpty
    private String eventId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String userId;

}
