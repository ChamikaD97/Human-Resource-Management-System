function validateEmail(email)
{
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validateNIC(nic)
{

    var cnic_no_regex = new RegExp('^[0-9+]{9}[vV|xX]$');
    var new_cnic_no_regex = new RegExp('^[0-9+]{12}$');
    if ((nic.length == 10 && cnic_no_regex.test(nic))||(nic.length == 12 && new_cnic_no_regex.test(nic)))
    {
        return true;
    }
    else
    {
        return false;
    }
}

function validatePhone(pno)
{
    var local = new RegExp('^[0][7][0-9+]{8}$');
    var international = new RegExp('^[+][9][4][7][0-9+]{8}$');

    if ((pno.length == 10 && local.test(pno))||(pno.length == 12 && international.test(pno)))
    {
        return true;
    }
    else
    {
        return false;
    }
}

function validateFloatNumbers(num)
{
    var regexFloat = /^[+]?\d+(\.\d+)?$/
    return regexFloat.test(num);
}

function validateIntNumbers(num)
{
    var regexInt = /^[+]?\d+$/
    return regexInt.test(num);
}

function validateForm1()
{
    document.getElementById("lblFName").innerHTML = "";
    document.getElementById("lblLName").innerHTML = "";
    document.getElementById("lblNic").innerHTML = "";
    document.getElementById("lblAddress").innerHTML = "";
    document.getElementById("lblEmail").innerHTML = "";
    document.getElementById("lblDob").innerHTML = "";
    document.getElementById("lblPhone").innerHTML = "";
    document.getElementById("lblPassword").innerHTML = "";
    document.getElementById("lblConPass").innerHTML = "";
    document.getElementById("lblBSalary").innerHTML = "";
    document.getElementById("lblOtRate").innerHTML = "";
    document.getElementById("lblLeavesPayed").innerHTML = "";
    document.getElementById("lblLeavesNoPay").innerHTML = "";
    document.getElementById("lblLeavesMedical").innerHTML = "";

    var val = 0;

    var fname = document.forms["editEmployee"]["first_name"].value.trim();
    var lname = document.forms["editEmployee"]["last_name"].value.trim();
    var nic = document.forms["editEmployee"]["nic"].value.trim();
    var address = document.forms["editEmployee"]["address"].value.trim();
    const email = $("#email").val();
    var dob = document.forms["editEmployee"]["dob"].value.trim();
    var phone = document.forms["editEmployee"]["phone"].value.trim();
    var password = document.forms["editEmployee"]["password"].value.trim();
    var confirm_password = document.forms["editEmployee"]["confirm_password"].value.trim();
    var basic = document.forms["editEmployee"]["bSalary"].value.trim();
    var ot = document.forms["editEmployee"]["otRate"].value.trim();
    var payLeaves = document.forms["editEmployee"]["leavesPayed"].value.trim();
    var noPayLeaves = document.forms["editEmployee"]["leavesNoPay"].value.trim();
    var medicalLeaves = document.forms["editEmployee"]["leavesMedical"].value.trim();

    //Validate First Name
    if(fname=="")
    {
        document.getElementById("lblFName").innerHTML = "First Name Required";
        val = 1;
        console.log("First Name Required");
    }

    //Validate Last Name
    if(lname=="")
    {
        document.getElementById("lblLName").innerHTML = "Last Name Required";
        val = 1;
        console.log("Last Name Required");
    }

    //Validate NIC
    if(nic=="")
    {
        document.getElementById("lblNic").innerHTML = "NIC Required";
        val = 1;
        console.log("NIC Required");
    }
    else
    {
        if (!validateNIC(nic))
        {
            document.getElementById("lblNic").innerHTML = "Invalid NIC";
            val = 1;
            console.log("Invalid NIC");
        }
    }

    //Validate Address
    if(address=="")
    {
        document.getElementById("lblAddress").innerHTML = "Address Required";
        val = 1;
        console.log("Address Required");
    }

    //Validate Email
    if(email=="")
    {
        document.getElementById("lblEmail").innerHTML = "Email Required";
        val = 1;
        console.log("Email Required");
    }
    else
    {
        if (!validateEmail(email))
        {
            document.getElementById("lblEmail").innerHTML = "Invalid Email";
            val = 1;
            console.log("Invalid Email");
        }
    }

    //Validate DOB
    if(dob=="")
    {
        document.getElementById("lblDob").innerHTML = "Date of Birth Required";
        val = 1;
        console.log("DOB Required");
    }

    //Validate Phone
    if(phone=="")
    {
        document.getElementById("lblPhone").innerHTML = "Phone Number Required";
        val = 1;
        console.log("Phone Number Required");
    }
    else
    {
        if (!validatePhone(phone))
        {
            document.getElementById("lblPhone").innerHTML = "Invalid Phone Number";
            val = 1;
            console.log("Invalid Phone Number");
        }
    }

    //Validate Password
    if(password=="")
    {
        document.getElementById("lblPassword").innerHTML = "Password Required";
        val = 1;
        console.log("Password Required");
    }
    else
    {
        if(password.length < 8)
        {
            document.getElementById("lblPassword").innerHTML = "Please Input Strong Password (Min-8 Characters)";
            val = 1;
            console.log("Strong Password Required");
        }
        else
        {
            if(password != confirm_password)
            {
                document.getElementById("lblConPass").innerHTML = "Password Mismatch";
                val = 1;
                console.log("Password Mismatch");
            }
        }
    }

    //Validate Basic Salary
    if(basic=="")
    {
        document.getElementById("lblBSalary").innerHTML = "Basic Salary Required";
        val = 1;
        console.log("Basic Salary Required");
    }
    else
    {
        if(!validateFloatNumbers(basic))
        {
            document.getElementById("lblBSalary").innerHTML = "Invalid Value";
            val = 1;
            console.log("Invalid Value");
        }
    }

    //Validate OT Rate
    if(ot=="")
    {
        document.getElementById("lblOtRate").innerHTML = "OT Rate Required";
        val = 1;
        console.log("OT Rate Required");
    }
    else
    {
        if(!validateFloatNumbers(ot))
        {
            document.getElementById("lblOtRate").innerHTML = "Invalid Value";
            val = 1;
            console.log("Invalid Value");
        }
    }

    //Validate Payed Leaves
    if(payLeaves=="")
    {
        document.getElementById("lblLeavesPayed").innerHTML = "Number of Payed Leaves Required";
        val = 1;
        console.log("Number of Payed Leaves Required");
    }
    else
    {
        if(!validateIntNumbers(payLeaves))
        {
            document.getElementById("lblLeavesPayed").innerHTML = "Invalid Value";
            val = 1;
            console.log("Invalid Value");
        }
    }

    //Validate NoPay Leaves
    if(noPayLeaves=="")
    {
        document.getElementById("lblLeavesNoPay").innerHTML = "Number of NoPay Leaves Required";
        val = 1;
        console.log("Number of NoPay Leaves Required");
    }
    else
    {
        if(!validateIntNumbers(noPayLeaves))
        {
            document.getElementById("lblLeavesNoPay").innerHTML = "Invalid Value";
            val = 1;
            console.log("Invalid Value");
        }
    }

    //Validate Medical Leaves
    if(medicalLeaves=="")
    {
        document.getElementById("lblLeavesMedical").innerHTML = "Number of Medical Leaves Required";
        val = 1;
        console.log("Number of Medical Leaves Required");
    }
    else
    {
        if(!validateIntNumbers(medicalLeaves))
        {
            document.getElementById("lblLeavesMedical").innerHTML = "Invalid Value";
            val = 1;
            console.log("Invalid Value");
        }
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

function validateForm2()
{
    document.getElementById("lblFName").innerHTML = "";
    document.getElementById("lblLName").innerHTML = "";
    document.getElementById("lblNic").innerHTML = "";
    document.getElementById("lblAddress").innerHTML = "";
    document.getElementById("lblEmail").innerHTML = "";
    document.getElementById("lblDob").innerHTML = "";
    document.getElementById("lblPhone").innerHTML = "";


    var val = 0;

    var fname = document.forms["editPersonal"]["first_name"].value;
    var lname = document.forms["editPersonal"]["last_name"].value;
    var nic = document.forms["editPersonal"]["nic"].value;
    var address = document.forms["editPersonal"]["address"].value;
    const email = $("#email").val();
    var dob = document.forms["editPersonal"]["dob"].value;
    var phone = document.forms["editPersonal"]["phone"].value;


    //Validate First Name
    if(fname=="")
    {
        document.getElementById("lblFName").innerHTML = "First Name Required";
        val = 1;
        console.log("First Name Required");
    }

    //Validate Last Name
    if(lname=="")
    {
        document.getElementById("lblLName").innerHTML = "Last Name Required";
        val = 1;
        console.log("Last Name Required");
    }

    //Validate NIC
    if(nic=="")
    {
        document.getElementById("lblNic").innerHTML = "NIC Required";
        val = 1;
        console.log("NIC Required");
    }
    else
    {
        if (!validateNIC(nic))
        {
            document.getElementById("lblNic").innerHTML = "Invalid NIC";
            val = 1;
            console.log("Invalid NIC");
        }
    }

    //Validate Address
    if(address=="")
    {
        document.getElementById("lblAddress").innerHTML = "Address Required";
        val = 1;
        console.log("Address Required");
    }

    //Validate Email
    if(email=="")
    {
        document.getElementById("lblEmail").innerHTML = "Email Required";
        val = 1;
        console.log("Email Required");
    }
    else
    {
        if (!validateEmail(email))
        {
            document.getElementById("lblEmail").innerHTML = "Invalid Email";
            val = 1;
            console.log("Invalid Email");
        }
    }

    //Validate DOB
    if(dob=="")
    {
        document.getElementById("lblDob").innerHTML = "Date of Birth Required";
        val = 1;
        console.log("DOB Required");
    }

    //Validate Phone
    if(phone=="")
    {
        document.getElementById("lblPhone").innerHTML = "Phone Number Required";
        val = 1;
        console.log("Phone Number Required");
    }
    else
    {
        if (!validatePhone(phone))
        {
            document.getElementById("lblPhone").innerHTML = "Invalid Phone Number";
            val = 1;
            console.log("Invalid Phone Number");
        }
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

function validateForm3()
{
    document.getElementById("lblCurPass").innerHTML = "";
    document.getElementById("lblPassword").innerHTML = "";
    document.getElementById("lblConPass").innerHTML = "";

    var current_password = document.forms["changePassword"]["current_password"].value;
    var password = document.forms["changePassword"]["password"].value;
    var confirm_password = document.forms["changePassword"]["confirm_password"].value;

    //Validate Current Password
    if(current_password=="")
    {
        document.getElementById("lblCurPass").innerHTML = "Current Password Required";
        val = 1;
        console.log("Current Password Required");
    }

    //Validate New Password
    if(password=="")
    {
        document.getElementById("lblPassword").innerHTML = "Password Required";
        val = 1;
        console.log("Password Required");
    }
    else
    {
        if(password.length < 8)
        {
            document.getElementById("lblPassword").innerHTML = "Please Input Strong Password (Min-8 Characters)";
            val = 1;
            console.log("Strong Password Required");
        }
        else
        {
            if(password != confirm_password)
            {
                document.getElementById("lblConPass").innerHTML = "Password Mismatch";
                val = 1;
                console.log("Password Mismatch");
            }
        }
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

