package com.csi.dao;

import com.csi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    String signup_SQL="insert into employee(empid,empname,empaddress,empsalary,empcontactnumber,empdob,empemailid,emppassword) values(?,?,?,?,?,?,?,?)";

    String select_by_id_SQL="select * from employee where empId=?";

    String select_All_SQL="select * from employee";

    String update_SQL="update employee set empname=?,empaddress=?,  empsalary=?,empcontactnumber=?, empdob=? ,empemailid=?, emppassword=? where empid=?";

    String partial_Updatetion_SQL="update employee set empaddress=?,  empcontactnumber=? where empid=?";

    String delete_byId_SQL="delete from employee where empid=?";

    String delete_All_SQL="truncate table employee";

    private  Employee employee(ResultSet resultSet, int n) throws SQLException {
        return  Employee.builder().empId(resultSet.getInt(1)).empName(resultSet.getString(2)).empAddress(resultSet.getString(3)).empSalary(resultSet.getDouble(4)).empContactNumber(resultSet.getLong(5)).empDOB(resultSet.getDate(6)).empEmailId(resultSet.getString(7)).empPassword(resultSet.getString(8)).build();
    }
    @Override
    public void signUp(Employee employee) {

        jdbcTemplate.update(signup_SQL,employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(),employee.getEmpSalary(),employee.getEmpContactNumber(),employee.getEmpDOB(),employee.getEmpEmailId(),employee.getEmpPassword());
    }

    @Override
    public boolean signIn(String empEmailId, String empPassword) {
        boolean flag =false;

        for (Employee employee: getAllData()){
            if (employee.getEmpEmailId().equals(empEmailId) && employee.getEmpPassword().equals(empPassword)){
                flag =true;
            }
        }
        return flag;
    }

    @Override
    public Employee getDataById(int empId) {
        return jdbcTemplate.query(select_by_id_SQL, this::employee,empId).get(0);
    }

    @Override
    public List<Employee> getAllData() {
        return jdbcTemplate.query(select_All_SQL, this:: employee);
    }

    @Override
    public void saveBulkOfData(List<Employee> employees) {


        for(Employee employee: employees){
            jdbcTemplate.update(signup_SQL,employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(),employee.getEmpSalary(),employee.getEmpContactNumber(),employee.getEmpDOB(),employee.getEmpEmailId(),employee.getEmpPassword());
        }
    }

    @Override
    public List<Employee> getDataByUsingAnyInput(String input) {

        List<Employee> employeeList=new ArrayList<>();

        for (Employee employee: getAllData()){
            if(String.valueOf(employee.getEmpId()).equals(input)
           || employee.getEmpName().equals(input)
            || employee.getEmpAddress().equals(input)
            || String.valueOf(employee.getEmpSalary()).equals(input)
            || String.valueOf(employee.getEmpContactNumber()).equals(input)
            || employee.getEmpEmailId().equals(input)
            ||employee.getEmpPassword().equals(input)){

                employeeList.add(employee);
            }
        }

        return  employeeList;
    }

    @Override
    public void updateData(int empId, Employee employee) {

        jdbcTemplate.update(update_SQL, employee.getEmpName(),employee.getEmpAddress(),employee.getEmpSalary(),employee.getEmpContactNumber(),employee.getEmpDOB(),employee.getEmpEmailId(),employee.getEmpPassword(),empId);
    }

    @Override
    public void partialUpdate(int empId, Employee employee) {

        jdbcTemplate.update(partial_Updatetion_SQL,employee.getEmpAddress(),employee.getEmpContactNumber(),empId);
    }

    @Override
    public void deleteDataById(int empId) {

        jdbcTemplate.update(delete_byId_SQL,empId);
    }

    @Override
    public void deleteAllData() {

        jdbcTemplate.update(delete_All_SQL);
    }
}
