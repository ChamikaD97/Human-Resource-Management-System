$(document).ready(getSelectedCB);
let i=0;
CountSelectedCB = [];
function getSelectedCB()
{
    $("input:checkbox").change(function()
    {

        CountSelectedCB.length = 0;
        $("input:checkbox").each(function()
        {
            if ($(this).is(":checked"))
            {
                CountSelectedCB.push($(this).attr("value"));
            }
        });
        if(CountSelectedCB.length>0)
        {
            document.getElementById('pSelect').innerHTML = CountSelectedCB.length+' Posts Selected';
            document.getElementById('pSelect').className = "help";
        }
        else
        {
            document.getElementById('pSelect').innerHTML = 'Select Post to Delete';
            document.getElementById('pSelect').className = "count";
        }
        $('input[name=pId]').val(CountSelectedCB);
    });
}

function executeForm()
{
    i=1;
    console.log(i);
    document.getElementById('delMyPost').action = "deleteAnyPost.jsp";
    document.getElementById('delMyPost').submit();
}

function validateForm()
{
    if(CountSelectedCB.length<1)
    {
        alert("Select Post to Delete.");
        return false;
    }
    else
    {
        return true;
    }
}