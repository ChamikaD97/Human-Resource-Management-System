function validateForm()
{
    document.getElementById("lblStTime").innerHTML = "";
    document.getElementById("lblEndTime").innerHTML = "";
    document.getElementById("lblSalDate").innerHTML = "";
    document.getElementById("lblReset").innerHTML = "";

    var val = 0;

    var stTime = document.forms["customize"]["startTime"].value;
    var endTime = document.forms["customize"]["endTime"].value;
    var salDate = document.forms["customize"]["salaryCalculation"].value;
    var reDate = document.forms["customize"]["fromDate"].value;
    var reMonth = document.forms["customize"]["fromMonth"].value;

    var t1,t2;
    t1 = Date.parse('01/01/2021 '+stTime);
    t2 = Date.parse('01/01/2021 '+endTime);

    if(t2 <= t1)
    {
        document.getElementById("lblStTime").innerHTML = "Invalid Start Time";
        document.getElementById("lblEndTime").innerHTML = "Invalid End Time";
        val=1;
        console.log("Invalid Time");
    }

    if(salDate=="")
    {
        document.getElementById("lblSalDate").innerHTML = "Invalid Date";
        val = 1;
        console.log("Invalid Salary Calculation Date");
    }

    if(reDate=="" || reMonth=="")
    {
        document.getElementById("lblReset").innerHTML = "Invalid Reset Date";
        val = 1;
        console.log("Invalid Reset Date");
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