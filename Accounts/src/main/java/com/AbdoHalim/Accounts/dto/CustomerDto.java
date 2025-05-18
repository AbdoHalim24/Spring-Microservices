package com.AbdoHalim.Accounts.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "name can not be a null or empty")
    @Size(min = 5,max =30,message = "the length of the customer name should be between 5 to 30 character ")
    private String name;
    @NotEmpty(message = "email can not be a null or empty")
    @Email(message = "enter a valid email value")
    private String email;

    @NotEmpty(message = "mobileNumber can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{11})",message = "mobile number must be 11 digites")
    private String mobileNumber;
    private AccountDto accountDto;

}
