package com.database.ProjectDB.DQO;

import com.database.ProjectDB.DAO.ServiceDAO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

/**
 * Created by seba on 2017-06-03.
 */
public class FunctionQueries {
    private static ServiceDAO service = ServiceDAO.getInstance();

    public static BigDecimal firstFunction(Long employeeId, Long year) {
        EntityManager entityManager = service.beginTransaction();
        BigDecimal getCount = (BigDecimal) entityManager.createNativeQuery
                ("Select ProfitPerOneEmployeeInYear(:employee_id,:yearForEmployee) from dual").
                setParameter("employee_id", employeeId).setParameter("yearForEmployee", year)
                .getSingleResult();
        service.commitTransaction();
        return getCount;
    }

    public static BigDecimal secondFunction(String status){
        EntityManager entityManager = ServiceDAO.getInstance().beginTransaction();
        BigDecimal getCount = (BigDecimal)entityManager.createNativeQuery
                ("Select get_Count(:status) from dual").
                setParameter("status", status).getSingleResult();
        return getCount;
    }

    public static BigDecimal thirdFunction(String status, int month){
        EntityManager entityManager = ServiceDAO.getInstance().beginTransaction();
        BigDecimal getCount = (BigDecimal)entityManager.createNativeQuery
                ("Select ProfitPerMonth(:status, :ourMonth) from dual")
                .setParameter("status", status)
                .setParameter("ourMonth", month)
                .getSingleResult();
        return getCount;
    }
}
