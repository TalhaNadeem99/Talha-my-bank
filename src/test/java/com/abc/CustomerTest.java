package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;
    private Account checkingAccount;
    private Account savingsAccount;
    private Customer henry;

    @Before
    public void setup(){
        checkingAccount = new Account(Account.CHECKING);
        savingsAccount = new Account(Account.SAVINGS);
        henry = new Customer("Henry");
    }

    @Test //Test customer statement generation
    public void testApp(){
        henry.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test 
    public void testOneAccount(){
        henry.openAccount(new Account(Account.SAVINGS));
        assertEquals(1, henry.getNumberOfAccounts());
    }

    @Test 
    public void testTwoAccount(){
        henry.openAccount(new Account(Account.SAVINGS));
        henry.openAccount(new Account(Account.CHECKING));
        assertEquals(2, henry.getNumberOfAccounts());
    }

    @Test 
    public void testThreeAcounts() {
        henry.openAccount(new Account(Account.SAVINGS));
        henry.openAccount(new Account(Account.CHECKING));
        henry.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, henry.getNumberOfAccounts());
    }

    @Test // list of customers and how many accounts they have
    public void reportListCustomerAccount(){
        Bank bank = new Bank();
        Customer oscar = new Customer("oscar");
        Customer james = new Customer("james");

        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        james.openAccount(new Account(Account.SAVINGS));

        bank.addCustomer(james);
        bank.addCustomer(oscar);

        String report = bank.customerSummary();
        String expactedReport = "Customer Summary\n" +
                                "  james (1 account)\n" +
                                "  oscar (2 accounts)";
        assertEquals(expactedReport, report);
    }

    @Test // transfer amount between customer accounts 
    public void transferBetweenAccounts(){
        Account checkAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        Customer oscar = new Customer("oscar");
        oscar.openAccount(checkAccount);
        oscar.openAccount(savingAccount);

        checkAccount.deposit(500.0);
        savingAccount.deposit(200.0);

        oscar.transferBetweenAccount(savingAccount, checkAccount, 100.0);
        assertEquals(600.0, checkAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(100.0, savingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    // transfer money if the balance is 0
    @Test (expected = IllegalArgumentException.class)
    public void transferNotPossible(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);
    
        savingsAccount.deposit(0.0);
    
        oscar.transferBetweenAccount(savingsAccount, checkingAccount, 100.0);
    }

    // transfer money between different customers 
    @Test (expected = IllegalArgumentException.class)
    public void transferBetweenTwoCustomer(){
        Account checkingAccount1 = new Account(Account.CHECKING);
        Account checkingAccount2 = new Account(Account.CHECKING);
        Customer oscar = new Customer("Oscar");
        Customer harry = new Customer("Harry");
        oscar.openAccount(checkingAccount1);
        harry.openAccount(checkingAccount2);
    
        checkingAccount1.deposit(500.0);
        checkingAccount2.deposit(200.0);
    
        oscar.transferBetweenAccount(checkingAccount1, checkingAccount2, 100.0);
    }
}
