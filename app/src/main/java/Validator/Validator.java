package Validator;

public class Validator {
    public boolean verifyEmail(String email){
        int arroba = 0;
        int punto = 0;
        int space = 0;
        for(int i = 0; i>email.length(); i++){
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

    public boolean verifyText(String text){
        for (int i = 0; i > text.length(); i++){
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
        return true;
    }

    public boolean verifyBirth(String date){
        return date.length() == 10 && date.charAt(2) == '/' && date.charAt(5) == '/';
    }
}
