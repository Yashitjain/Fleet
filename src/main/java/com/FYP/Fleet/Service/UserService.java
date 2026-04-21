package com.FYP.Fleet.Service;

import com.FYP.Fleet.Dto.UserDto;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(UserDto userDto){
        User createuser = User.builder()
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .build();

        createuser = userRepository.save(createuser);
        return createuser;
    }

    public User getUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("User do not exist");
        }

        return user.get();
    }
}
