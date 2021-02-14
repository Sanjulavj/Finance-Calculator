package sample;

//________Project Finance Calculator

//_______mathematical functions for Finance Calculator

public class Controller {
    //calculating future value for finance calculator
    public static double finanFV(double SP,double IR,double NP){
        double decimalIR = IR/(100);           //getting real value from percentage of interest rate for a month
        double interestSP = (Math.pow(1+decimalIR,NP)*SP);  //calculating start principle + start principal interest
        return Math.round((interestSP)*100.0)/100.0;
    }

    //calculating start principle for finance calculator
    public static double finanSP(double FV,double IR,double NP){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double SP = FV/(Math.pow(1+decimalIR,NP));    //calculating start principal by using start principal formula
        return Math.round((SP)*100.0)/100.0;
    }

    //calculating interest rate for finance calculator
    public static double finanIR(double FV,double SP,double NP){
        double power = 1/(12*NP);   //calculating the power of the formula
        double IR = Math.pow((FV/SP),power);  //calculating Interest rate
        double annuityIR = (IR-1)*12*100;      //interest rate for annuity period
        return Math.round((annuityIR)*100.0)/100.0;
    }

    //calculating number of payments for finance calculator
    public static double finanNP(double FV,double SP,double IR){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double NP =Math.log((decimalIR*FV)/(decimalIR*SP))/Math.log(1+decimalIR);      //calculating the number of payments
        return Math.round((NP)*100.0)/100.0;

    }

    //calculating future value for finance calculator with annuity payments(end)
    public static double finanFV2(double SP,double IR,double NP, double PMT){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double annuityPayFV = (PMT/decimalIR)*(Math.pow(1+decimalIR,NP)-1);    //using annuity formula to calculate total annuity payment with interest
        double interestSP = (Math.pow(1+decimalIR,NP)*SP);   //calculating future value only for starting principal
        double FV = interestSP + annuityPayFV ;     //Future value= interest for start principal +start principal + annuity total
        return Math.round((FV)*100.0)/100.0;
    }

    //calculating start principal for finance calculator with annuity payments(end)
    public static double finanSP2(double FV,double IR,double NP, double PMT){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double annuityPayFV = (PMT/decimalIR)*(Math.pow(1+decimalIR,NP)-1);    //using annuity formula to calculate total annuity payment with interest
        double totForSP = FV- annuityPayFV;     //calculating total amount only for start principal
        double SP = totForSP/(Math.pow(1+decimalIR,NP));    //calculating start principal by using start principal formula
        return Math.round((SP)*100.0)/100.0;
    }



    //calculating number of periods for finance calculator with annuity payments(end)
    public static double finanNP2(double FV,double SP,double IR, double PMT){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double NP =Math.log((decimalIR*FV+PMT)/(decimalIR*SP+PMT))/Math.log(1+decimalIR);      //calculating the number of payments
        return Math.round((NP)*100.0)/100.0;

    }

    //calculating monthly payment for finance calculator with annuity payments(end)
    public static double finanPMT2(double FV,double SP,double IR,double NP){
        double decimalIR = IR/100;      //getting real value from percentage of interest rate
        double power = Math.pow(1+decimalIR,NP);   //math power part of the formula
        double PMT = decimalIR * (FV-(SP* power)) / (power - 1);   //calculating payment for payment for the month
        return Math.round((PMT)*100.0)/100.0;

    }


    //calculating future value for finance calculator with annuity payments(beginning)
    public static double finanFV3(double SP,double IR,double NP, double PMT){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double annuityPayFV = (PMT*(1+decimalIR)/decimalIR)*(Math.pow(1+decimalIR,NP)-1);    //using annuity formula to calculate total annuity payment with interest
        double interestSP = (Math.pow(1+decimalIR,NP)*SP);   //calculating future value only for starting principal
        double FV = interestSP + annuityPayFV ;     //Future value= interest for start principal +start principal + annuity total
        return Math.round((FV)*100.0)/100.0;
    }

