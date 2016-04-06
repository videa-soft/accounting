package ir.visoft.accounting.entity;


import ir.visoft.accounting.annotation.EntityField;
import ir.visoft.accounting.annotation.PK;
import java.util.Date;

/**
 * @author Amir
 */
public class AccountBalance extends BaseEntity {

    @EntityField
    @PK
    private Integer accId;
    @EntityField
    private Date createDate;
    @EntityField
    private Integer debit;
    @EntityField
    private Integer credit;
    @EntityField
    private Integer accountBalance;
    @EntityField
    private String description;
    @EntityField
    private Integer userId;
    
    public AccountBalance(Integer accId, Date createDate, Integer debit, Integer credit, Integer accountBalance, String description, Integer userId) {
        this.accId = accId;
        this.createDate = createDate;
        this.debit = debit;
        this.credit = credit;
        this.accountBalance = accountBalance;
        this.description = description;
        this.userId = userId;
    }
    
    public AccountBalance(){}
    
    public AccountBalance(Integer userId) {
        this.userId = userId;
    }
    
     public AccountBalance(Integer userId, Integer accId) {
        this.userId = userId;
        this.accId = accId ;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDebit() {
        return debit;
    }

    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Integer accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
        
}
