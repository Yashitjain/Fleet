package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.ExpenseDto;
import com.FYP.Fleet.Dto.ExpenseResponseDto;
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
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody ExpenseDto expenseDto){
        ExpenseResponseDto response = expenseService.createExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable long expenseId){
        ExpenseResponseDto response = expenseService.getExpenseById(expenseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
