package com.erp.classes;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity(name = "ID_GEN")
@NamedNativeQuery(name = "Reports.ProfitLossReport", query = "select a.accName as Name, sum(tbd.subTotal) as subTotal,t.Type as TYPE "
		+ "from accountgroup a " + "join TrailBalanceDetails tbd on tbd.Acc_ID = a.acc_id "
		+ "join TrailBalance t on t.tb_ID = tbd.TB_ID  where t.type = 1 or t.Type= 2 and  t.date BETWEEN :startDate AND :endDate "
		+ "group by a.acc_ID,a.accName,t.Type", resultSetMapping = "ProfitLossReport")

@SqlResultSetMapping(name = "ProfitLossReport", classes = {
		@ConstructorResult(targetClass = ProfitLoss.class, columns = {
				@ColumnResult(name = "name", type = String.class),
				@ColumnResult(name = "subTotal", type = Double.class),
				@ColumnResult(name = "type", type = Double.class) }) })




public class Reports {

	@Id
	int GEN_KEY;

}
