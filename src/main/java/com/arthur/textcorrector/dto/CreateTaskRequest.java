package com.arthur.textcorrector.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    @NotBlank(message = "Text must not be blank")
    @Size(min = 3, message = "Text must contain at least 3 characters")
    @Pattern(regexp = ".*[a-zA-Zа-яА-Я].*", message = "Text must contain at least one letter")
    private String text;

    @NotBlank(message = "Language must not be blank")
    @Pattern(regexp = "EN|RU", message = "Language must be EN or RU")
    private String language;
}
