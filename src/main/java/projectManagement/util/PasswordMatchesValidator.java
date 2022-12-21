package projectManagement.util;

import projectManagement.dto.RegistrationDto;
import projectManagement.util.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistrationDto dto = (RegistrationDto) obj;
        return dto.getPassword().equals(dto.getMatchingPassword());
    }
}

