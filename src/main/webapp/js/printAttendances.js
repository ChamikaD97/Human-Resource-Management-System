function AttendancestoPDF() {

    const  rowCount = document.getElementById("myTable").rows.length;
    const row = document.getElementById("myTable").rows[0].cells.length;
    const generateData = function (amount) {
        var result = [];

        for (var i = 0; i < amount; i += 1) {

            const attendanceID = document.getElementById("myTable").rows[i].cells.item(0).innerHTML;
            var  EmployeeId= document.getElementById("myTable").rows[i].cells.item(1).innerHTML;
            var date = document.getElementById("myTable").rows[i].cells.item(2).innerHTML;
            var attendeTime = document.getElementById("myTable").rows[i].cells.item(3).innerHTML;
            var leaveTime = document.getElementById("myTable").rows[i].cells.item(4).innerHTML;
            var workedHours = document.getElementById("myTable").rows[i].cells.item(5).innerHTML;
            var Ot = document.getElementById("myTable").rows[i].cells.item(6).innerHTML;


            var data = {
                Attendance: attendanceID,
                Employee: EmployeeId,
                Date: date,
                Attend: attendeTime,
                Leave: leaveTime,
                WorkedHours: workedHours,
                OTHours: Ot
            };
            result.push(Object.assign({}, data));
        }
        return result;
    };
    function createHeaders(keys) {
        var result = [];
        for (var i = 0; i < keys.length; i += 1) {
            result.push({
                id: keys[i],
                name: keys[i],
                prompt: keys[i],
                width: 54.2,
                top:5,
                align: "center",
                padding: 0
            });
        }
        return result;
    }
    var headers = createHeaders([
        "Attendance",
        "Employee",
        "Date",
        "Attend",
        "Leave",
        "WorkedHours",
        "OTHours"
    ]);

    var doc = new jsPDF({orientation: "landscape" });
    doc.setFontType("bold");
    doc.setFontSize(32);
    doc.text("Human Resource Management System",50,25);


    doc.setFontType("normal");
    doc.setFontSize(16);
    doc.text("Staff Members Attendances ",125,35);

    var date = new Date();
    var d=date.getDate();
    var m = date.getMonth();
    var y =date.getFullYear();
    doc.setLineWidth(0.5);
    doc.line(6, 42, 291, 42);


    doc.setFontSize(14);
    doc.text("Date  "+y+"-"+m+1+"-"+d,10,50);

    var detail = document.getElementById("result").rows[0].cells.item(0).innerHTML;

    doc.setFontSize(14);
    doc.text(detail,40,45);

    doc.setFontSize(14);
    doc.text(rowCount+" Records ",262,50);

    doc.setLineWidth(0.5);
    doc.line(6, 55, 291, 55);

    doc.setFontSize(20);
    doc.setTextColor("#000");
    doc.table(6,60, generateData(rowCount), headers);
    doc.save("HRMS_Staff_Attendances_"+y+"_"+m+"_"+d);

}

function validateForm()
{
    document.getElementById("lblFromDate").innerHTML = "";
    document.getElementById("lblToDate").innerHTML = "";

    var frDate = document.forms["attendanceReport"]["fromDate"].value;
    var toDate = document.forms["attendanceReport"]["toDate"].value;

    const d1 = new Date(frDate);
    const d2 = new Date(toDate);
    const today = new Date();

    //Validate From and To Dates
    if(d2<d1)
    {
        document.getElementById("lblFromDate").innerHTML = "From Date need to be less than To Date";
        document.getElementById("lblToDate").innerHTML = "To Date need to be greater than From Date";
        console.log("To Date < From Date");
        return false;
    }
    else
    {
        return true;
    }
}