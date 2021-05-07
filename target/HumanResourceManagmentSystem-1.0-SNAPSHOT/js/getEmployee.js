var table = document.getElementById('tableResult');

for (var i = 1; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {
        document.getElementById("empId").value = this.cells[0].innerHTML;

    };
}

function validateForm1()
{
    document.getElementById("lblEmpId").innerHTML = "";

    var empId = document.forms["searchEmp"]["empId"].value;

    if(empId=="")
    {
        document.getElementById("lblEmpId").innerHTML = "Select Employee to Update";
        console.log("Select Employee to Update")
        return false;
    }
    else
    {
        return true;
    }
}

function validateForm2()
{
    document.getElementById("lblEmpId").innerHTML = "";

    var empId = document.forms["delEmp"]["empId"].value;

    if(empId=="")
    {
        document.getElementById("lblEmpId").innerHTML = "Select Employee to Delete";
        console.log("Select Employee to Delete")
        return false;
    }
    else
    {
        return true;
    }
}