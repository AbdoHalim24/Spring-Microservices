package com.AbdoHalim.Accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AccountDto {

    @NotEmpty(message = "accountNumber can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "accountNumber must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "accountNumber can not be a null or empty")

    private String accountType;
    @NotEmpty(message = "accountNumber can not be a null or empty")

    private String branchAddress;
}

