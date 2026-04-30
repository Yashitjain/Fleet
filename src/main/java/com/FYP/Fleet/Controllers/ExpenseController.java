package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.Request.ExpenseRequestDto;
import com.FYP.Fleet.Dto.Response.ExpenseResponseDto;
import com.FYP.Fleet.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @PostMapping("/")
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody ExpenseRequestDto expenseRequestDto){
        ExpenseResponseDto response = expenseService.createExpense(expenseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable long expenseId){
        ExpenseResponseDto response = expenseService.getExpenseResponseById(expenseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
