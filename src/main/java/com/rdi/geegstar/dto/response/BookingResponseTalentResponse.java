package com.rdi.geegstar.dto.response;

import com.rdi.geegstar.enums.TalentCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class BookingResponseTalentResponse {
    private Long id;
    private String displayName;
    private String bio;
    private TalentCategory category;
    private String profilePicture;
    private List<String> portfolio;
}
