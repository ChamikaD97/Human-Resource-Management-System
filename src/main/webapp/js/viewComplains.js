
var table = document.getElementById('result');

for (var i = 1; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {

        document.getElementById("comId").value = this.cells[0].innerHTML;
        document.getElementById("date").value = this.cells[1].innerHTML;
        document.getElementById("description").value = this.cells[2].innerHTML;
    };
}
