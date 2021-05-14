package leave;

public class LeaveBean {
    private  String empId,leaveId, toDate, senderId,fromDate,appDate,reason,status,empIdNotification;

    private int totPayedLeaves  ,tackenPayedLeaves;
    private int totNoPayedLeaves, remNoPayedLeaves,tackenNoPayLeaves;
    private int totMedicalLeaves, remMedicalLeaves ,tackenMedicalLeaves;
float remPayedLeaves;
    private String firstName,lName,NIC,type;

    public String getFName()
    {
        return firstName;
    }
    public void setEmpIdNotification(String empIdNotification)
    {

        this.empIdNotification = empIdNotification;
    }

    public String getEmpIdNotification()
    {
        return empIdNotification;
    }
    public void setFName(String firstName)
    {

        this.firstName = firstName;
    }

    public String getLName()
    {
        return lName;
    }
    public void setType(String type)
    {

        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setLName(String lName)
    {
        this.lName = lName;
    }
    public String getNIC()
    {
        return NIC;
    }
    public void setNIC(String NIC)
    {
        this.NIC = NIC;
    }
    public String getEmpId()
    {
        return empId;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public void setLeaveId(String leaveId)
    {
         this.leaveId=leaveId;
    }
    public String getLeaveId()
    {
        return leaveId;
    }
    public String getAuthorizedPersonId()
    {
        return senderId;
    }
    public void setAuthorizedPersonId(String senderId) {
        this.senderId = senderId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason(){
        return reason;
    }

    public String gettoDate(){
        return toDate;
    }
    public void settoDate(String toDate) {
        this.toDate = toDate;
    }
    public String getfromDate() {
        return fromDate;
    }
    public void setfromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    public String getappDate() {
        return appDate;
    }
    public void setappDate(String appDate)
    {
        this.appDate = appDate;
    }




    public int gettotPayedLeaves()
    {
        return totPayedLeaves;
    }
    public void settotPayedLeaves(int totPayedLeaves)
    {
        this.totPayedLeaves = totPayedLeaves;
    }

    public int gettotNoPayedLeaves()
    {
        return totNoPayedLeaves;
    }
    public void settotNoPayedLeaves(int totNoPayedLeaves)
    {
        this.totNoPayedLeaves = totNoPayedLeaves;
    }

    public int gettotMedicalLeaves()
    {
        return totMedicalLeaves;
    }
    public void settotMedicalLeaves(int totMedicalLeaves)
    {
        this.totMedicalLeaves = totMedicalLeaves;
    }

    public float getremPayedLeaves()
    {
        return remPayedLeaves;
    }
    public void setremPayedLeaves(float remPayedLeaves)
    {
        this.remPayedLeaves = remPayedLeaves;
    }

    public int getremNoPayedLeaves()
    {
        return remNoPayedLeaves;
    }
    public void setremNoPayedLeaves(int remNoPayedLeaves)
    {
        this.remNoPayedLeaves = remNoPayedLeaves;
    }


    public int getremMedicalLeaves()
    {
        return remMedicalLeaves;
    }
    public void setremMedicalLeaves(int remMedicalLeaves)
    {
        this.remMedicalLeaves = remMedicalLeaves;
    }

    public int gettackenPayedLeaves() {
        return tackenPayedLeaves;
    }
    public void settackenPayedLeaves(int tackenPayedLeaves)
    {
        this.tackenPayedLeaves = tackenPayedLeaves;
    }

    public int gettackenNoPayLeaves()
    {
        return tackenNoPayLeaves;
    }
    public void settackenNoPayLeaves(int tackenNoPayLeaves)
    {
        this.tackenNoPayLeaves = tackenNoPayLeaves;
    }

    public int gettackenMedicalLeaves() {
        return tackenMedicalLeaves;
    }
    public void settackenMedicalLeaves(int tackenMedicalLeaves)
    {
        this.tackenMedicalLeaves = tackenMedicalLeaves;
    }




    public String getstatus() {
        return status;
    }
    public void setstatus(String status) {
        this.status = status;
    }
}
