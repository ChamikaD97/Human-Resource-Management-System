var table = document.getElementById('tresult');

for (var i = 1; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {

        document.getElementById("leaveId").value = this.cells[0].innerHTML;

        document.getElementById("name").value = this.cells[1].innerHTML;

        document.getElementById("from").value = this.cells[2].innerHTML;

        document.getElementById("to").value = this.cells[3].innerHTML;
        document.getElementById("reason").value = this.cells[4].innerHTML;
        document.getElementById("type").value = this.cells[5].innerHTML;

        document.getElementById("RemainingPayed").value = this.cells[6].innerHTML;
        document.getElementById("RemainingNoPayed").value = this.cells[7].innerHTML;
        document.getElementById("RemainingMedical").value = this.cells[8].innerHTML;

    }}

function validateForm()
{
    var leaveId = document.forms["leaveApproveReject"]["leaveId"].value;

    if(leaveId=="")
    {
        alert("Select an Applied Leave from Below List");
        console.log("Empty Leave");
        return false;
    }
    else
    {
        return true;
    }
}