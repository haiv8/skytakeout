package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param dto
     */
    void addEmp(EmployeeDTO dto);

    PageResult page(EmployeePageQueryDTO dto);

    void enableOrDisable(Integer status, long id);

    Employee getById(Long id);

    void update(EmployeeDTO dto);
}
