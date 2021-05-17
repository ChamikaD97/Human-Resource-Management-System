package salary;

public class SalaryBean {

    private String empId,fName,lName;
    private int extraHolidays;
    private int noPayes;
    private int totalLeaves, officeWorkingDays;
    private String salaryId;
    private String otHours;
    private String fromDate, toDate,resultGreater,resultLess;
    private int absences;
    private float dailyPayment;
    private float basic,totOtHours;
    private float otRate;
    private float totalSalary,totSalaryLess;

    public String getFName()
    {
        return fName;
    }
    public void setFName(String fName)
    {
        this.fName = fName;
    }
    public String getLName()
    {
        return lName;
    }
    public void setLName(String lName)
    {
        this.lName = lName;
    }


    public int getExtraHolidays() {
        return extraHolidays;
    }

    public void setExtraHolidays(int extraHolidays) {
        this.extraHolidays = extraHolidays;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setNoPayes(int noPayes) {
        this.noPayes = noPayes;
    }

    public int getNoPayes() {
        return noPayes;
    }

    public void setTotalLeaves(int noPayes) {
        this.totalLeaves = totalLeaves;
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public String getSalarId() {
        return salaryId;
    }

    public void setOfficeWorkingDays(int officeWorkingDays) {
        this.officeWorkingDays = officeWorkingDays;
    }

    public int getOfficeWorkingDays() {
        return officeWorkingDays;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setAbsences(int absences) {
        this.absences = absences;
    }

    public int getAbsences() {
        return absences;
    }

    public void setDailyPayment(float dailyPayment) {
        this.dailyPayment = dailyPayment;
    }

    public float getDailyPayment() {
        return dailyPayment;
    }

    public void setBasic(float basic) {
        this.basic = basic;
    }

    public float getTotalOtHours() {
        return totOtHours;
    }

    public void setTotalOtHours(float totOtHours) {
        this.totOtHours = totOtHours;
    }

    public float getBasic() {
        return basic;
    }

    public void setOtRate(float otRate) {
        this.otRate = otRate;
    }

    public float getOtRate() {
        return otRate;
    }

    public void setOtHours(String otHours) {
        this.otHours = otHours;
    }

    public String getOtHours() {
        return otHours;
    }

    public void setTotalSalary(float totalSalary) {
        this.totalSalary = totalSalary;
    }

    public float getTotalSalary() {
        return totalSalary;
    }

    public void setTotSalaryLess(float totSalaryLess) {
        this.totSalaryLess = totSalaryLess;
    }
    public float getTotSalaryLess() {
        return totSalaryLess;
    }

    public void setResultGreater(String resultGreater) {
        this.resultGreater = resultGreater;
    }
    public String getResultGreater() {
        return resultGreater;
    }

    public void setResultLess(String resultLess) {
        this.resultLess = resultLess;
    }
    public String getResultLess() {
        return resultLess;
    }
}
