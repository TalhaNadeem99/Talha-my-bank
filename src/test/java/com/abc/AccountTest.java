package com.abc;

import org.junit.Test;
import org.junit.Before;

public class AccountTest {

    public Customer henry;
    public Account checkAccount;

    @Before
    public void setup(){
        checkAccount = new Account(Account.CHECKING);
        henry = new Customer("Henry");
    }

    // withdraw 0 amount if balance is 0 
    @Test (expected = IllegalArgumentException.class)
    public void withdrawZeroAmount(){
        henry.openAccount(new Account(Account.CHECKING));
        checkAccount.withdraw(0.0);
    }

    @Test
    public void withdrawAmount(){
        henry.openAccount(new Account(Account.CHECKING));
        checkAccount.deposit(100.0);
        checkAccount.withdraw(50.0);
    }

    // withdraw amount more then deposit 
    @Test (expected = IllegalArgumentException.class)
    public void withdrawAmountGreaterThanDeposit(){
        henry.openAccount(new Account(Account.CHECKING));
        checkAccount.deposit(100.0);
        checkAccount.withdraw(200.0);
        System.out.println(checkAccount.sumTransactions());
    }

    // deposit amount in negative  
    @Test (expected = IllegalArgumentException.class)
    public void DepositWithNagitiveAmount(){
        henry.openAccount(new Account(Account.CHECKING));
        checkAccount.deposit(-100.0);
    }

    // deposit 0 amount
    @Test (expected = IllegalArgumentException.class)
    public void DepositZeroAmount(){
        henry.openAccount(new Account(Account.CHECKING));
        checkAccount.deposit(0.0);
    }    
}
