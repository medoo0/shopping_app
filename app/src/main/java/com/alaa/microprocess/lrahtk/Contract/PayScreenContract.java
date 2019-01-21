package com.alaa.microprocess.lrahtk.Contract;

public interface PayScreenContract {

    interface payView{


        void showNextFragment_SuringPay(String s , double extra);

        void showNextLastFragmentPayingFragment(double finaltotal);

        void showThanksOrder(String OrderID);

    }


}
