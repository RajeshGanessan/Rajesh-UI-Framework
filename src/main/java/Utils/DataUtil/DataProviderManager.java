package Utils.DataUtil;

import org.testng.annotations.DataProvider;

public class  DataProviderManager {

    //Data Provider for TransactionTypes
    @DataProvider(name = "TransactionTypes")
    public Object[] getTransactionTypes(){
        return new Object[]{"All Transactions","Lapse","Exercise","Vesting"};
    }

    //Data provider for ExpenseAccountingMethod
    @DataProvider(name = "ExpenseAcctMethod")
    public Object[] getExpenseAcctMethod(){
        return new Object[]{"Straight Line method","Front Loaded method"};
    }


}
