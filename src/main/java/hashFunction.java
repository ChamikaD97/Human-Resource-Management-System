public class hashFunction {
        String toHash(String password, String empId){

        String hash = null;
        int id = Integer.parseInt(empId);
        int length = password.length();

        char[] passs = new char[length];
        int[] asc = new int[length];
        int sum =0;
        for (int i = 0; i < length; i++) {
            passs[i] = password.charAt(i);
            asc[i] = (int) passs[i];

            if (asc[i]< 47){
                asc[i] =99;
            }
            else if(asc[i] <=57){
                asc[i]= asc[i]-48;
            }

            else if(asc[i] <=91){
                asc[i]= asc[i]-64;
            }

            else if(asc[i] <=122){
                asc[i]= asc[i]-96;
            }else{
                asc[i] =99;
            }

            sum =sum + asc[i];

        }
        int r=id % 1000;
        int val = (id * r * sum * id * sum);
        if(val < 0){
            val = val *(-1);
        }
        hash = String.valueOf(val);

        return hash;
    }
}
