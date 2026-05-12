package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.Request.TransactionRequestDto;
import com.FYP.Fleet.Dto.Response.TransactionResponseDto;
import com.FYP.Fleet.Models.Owner;
import com.FYP.Fleet.Models.Transactions;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Repository.OwnerRepository;
import com.FYP.Fleet.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final OwnerRepository ownerRepository;
    private final UserService userService;

    @Autowired
    TransactionService(TransactionRepository transactionRepository, OwnerRepository ownerRepository, UserService userService){
        this.transactionRepository = transactionRepository;
        this.ownerRepository = ownerRepository;
        this.userService = userService;
    }

    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto, Long userId) throws UserPrincipalNotFoundException {
        Owner owner = ownerRepository.findByIdAndUserId(transactionRequestDto.getOwnerId(), userId).orElseThrow(
                ()-> new RuntimeException("Owner Not Found")
        );
        User user = userService.getUserById(userId);
        Transactions transactions = Transactions.builder()
                .user(user)
                .owner(owner)
                .date(transactionRequestDto.getDate())
                .note(transactionRequestDto.getNote())
                .amount(transactionRequestDto.getAmount())
                .method(transactionRequestDto.getMethod())
                .build();

        transactions = transactionRepository.save(transactions);
        return generateTransactionResponse(transactions);
    }

    public List<TransactionResponseDto> getTransactionByUserIdAndOwnerId(Long userId, Long ownerId){
        return transactionRepository.findTransactionByUserIdAndOwnerId(userId, ownerId)
                .stream().map(this::generateTransactionResponse).toList();
    }

    public TransactionResponseDto generateTransactionResponse(Transactions transactions){
        return TransactionResponseDto.builder()
                .ownerId(transactions.getOwner().getId())
                .amount(transactions.getAmount())
                .date(transactions.getDate())
                .note(transactions.getNote())
                .ownerName(transactions.getOwner().getName())
                .method(transactions.getMethod())
                .build();
    }
}
