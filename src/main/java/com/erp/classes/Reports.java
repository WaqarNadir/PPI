package com.erp.classes;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity(name = "ID_GEN")
@NamedNativeQuery(name = "Reports.ProfitLossReport", query = "select Name,sum(subTotal) as subTotal,TYPE \r\n"
		+ "from (\r\n" + "select a.accName as Name, sum(tbd.subTotal) as subTotal,t.Type as TYPE \r\n"
		+ "from accountgroup a \r\n" + " join TrailBalanceDetails tbd on tbd.Acc_ID = a.acc_id\r\n"
		+ " join TrailBalance t on t.tb_ID = tbd.TB_ID  \r\n"
		+ "where t.type = 1 or t.Type= 2 and  t.date BETWEEN    :startDate AND :endDate   group by a.accName,t.Type\r\n"
		+ "union all\r\n" + "select a.accName as Name, SUM(APD.amount_Paid) as subTotal,'1' AS type\r\n"
		+ "from AccountGroup a\r\n" + "JOIN AccountPayableDetails APD on APD.Acc_ID=a.Acc_ID\r\n"
		+ "join AccountPayable AP on AP.AP_ID= APD.AP_ID\r\n" + "where AP.[date] BETWEEN  :startDate AND :endDate  \r\n"
		+ "group by a.accName\r\n" + "\r\n" + "UNION ALL\r\n"
		+ "select  a.accName as Name ,SUM(ARD.amountReceivable ) as subTotal,'2' AS type\r\n"
		+ "from AccountGroup a\r\n" + "JOIN AccountReceivableDetails ARD on ARD.Acc_ID=a.Acc_ID\r\n"
		+ "join AccountReceivable AR on AR.AR_ID= ARD.AR_ID\r\n"
		+ "where AR.[date] BETWEEN  :startDate AND :endDate  \r\n" + "group by a.accName) X\r\n"
		+ "group by X.Name ,X.TYPE", resultSetMapping = "ProfitLossReport")

@SqlResultSetMapping(name = "ProfitLossReport", classes = {
		@ConstructorResult(targetClass = ProfitLoss.class, columns = {
				@ColumnResult(name = "name", type = String.class),
				@ColumnResult(name = "subTotal", type = Double.class),
				@ColumnResult(name = "type", type = Double.class) }) })

public class Reports {

	@Id
	int GEN_KEY;

}
