package Validator;

public class Validator {
    public boolean verifyEmail(String email){
        int arroba = 0;
        int punto = 0;
        int space = 0;
        if(email.length() == 0)
            return false;
        for(int i = 0; i<email.length(); i++){
            char mander = email.charAt(i);
            if(mander == '@'){
                arroba ++;
            }
            if (mander == '.'){
                punto ++;
            }
            if (mander == ' '){
                space ++;
            }
        }
        if (arroba == 1 && punto >=1 && space == 0){
            return true;
        }
        return false;
    }

    public boolean verifyNumber(String number){
        try{
            int n = Integer.valueOf(number);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean verifyPhone(String phone){
        if(verifyNumber(phone)){
            if(phone.length() == 8 || phone.length() == 12 || phone.length() == 10)
                return true;
        }
        return false;
    }

    public boolean verifyText(String text){
        for (int i = 0; i < text.length(); i++){
            char mander = text.charAt(i);
            if(     mander == '@' ||
                    mander == '#' ||
                    mander == '$' ||
                    mander == ',' ||
                    mander == '%' ||
                    mander == '^' ||
                    mander == '!' ||
                    mander == '&' ||
                    mander == '8' ||
                    mander == '(' ||
                    mander == ')' ||
                    mander == '+' ||
                    mander == '-' ||
                    mander == '[' ||
                    mander == ']' ||
                    mander == '{' ||
                    mander == '}'
            ){
                return false;
            }
        }
        if(text.length() == 0)
            return false;
        return true;
    }

    public boolean verifyBirth(String date){
        if(date.length()== 0)
            return true;
        Boolean valid = false;
        int day;
        int month;
        int year;
        try{
            if(date.length() == 10 && date.charAt(2) == '/' && date.charAt(5) == '/'
                    && verifyNumber(date.substring(0,2)) && verifyNumber(date.substring(3,5)) && verifyNumber(date.substring(6,10)) ){
                day = Integer.parseInt(date.substring(0,2));
                month = Integer.parseInt(date.substring(3,5));
                year = Integer.parseInt(date.substring(6,10));
                if(!(day <=31 && month<=12 && year<=2022)){
                    return false;
                }
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

}
