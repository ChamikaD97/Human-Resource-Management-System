let i=1;
let j=0;
function addImage()
{
    var filein = document.createElement("input");
    filein.type = "file";
    filein.name = "imgFile"+i;
    filein.id = "imgFile"+i;
    filein.className = "fileChoose";
    filein.onchange = function(){fileCount(this.id)};
    filein.accept = "image/*";

    var filelbl = document.createElement("label");
    filelbl.id = "lblFile"+i;
    filelbl.htmlFor = "imgFile"+i;
    filelbl.className = "limg";
    filelbl.onclick = function(){remImg(this.htmlFor,this)};
    filelbl.innerHTML = "-";

    document.getElementById("m").appendChild(filelbl);
    document.getElementById("m").appendChild(filein);
    var elem = document.getElementById("imgFile"+i);

    if(elem && document.createEvent)
    {
        var evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, false);
        elem.dispatchEvent(evt);
    }
    document.getElementById("tot").value=i;
    i++;

}

function getSize(fid)
{
    var fi = document.getElementById(fid);
    var isfile = fi.files.item(0);

    if(isfile==null)
    {
        return false;
    }
    else
    {
        return true;
    }
}

function fileCount(fid)
{
    if(getSize(fid))
    {
        j++;
    }
}

function remImg(eId,obj2)
{
    var obj1 = document.getElementById(eId);
    obj2.remove();

    if(getSize(eId))
    {
        j--;
    }
    obj1.remove();
}

function validateForm()
{
    var des = document.forms["addPost"]["description"].value.trim();
    var imgCount = j;

    if(des=="" && imgCount<1)
    {
        alert("Create a Post to Upload");
        return false;
    }
    else
    {
        return true;
    }
}
