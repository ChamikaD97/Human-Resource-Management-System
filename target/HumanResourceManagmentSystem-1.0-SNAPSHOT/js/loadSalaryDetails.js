var table = document.getElementById('tresult');

for (var i = 1; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {

        document.getElementById("salaryId").value       = this.cells[0].innerHTML;
        document.getElementById("empId").value          = this.cells[1].innerHTML;
        document.getElementById("officeDays").value     = this.cells[2].innerHTML;
        document.getElementById("noPays").value         = this.cells[3].innerHTML;
        document.getElementById("absences").value       = this.cells[4].innerHTML;
        document.getElementById("basicSalary").value    = this.cells[5].innerHTML;
        document.getElementById("dailySalary").value    = this.cells[6].innerHTML;
        document.getElementById("otHours").value        = this.cells[7].innerHTML;
        document.getElementById("otRate").value         = this.cells[8].innerHTML;
        document.getElementById("total").value          = this.cells[9].innerHTML;

    }}