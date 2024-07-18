package com.datatable.data.table.dto;

import com.datatable.data.table.model.Employee;
import lombok.Data;

import java.util.List;

@Data
public class BulkEmployeeRequest {

    private List<Employee> create;
    private List<Employee> update;
    private List<Long> delete;
}
