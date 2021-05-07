var table = document.getElementById('tableResult');

for (var i = 1; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {
        document.getElementById("empId").value = this.cells[0].innerHTML;

    };
}

function validateForm()
{
    document.getElementById("lblFromDate").innerHTML = "";
    document.getElementById("lblToDate").innerHTML = "";
    document.getElementById("lblReason").innerHTML = "";
    document.getElementById("lblLeaveType").innerHTML = "";
    document.getElementById("lblAuthorizedPerson").innerHTML = "";

    var val = 0;

    var frDate = document.forms["applyForLeave"]["fromDate"].value;
    var toDate = document.forms["applyForLeave"]["toDate"].value;
    var reason = document.forms["applyForLeave"]["reason"].value.trim();
    var type = document.forms["applyForLeave"]["leaveType"].value;
    var person = document.forms["applyForLeave"]["authorizedPersonId"].value;

    const d1 = new Date(frDate);
    const d2 = new Date(toDate);
    const today = new Date();

    //Validate From Date
    if(frDate=="")
    {
        document.getElementById("lblFromDate").innerHTML = "From Date Required";
        val = 1;
        console.log("From Date Required");
    }
    else
    {
        if(d1<today)
        {
            document.getElementById("lblFromDate").innerHTML = "Cannot Apply Leaves for Past Dates";
            val = 1;
            console.log("Cannot Apply Leaves for Past Dates");
        }
    }
    //Validate To Date
    if(toDate=="")
    {
        document.getElementById("lblToDate").innerHTML = "To Date Required";
        val = 1;
        console.log("To Date Required");
    }
    else
    {
        if(d2<d1)
        {
            document.getElementById("lblFromDate").innerHTML = "From Date need to be less than To Date";
            document.getElementById("lblToDate").innerHTML = "To Date need to be greater than From Date";
            val = 1;
            console.log("To Date < From Date");
        }
    }

    //Validate Reason
    if(reason=="")
    {
        document.getElementById("lblReason").innerHTML = "Reason Required";
        val = 1;
        console.log("Reason Required");
    }

    //Validate Leave Type
    if(type=="")
    {
        document.getElementById("lblLeaveType").innerHTML = "Leave Type Required";
        val = 1;
        console.log("Leave Type Required");
    }

    //Validate Leave Type
    if(person=="")
    {
        document.getElementById("lblAuthorizedPerson").innerHTML = "Authorised Person Required, Select Authorised person from the below list";
        val = 1;
        console.log("Authorised Person Required");
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