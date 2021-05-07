function validateMyForm()
{
    var val = 0;
    document.getElementById("lblGrSal").innerHTML = "";
    document.getElementById("lblLeSal").innerHTML = "";
    document.getElementById("lblFromDate").innerHTML = "";
    document.getElementById("lblToDate").innerHTML = "";

    var salMin = document.forms["mySalOverview"]["totSalary"].value;
    var salMax = document.forms["mySalOverview"]["totSalaryLess"].value;
    var fromYear = document.forms["mySalOverview"]["fromYear"].value;
    var fromMonth = document.forms["mySalOverview"]["fromMonth"].value;
    var toYear = document.forms["mySalOverview"]["toYear"].value;
    var toMonth = document.forms["mySalOverview"]["toMonth"].value;

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

function validateStaffForm()
{
    var val = 0;
    document.getElementById("lblGrSal").innerHTML = "";
    document.getElementById("lblLeSal").innerHTML = "";
    document.getElementById("lblFromDate").innerHTML = "";
    document.getElementById("lblToDate").innerHTML = "";

    var salMin = document.forms["staffSalOverview"]["totSalary"].value;
    var salMax = document.forms["staffSalOverview"]["totSalaryLess"].value;
    var fromYear = document.forms["staffSalOverview"]["fromYear"].value;
    var fromMonth = document.forms["staffSalOverview"]["fromMonth"].value;
    var toYear = document.forms["staffSalOverview"]["toYear"].value;
    var toMonth = document.forms["staffSalOverview"]["toMonth"].value;

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