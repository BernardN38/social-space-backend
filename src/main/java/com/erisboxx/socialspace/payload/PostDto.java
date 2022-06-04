package com.erisboxx.socialspace.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class PostDto {
    Long senderId;
    Long receiverId;
    @NotEmpty
    private String textContent;
    private Date date = new Date();
    private String imageSrc;
}
