function validateMyForm()
{
    document.getElementById("lblFromDate").innerHTML = "";
    document.getElementById("lblToDate").innerHTML = "";

    var frDate = document.forms["myAttendHist"]["fromDate"].value;
    var toDate = document.forms["myAttendHist"]["toDate"].value;

    const d1 = new Date(frDate);
    const d2 = new Date(toDate);
    const today = new Date();

    //Validate From and To Date
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

function validateStaffForm()
{
    document.getElementById("lblFromDate").innerHTML = "";
    document.getElementById("lblToDate").innerHTML = "";

    var frDate = document.forms["staffAttendHist"]["fromDate"].value;
    var toDate = document.forms["staffAttendHist"]["toDate"].value;

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