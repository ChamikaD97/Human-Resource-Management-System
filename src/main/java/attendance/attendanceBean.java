package attendance;
public class attendanceBean {
    private String empId,  workedHoursFrom,workedHoursTo,otHoursFrom,otHoursTo,from,to,attendanceId,date,attendTime;
    private float otHours;
    private String workedHr,leaveTime;

    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }


    public String getWorkedHoursFrom() {
        return workedHoursFrom;
    }
    public void setWorkedHoursFrom(String workedHoursFrom) {
        this.workedHoursFrom = workedHoursFrom;
    }


    public String getWorkedHoursTo() {
        return workedHoursTo;
    }
    public void setWorkedHoursTo(String workedHoursTo) {
        this.workedHoursTo = workedHoursTo;
    }


    public String getOtHoursFrom() {
        return otHoursFrom;
    }
    public void setOtHoursFrom(String otHoursFrom) {
        this.otHoursFrom = otHoursFrom;
    }


    public String getOtHoursTo() {
        return otHoursTo;
    }
    public void setOtHoursTo(String otHoursTo) {
        this.otHoursTo = otHoursTo;
    }


    public String getFromDate() {
        return from;
    }
    public void setFromDate(String from) {
        this.from = from;
    }


    public String getToDate() {
        return to;
    }
    public void setToDate(String to) {
        this.to = to;
    }

    public String getAttendanceId() {
        return attendanceId;
    }
    public void setAttendId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendTime() {
        return attendTime;
    }
    public void setAttendTime(String attendTime) {
        this.attendTime = attendTime;
    }

    public String getWorkedHrs() {
        return workedHr;
    }
    public void setWorkedHrs(String workedHr) {
        this.workedHr = workedHr;
    }

    public float getotHours() {
        return otHours;
    }
    public void setOtHours(float otHours) { this.otHours = otHours;}

    public String getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

}
