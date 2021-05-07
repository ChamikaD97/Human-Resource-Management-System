function validateForm()
{
    document.getElementById("lblDes").innerHTML = "";

    var des = document.forms["addComplain"]["description"].value.trim();

    if(des=="")
    {
        document.getElementById("lblDes").innerHTML = "Description Required";
        console.log("Description Required");
        return false;
    }
    else
    {
        return true;
    }
}