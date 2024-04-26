package com.example.project.annotation.registration_valid;

import com.example.project.security.model.dto.UserDto;
import com.example.project.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserDto userDto = (UserDto) object;
        return userDto.getPassword().equals(userDto.getMatchingPassword());
    }
}
