function SalariestoPDF() {

    const  rowCount = document.getElementById("myTable").rows.length;
    const row = document.getElementById("myTable").rows[0].cells.length;
    const generateData = function (amount) {
        var result = [];

        for (var i = 0; i < amount; i += 1) {

            const EmployeeId = document.getElementById("myTable").rows[i].cells.item(0).innerHTML;
            var  From= document.getElementById("myTable").rows[i].cells.item(1).innerHTML;
            var To = document.getElementById("myTable").rows[i].cells.item(2).innerHTML;
            var NoPay = document.getElementById("myTable").rows[i].cells.item(3).innerHTML;
            var Absences = document.getElementById("myTable").rows[i].cells.item(4).innerHTML;
            var Basic = document.getElementById("myTable").rows[i].cells.item(6).innerHTML;
            var Daily = document.getElementById("myTable").rows[i].cells.item(5).innerHTML;
            var OTRate = document.getElementById("myTable").rows[i].cells.item(7).innerHTML;
            var OTHours = document.getElementById("myTable").rows[i].cells.item(8).innerHTML;
            var Total = document.getElementById("myTable").rows[i].cells.item(9).innerHTML;


            var data = {
                EmployeeId: EmployeeId,
                From: From,
                To: To,
                NoPay: NoPay,
                Absences: Absences,
                Basic: Basic,
                Daily: Daily,
                OTRate: OTRate,
                OTHours: OTHours,
                Total: Total
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
                width: 38,
                top:5,
                align: "center",
                padding: 0
            });
        }
        return result;
    }
    var headers = createHeaders([
        "EmployeeId",
        "From",
        "To",
        "NoPay",
        "Absences",
        "Basic",
        "Daily",
        "OTRate",
        "OTHours",
        "Total"
    ]);


    var doc = new jsPDF({orientation: "landscape" });
    doc.setFontType("bold");
    doc.setFontSize(32);
    doc.text("Human Resource Management System",50,25);


    doc.setFontType("normal");
    doc.setFontSize(16);
    doc.text("Staff Members Salaries ",125,35);

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
    doc.save("HRMS_Staff_Salaries_"+y+"_"+m+"_"+d);

}

function validateForm()
{
    var val = 0;
    document.getElementById("lblGrSal").innerHTML = "";
    document.getElementById("lblLeSal").innerHTML = "";
    document.getElementById("lblFromDate").innerHTML = "";
    document.getElementById("lblToDate").innerHTML = "";

    var salMin = document.forms["salReport"]["totSalary"].value;
    var salMax = document.forms["salReport"]["totSalaryLess"].value;
    var fromYear = document.forms["salReport"]["fromYear"].value;
    var fromMonth = document.forms["salReport"]["fromMonth"].value;
    var toYear = document.forms["salReport"]["toYear"].value;
    var toMonth = document.forms["salReport"]["toMonth"].value;

    var minSal = parseFloat(salMin);
    var maxSal = parseFloat(salMax);
    var frYr = parseInt(fromYear);
    var toYr = parseInt(toYear);
    var frMon = parseInt(fromMonth);
    var toMon = parseInt(toMonth);

    //Validate Salary Scale
    if(maxSal<minSal)
    {
        document.getElementById("lblGrSal").innerHTML = "Invalid Value";
        document.getElementById("lblLeSal").innerHTML = "Invalid Value";
        val=1;
    }
    //Validate From Date
    if(toYr<frYr)
    {
        document.getElementById("lblFromDate").innerHTML = "From Date need to be less than To Date";
        document.getElementById("lblToDate").innerHTML = "To Date need to be greater than From Date";
        console.log("To Date < From Date");
        val = 1;
    }
    else if(toYr==frYr)
    {
        if(toMon<=frMon)
        {
            document.getElementById("lblFromDate").innerHTML = "From Date need to be less than To Date";
            document.getElementById("lblToDate").innerHTML = "To Date need to be greater than From Date";
            console.log("To Date < From Date");
            val = 1;
        }
    }

    if(val!=0)
    {
        return false;
    }
    else
    {
        return true;
    }
}