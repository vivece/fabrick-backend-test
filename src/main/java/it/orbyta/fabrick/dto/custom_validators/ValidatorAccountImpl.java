package it.orbyta.fabrick.dto.custom_validators;

import it.orbyta.fabrick.dto.request.moneyTransfer.Account;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidatorAccountImpl implements ConstraintValidator<ValidatorAccount, Account> {
    private static final Pattern IBAN_PATTERN = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$", Pattern.CASE_INSENSITIVE);

    /**
     * Validates the account BIC requirement.
     * <p>
     * If the account code is provided as an Iban, the BIC code is optional.
     * If the account code is not an IBAN, the BIC code is mandatory.
     *
     * @param account the account to validate
     * @param context the validation context
     * @return true if the account is valid, false otherwise
     */
    @Override
    public boolean isValid(Account account, ConstraintValidatorContext context) {
        if (account == null || account.getAccountCode() == null) {
            return true;
        }
        boolean isIban = IBAN_PATTERN.matcher(account.getAccountCode()).matches();
        boolean isBicCode = account.getBicCode() != null && !account.getBicCode().isBlank();

        return isIban || isBicCode;
    }
}
