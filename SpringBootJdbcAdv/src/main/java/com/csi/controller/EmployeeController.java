package com.csi.controller;

import com.csi.model.Employee;
import com.csi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Employee employee){
        employeeServiceImpl.signUp(employee);
        return ResponseEntity.ok("Data Saved Successfully");
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")

    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId,String empPassword){
        return ResponseEntity.ok(employeeServiceImpl.signIn(empEmailId,empPassword));
    }

    @GetMapping("/getdatabyid/{empId}")

    public ResponseEntity<Employee> getDataById(@PathVariable int empId){
        return ResponseEntity.ok(employeeServiceImpl.getDataById(empId));
    }

    @GetMapping("/getalldata")

    public ResponseEntity <List<Employee>> getAllData(){
        return ResponseEntity.ok(employeeServiceImpl.getAllData());
    }

    @GetMapping("/sortbyname")

    public ResponseEntity <List<Employee>> sortDataByName(){
        return ResponseEntity.ok(employeeServiceImpl.getAllData().stream().sorted(Comparator.comparing(Employee::getEmpName)).collect(Collectors.toList()));
    }

    @GetMapping("/sortbysalary")

    public ResponseEntity <List<Employee>> sortDataBySalary(){
        return ResponseEntity.ok(employeeServiceImpl.getAllData().stream().sorted(Comparator.comparingDouble(Employee::getEmpSalary)).collect(Collectors.toList()));
    }

    @GetMapping("/filterbysalary/{empSalary}")

    public ResponseEntity <List<Employee>> filterBySalary(@PathVariable double empSalary){
        return ResponseEntity.ok(employeeServiceImpl.getAllData().stream().filter(emp->emp.getEmpSalary()>=50000).collect(Collectors.toList()));
    }
    @PostMapping("/savebulkofdata")

    public ResponseEntity<String> saveBulkOfData(@RequestBody List<Employee> employees){
        employeeServiceImpl.saveBulkOfData(employees);
        return ResponseEntity.ok("Bulk of Data saved Successfully");
    }
    @GetMapping("/getdatabyusinganyinput/{input}")

    public ResponseEntity <List<Employee>> getDataByUsingAnyInput(@PathVariable String input){
        return ResponseEntity.ok(employeeServiceImpl.getDataByUsingAnyInput(input));
    }
    @GetMapping("/checkloaneligiblity/{empId}")

    public ResponseEntity<String> checkLoanEligiblity(@PathVariable int empId){
        String msg=null;
        for(Employee employee: employeeServiceImpl.getAllData()){
            if(employee.getEmpId()==empId && employee.getEmpSalary()>=50000){

                msg= "yes ! Eligible for loan";
                break;
            }
            else{

                msg= "Not ! Eligible for loan";
            }
        }return ResponseEntity.ok(msg);
    }

    @PutMapping("/updatedata/{empId}")

    public ResponseEntity<String> updateData(@PathVariable int empId,@RequestBody Employee employee){
        employeeServiceImpl.updateData(empId,employee);
        return ResponseEntity.ok("Data updated succesfully");
    }

    @PatchMapping("/partialupdation/{empId}")

    public ResponseEntity<String> partialUpdate(@PathVariable int empId,@RequestBody Employee employee){
        employeeServiceImpl.partialUpdate(empId,employee);
        return ResponseEntity.ok("Data updated succesfully");
    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeServiceImpl.deleteDataById(empId);
        return ResponseEntity.ok("Data deleted succesfully");
    }

    @DeleteMapping("/deletealldata")
    public ResponseEntity<String> deleteAllData() {
        employeeServiceImpl.deleteAllData();
        return ResponseEntity.ok("Data deleted succesfully");
    }
}
