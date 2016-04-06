package ir.visoft.accounting.entity;

import ir.visoft.accounting.annotation.EntityField;
import ir.visoft.accounting.annotation.PK;

/**
 * @author Amir
 */
public class User extends BaseEntity {

    @EntityField
    @PK
    private Integer userId;
    @EntityField
    private String username;
    @EntityField
    private String password;
    @EntityField
    private String firstName;
    @EntityField
    private String lastName;
    @EntityField
    private String customerNumber;
    @EntityField
    private String homeAddress;
    @EntityField
    private String workAddress;
    @EntityField
    private String phoneNumber;
    @EntityField
    private String nationalCode;
    @EntityField
    private Integer familyCount;

    public User(Integer userId, String username, String password, String firstName, String lastName, String customerNumber, String homeAddress, String workAddress, String phoneNumber, String nationalCode, Integer familyCount) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerNumber = customerNumber;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
        this.phoneNumber = phoneNumber;
        this.nationalCode = nationalCode;
        this.familyCount = familyCount;
    }

    public User(String username) {
        this.username = username;
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User() {}


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Integer getFamilyCount() {
        return familyCount;
    }

    public void setFamilyCount(Integer familyCount) {
        this.familyCount = familyCount;
    }
}

