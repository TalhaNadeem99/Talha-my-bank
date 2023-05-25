package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;
    private Account checkingAccount;
    private Account savingAccount;
    private Account maxAccount;

    @Before
    public void setup(){
        bank = new Bank();
        checkingAccount = new Account(Account.CHECKING);
        savingAccount = new Account(Account.SAVINGS);
        maxAccount = new Account(Account.MAXI_SAVINGS);
    }

    @Test 
    public void customerSummary() {
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n  John (1 account)", bank.customerSummary());
    }

    @Test 
    public void checkingAccountInterest() {
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test 
    public void savings_accountInterest() {
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));
        savingAccount.deposit(1000.0);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test 
    public void savings_account_DepositMoreThen1000() {
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(1000.0);
        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        savingAccount.deposit(1500.0);
        assertEquals(4.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test 
    public void maxi_savings_account() {
        bank.addCustomer(new Customer("Bill").openAccount(maxAccount));

        maxAccount.deposit(1000.0);
        assertEquals(20.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        maxAccount.deposit(1000.0);
        assertEquals(70.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        maxAccount.deposit(1000.0);
        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test 
    public void maxi_saving_5_Percent() {
        bank.addCustomer(new Customer("Bill").openAccount(maxAccount));
        maxAccount.deposit(2000.0);
        assertEquals(70.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test 
    public void maxi_savings_10_Percent() {
        bank.addCustomer(new Customer("Bill").openAccount(maxAccount));
        maxAccount.deposit(3000.0);
        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test // total interest paid by the bank on all Accounts 
    public void intrestPaidByBankAllAccount(){
        bank.addCustomer(new Customer("James").openAccount(checkingAccount));
        bank.addCustomer(new Customer("Elton").openAccount(savingAccount));
        bank.addCustomer(new Customer("Harry").openAccount(maxAccount));

        checkingAccount.deposit(1000.0);
        savingAccount.deposit(2000.0);
        maxAccount.deposit(3000.0);

        assertEquals(174.0,bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