    //calculating start principal for finance calculator with annuity payments(beginning)
    public static double finanSP3(double FV,double IR,double NP, double PMT){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double annuityPayFV = (PMT*(1+decimalIR)/decimalIR)*(Math.pow(1+decimalIR,NP)-1);    //using annuity formula to calculate total annuity payment with interest
        double totForSP = FV- annuityPayFV;     //calculating total amount only for start principal
        double SP = totForSP/(Math.pow(1+decimalIR,NP));    //calculating start principal by using start principal formula
        return Math.round((SP)*100.0)/100.0;
    }

    //calculating number of periods for finance calculator with annuity payments(beginning)
    public static double finanNP3(double FV,double SP,double IR, double PMT){
        double decimalIR = IR/100;          //getting real value from percentage of interest rate
        double NP =Math.log((decimalIR*FV+PMT)/(decimalIR*SP+PMT))/Math.log(1+decimalIR);      //calculating the number of payments
        return Math.round((NP)*100.0)/100.0;

    }

    //calculating monthly payment for finance calculator with annuity payments(beginning)
    public static double finanPMT3(double FV,double SP,double IR,double NP){
        double decimalIR = IR/100;      //getting real value from percentage of interest rate
        double power = Math.pow(1+decimalIR,NP);   //math power part of the formula
        //double power2 = Math.pow(1+decimalIR,(NP));
        double PMT = decimalIR* (FV-(SP* power)) / (power - 1);   //calculating payment for payment for the month
        return Math.round((PMT)*100.0)/100.0;
    }

    //calculating present value for both finance calculations
    public static double finanPresentValue(double FV,double IR,double NP){
        double decimalIR = IR/100;      //getting real value from percentage of interest rate
        double presentValue= FV/Math.pow((1+decimalIR),NP);
        return Math.round((presentValue)*100.0)/100.0;
    }


    //calculating total loan price for loan calculator
    public static double loanAP(double MP,double LT,double IR){
        double decimalIR = IR/(12*100);          //getting real value from percentage of interest rate for a month
        double AP = (MP*(1-1/(Math.pow(1+decimalIR,LT))))/decimalIR;       //calculating total loan price
        return Math.round((AP)*100.0)/100.0;
    }

    //calculating monthly payment for total loan price for loan calculator
    public static double loanMP(double AP,double LT,double IR,double DP){
        double decimalIR = IR/(12*100);         //getting real value from percentage of interest rate for a month
        double APDividend = ((AP-DP)*decimalIR)*(Math.pow(1+decimalIR,LT));      //dividend of, loan monthly payment formula
        double APDivisor = ((Math.pow(1+decimalIR,LT))-1);          //divisor of, loan monthly payment formula
        double AnnuityPayment= APDividend/APDivisor;       //Monthly payment for loan amount
        return Math.round((AnnuityPayment)*100.0)/100.0;
    }

    //calculating total loan price + downPayment for loan calculator
    public static double loanAPPlusDP(double MP,double LT,double IR,double DP){
        double APPlusDP = loanAP(MP,LT,IR) + DP;      //calculating full loan amount
        return Math.round((APPlusDP)*100.0)/100.0;
    }


    //calculating total loan amount for monthly payment (loan calculator)
    public static double loanMPTotLoan(double AP,double DP){
        double totalLoanAmount = AP-DP;
        return Math.round((totalLoanAmount*100.0)/100.0);
    }

    //calculating monthly payment for mortgage calculator
    public static double mortgageMP(double PP,double DP,double LT,double IR){
        double decimalIR = IR/(12*100);         //getting real value from percentage of interest rate for a month
        double MPDividend = ((PP-DP)*decimalIR)*(Math.pow(1+decimalIR,(12*LT)));      //dividend of, loan monthly payment formula
        double MPDivisor = (Math.pow(1+decimalIR,(12*LT))-1);          //divisor of, loan monthly payment formula
        double MP= MPDividend/MPDivisor;       //Monthly payment for loan amount
        return Math.round((MP)*100.0)/100.0;
    }






}


