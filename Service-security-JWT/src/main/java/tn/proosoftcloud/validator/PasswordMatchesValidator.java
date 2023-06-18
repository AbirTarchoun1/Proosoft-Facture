package tn.proosoftcloud.validator;


import tn.proosoftcloud.payload.request.SignupRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignupRequest> {

    @Override
    public boolean isValid(final SignupRequest account, final ConstraintValidatorContext context) {
        return account.getPassword().equals(account.getMatchingPassword());
    }

}
