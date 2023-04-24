package com.csi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private int empId;

    private String empName;

    private String empAddress;

    private double empSalary;

    private long empContactNumber;

    @JsonFormat(pattern = "dd-MM-yyyy" , timezone = "Asia/Kolkata")
    private Date empDOB;

    private String empEmailId;

    private String empPassword;
}
