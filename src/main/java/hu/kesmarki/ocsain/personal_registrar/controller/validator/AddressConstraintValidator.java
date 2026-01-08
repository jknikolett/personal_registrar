package hu.kesmarki.ocsain.personal_registrar.controller.validator;

import hu.kesmarki.ocsain.personal_registrar.dto.AddressDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class AddressConstraintValidator implements ConstraintValidator<AddressConstraint, List<AddressDTO>> {
    @Override
    public void initialize(AddressConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<AddressDTO> addressDTOS, ConstraintValidatorContext constraintValidatorContext) {
        if(addressDTOS == null || addressDTOS.size()<2){
            return true;
        }
        if(addressDTOS.size()>2){
            return false;
        }
        return !addressDTOS.get(0).getAddressType().equals(addressDTOS.get(1).getAddressType());
    }
}
