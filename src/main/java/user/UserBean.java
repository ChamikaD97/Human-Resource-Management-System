package user;

public class UserBean
{
    private String empId,fName,lName,NIC,dob,address,email,current_password,password,confirmPassword,contact;
    private int fingerprint,empAdd,empDel,postAdd,postDel,postView,chatSys,applyLeave,decisionLeave,salaryManage,customizeData,editPersonalDetails,giveComSug,viewComSug,viewMyAttend,viewAllAttend,viewMyLeaves,viewAllLeaves,viewMySalary,viewAllSalary,genReport;;
    private float basicSal,otRate;
    private String firstName;
    private int totPayedLeaves, remPayedLeaves ,tackenPayedLeaves;
    private int totNoPayedLeaves, remNoPayedLeaves,tackenNoPayLeaves;
    private int totMedicalLeaves, remMedicalLeaves ,tackenMedicalLeaves;
    String test;

    public String getName()
    {
        return firstName;
    }
    public void setName(String firstName) {
        this.firstName = firstName;
    }
    public String getEmpId()
    {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFName()
    {
        return fName;
    }
    public void setFName(String fName)
    {
        this.fName = fName;
    }
    public String getLName()
    {
        return lName;
    }
    public void setLName(String lName)
    {
        this.lName = lName;
    }
    public String getNIC()
    {
        return NIC;
    }
    public void setNIC(String NIC)
    {
        this.NIC = NIC;
    }

    public String getDOB()
    {
        return dob;
    }
    public void setDOB(String dob)
    {
        this.dob = dob;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getConfirmPassword()
    {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }
    public String getContact()
    {
        return contact;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }


    public int gettotPayedLeaves()
    {
        return totPayedLeaves;
    }
    public void settotPayedLeaves(int totPayedLeaves)
    {
        this.totPayedLeaves = totPayedLeaves;
    }

    public int gettotNoPayedLeaves()
    {
        return totNoPayedLeaves;
    }
    public void settotNoPayedLeaves(int totNoPayedLeaves)
    {
        this.totNoPayedLeaves = totNoPayedLeaves;
    }

    public int gettotMedicalLeaves()
    {
        return totMedicalLeaves;
    }
    public void settotMedicalLeaves(int totMedicalLeaves)
    {
        this.totMedicalLeaves = totMedicalLeaves;
    }

    public int getremPayedLeaves()
    {
        return remPayedLeaves;
    }
    public void setremPayedLeaves(int remPayedLeaves)
    {
        this.remPayedLeaves = remPayedLeaves;
    }

    public int getremNoPayedLeaves()
    {
        return remNoPayedLeaves;
    }
    public void setremNoPayedLeaves(int remNoPayedLeaves)
    {
        this.remNoPayedLeaves = remNoPayedLeaves;
    }


    public int getremMedicalLeaves()
    {
        return remMedicalLeaves;
    }
    public void setremMedicalLeaves(int remMedicalLeaves)
    {
        this.remMedicalLeaves = remMedicalLeaves;
    }

    public int gettackenPayedLeaves() {
        return tackenPayedLeaves;
    }
    public void settackenPayedLeaves(int tackenPayedLeaves)
    {
        this.tackenPayedLeaves = tackenPayedLeaves;
    }

    public int gettackenNoPayLeaves()
    {
        return tackenNoPayLeaves;
    }
    public void settackenNoPayLeaves(int tackenNoPayLeaves)
    {
        this.tackenNoPayLeaves = tackenNoPayLeaves;
    }

    public int gettackenMedicalLeaves() {
        return tackenMedicalLeaves;
    }
    public void settackenMedicalLeaves(int tackenMedicalLeaves)
    {
        this.tackenMedicalLeaves = tackenMedicalLeaves;
    }

    public float getBasicSal()
    {
        return basicSal;
    }
    public void setBasicSal(float basicSal)
    {
        this.basicSal = basicSal;
    }
    public float getOtRate()
    {
        return otRate;
    }
    public void setOtRate(float otRate)
    {
        this.otRate = otRate;
    }


    public int getEmpAdd()
    {
        return empAdd;
    }
    public void setEmpAdd(int empAdd)
    {
        this.empAdd =  empAdd;
    }
    public int getEmpDel()
    {
        return empDel;
    }
    public void setEmpDel(int empDel)
    {
        this.empDel =  empDel;
    }
    public int getPostAdd()
    {
        return postAdd;
    }
    public void setPostAdd(int postAdd)
    {
        this.postAdd =  postAdd;
    }
    public int getPostDel()
    {
        return postDel;
    }
    public void setPostDel(int postDel)
    {
        this.postDel =  postDel;
    }
    public int getPostView()
    {
        return postView;
    }
    public void setPostView(int postView)
    {
        this.postView =  postView;
    }
    public int getChatSys()
    {
        return chatSys;
    }
    public void setChatSys(int chatSys)
    {
        this.chatSys =  chatSys;
    }
    public int getApplyLeave()
    {
        return applyLeave;
    }
    public void setApplyLeave(int applyLeave)
    {
        this.applyLeave =  applyLeave;
    }
    public int getDecisionLeave()
    {
        return decisionLeave;
    }
    public void setDecisionLeave(int decisionLeave)
    {
        this.decisionLeave =  decisionLeave;
    }
    public int getSalaryManage()
    {
        return salaryManage;
    }
    public void setSalaryManage(int salaryManage)
    {
        this.salaryManage =  salaryManage;
    }
    public int getCustomizeData()
    {
        return customizeData;
    }
    public void setCustomizeData(int customizeData)
    {
        this.customizeData =  customizeData;
    }
    public int getEditPersonalDetails()
    {
        return editPersonalDetails;
    }
    public void setEditPersonalDetails(int editPersonalDetails)
    {
        this.editPersonalDetails =  editPersonalDetails;
    }
    public int getGiveComSug()
    {
        return giveComSug;
    }
    public void setGiveComSug(int giveComSug)
    {
        this.giveComSug =  giveComSug;
    }
    public int getViewComSug()
    {
        return viewComSug;
    }
    public void setViewComSug(int viewComSug)
    {
        this.viewComSug =  viewComSug;
    }
    public int getViewMyAttend()
    {
        return viewMyAttend;
    }
    public void setViewMyAttend(int viewMyAttend)
    {
        this.viewMyAttend =  viewMyAttend;
    }
    public int getViewAllAttend()
    {
        return viewAllAttend;
    }
    public void setViewAllAttend(int viewAllAttend)
    {
        this.viewAllAttend =  viewAllAttend;
    }
    public int getViewMyLeaves()
    {
        return viewMyLeaves;
    }
    public void setViewMyLeaves(int viewMyLeaves)
    {
        this.viewMyLeaves =  viewMyLeaves;
    }
    public int getViewAllLeaves()
    {
        return viewAllLeaves;
    }
    public void setViewAllLeaves(int viewAllLeaves)
    {
        this.viewAllLeaves =  viewAllLeaves;
    }
    public int getViewMySalary()
    {
        return viewMySalary;
    }
    public void setViewMySalary(int viewMySalary)
    {
        this.viewMySalary =  viewMySalary;
    }
    public int getViewAllSalary()
    {
        return viewAllSalary;
    }
    public void setViewAllSalary(int viewAllSalary)
    {
        this.viewAllSalary =  viewAllSalary;
    }
    public int getGenReport()
    {
        return genReport;
    }
    public void setGenReport(int genReport)
    {
        this.genReport =  genReport;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    public void setCurrentpassword(String current_password) {
        this.current_password = current_password;
    }
    public String getCurrentpassword() {
        return current_password;
    }
}
