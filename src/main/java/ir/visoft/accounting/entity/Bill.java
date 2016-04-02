package ir.visoft.accounting.entity;

import ir.visoft.accounting.annotation.EntityField;
import ir.visoft.accounting.annotation.PK;
import ir.visoft.accounting.annotation.SortDate;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author ghazaleh
 */
public class Bill extends BaseEntity {
    
    @EntityField
    @PK
    @SortDate
    private Integer billId;
    @EntityField
    private Date previousDate;
    @EntityField
//    @SortDate
    private Date newDate;
    @EntityField
    private Integer previousFigure;
    @EntityField
    private Integer currentFigure;
    @EntityField
    private Integer cunsumption;
    @EntityField
    private Double abonman;
    @EntityField
    private Integer reduction;
    @EntityField
    private Integer services;
    @EntityField
    private Double costWater;
    @EntityField
    private Integer costBalance;
    @EntityField
    private Integer finalAmount;
    @EntityField
    private Integer userId;


    public Bill(Integer billId, Date preDate, Date currentDate, Integer preFigure, Integer currentFigure, Integer cunsumption, Double abonman, Integer reduction, Integer services, Double costWater, Integer costBalance, Integer finalAmount, Integer userId) {

        this.billId = billId;
        this.previousDate = preDate;
        this.newDate = currentDate;
        this.previousFigure = preFigure;
        this.currentFigure = currentFigure;
        this.cunsumption = cunsumption;
        this.abonman = abonman;
        this.reduction = reduction;
        this.services = services;
        this.costWater = costWater;
        this.costBalance = costBalance;
        this.finalAmount = finalAmount;
        this.userId = userId;
    }
    
    public Bill(Integer userId) {
        this.userId = userId;
    }

    public Bill() {}

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Date getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(Date previousDate) {
        this.previousDate = previousDate;
    }

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }

    public Integer getPreviousFigure() {
        return previousFigure;
    }

    public void setPreviousFigure(Integer previousFigure) {
        this.previousFigure = previousFigure;
    }

    public Integer getCurrentFigure() {
        return currentFigure;
    }

    public void setCurrentFigure(Integer currentFigure) {
        this.currentFigure = currentFigure;
    }

    public Integer getCunsumption() {
        return cunsumption;
    }

    public void setCunsumption(Integer cunsumption) {
        this.cunsumption = cunsumption;
    }

    public Double getAbonman() {
        return abonman;
    }

    public void setAbonman(Double abonman) {
        this.abonman = abonman;
    }

    public Integer getReduction() {
        return reduction;
    }

    public void setReduction(Integer reduction) {
        this.reduction = reduction;
    }

    public Integer getServices() {
        return services;
    }

    public void setServices(Integer services) {
        this.services = services;
    }

    public Double getCostWater() {
        return costWater;
    }

    public void setCostWater(Double costWater) {
        this.costWater = costWater;
    }

    public Integer getCostBalance() {
        return costBalance;
    }

    public void setCostBalance(Integer costBalance) {
        this.costBalance = costBalance;
    }

    public Integer getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Integer finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
