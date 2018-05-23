package com.erp.classes;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity(name = "ID_GEN")
@NamedNativeQuery(name = "Reports.ProfitLossReport", query = "select Name,sum(subTotal) as subTotal,TYPE  " + "from ( "
		+ "select a.accName as Name, sum(tbd.subTotal) as subTotal,t.Type as TYPE  " + "from accountgroup a  "
		+ " join TrailBalanceDetails tbd on tbd.Acc_ID = a.acc_id " + " join TrailBalance t on t.tb_ID = tbd.TB_ID   "
		+ "where t.type = 1 or t.Type= 2 and  t.date BETWEEN    :startDate AND :endDate   group by a.accName,t.Type "

		+ "union all " + "select 'Account Payable' As Name, COALESCE(SUM(APR.AmountPaid),0) as subTotal,'1' AS type  "
		+ "		FROM AccountPayable AP  " + "join APReciept APR on APR.AP_ID= AP.AP_ID  "
		+ "		where APR.[date] BETWEEN  :startDate AND :endDate and not AP.status =:statusNotLike   "
		
		+ "UNION ALL "
		+ "select 'Account Recievable' As Name, COALESCE(SUM(ARR.AmountRecieved),0) as subTotal,'2' AS type\r\n"
		+ "FROM AccountReceivable AR\r\n" + "join ARReciept ARR on ARR.AR_ID= AR.AR_ID\r\n"
		+ "		where ARR.[date]  BETWEEN  :startDate AND :endDate and not AR.status =:statusNotLike  " + ") X "
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
