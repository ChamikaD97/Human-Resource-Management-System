window.onload = function()
{
    $("#chatMsg").scrollTop($("#chatMsg")[0].scrollHeight);
}

function getFileName() {
    var fullPath = document.getElementById('fbtn').value;
    if (fullPath) {
        var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
        var filename = fullPath.substring(startIndex);
        if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0)
        {
            filename = filename.substring(1);
            document.getElementById("fileName").value = filename;
        }
    }
}

function downloadFile(mId)
{
    document.getElementById("msgFileId").value = mId;
    document.getElementById('chatSys').action = "downloadfile";
    document.getElementById('chatSys').method = "GET"
    document.getElementById('chatSys').submit();
}

function deleteMsg(mId)
{
    if (confirm("Delete this Message?")) {
        document.getElementById("msgFileId").value = mId;
        document.getElementById('chatSys').action = "deletemessage";
        document.getElementById('chatSys').method = "POST"
        document.getElementById('chatSys').submit();
    }
    else
    {
        document.getElementById("fId").value = "1";
        document.getElementById('chatSys').action = "chatmessages";
        document.getElementById('chatSys').method = "POST"
        document.getElementById('chatSys').submit();
    }
}

function sendMsg()
{
    document.getElementById('chatSys').action = "chatmessages";
    document.getElementById('chatSys').method = "POST"
    document.getElementById('chatSys').submit();
}

function searchName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("empName");
    filter = input.value.toUpperCase();
    table = document.getElementById("tableNames");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

/*function validateChat()
{
    var val=0;
    var msg = document.forms["chatSys"]["msg"].value;
    var file = document.forms["chatSys"]["fileName"].value;

    if(msg=="" && file=="")
    {
        alert("No Message to Send");
        val=1;
    }

    if (val!=0)
    {
        return false;
    }
    else
    {
        return true;
    }
}*/

var table = document.getElementById('tableNames');

for (var i = 1; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {

        document.getElementById("gName").value = this.cells[1].innerHTML;
        document.getElementById("gId").value = this.cells[0].innerHTML;
        document.getElementById("fId").value = "1";
        document.getElementById('chatSys').action = "chatmessages";
        document.getElementById('chatSys').method = "POST"
        document.getElementById('chatSys').submit();
    };
}