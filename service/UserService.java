package service;

import dtos.UserSummaryDTO;
import repository.UserRepository;
import model.Admin;
import model.Student;
import model.SubscriptionPlan;
import model.User;

import Exceptions.AccessDeniedException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import Exceptions.UserAlreadyExistException;
import Exceptions.UserNotFoundException;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserSummaryDTO register(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyExistException("Usuário com o email " + user.getEmail() + " ja cadastrado.");
        }
        userRepository.save(user);
        return toDTO(user);
    }

    public UserSummaryDTO login(String email){
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado para o email: " + email);
        }

        User user = userOpt.get();
        return toDTO(user);
    }

     public void changeSubscriptionPlan(String studentEmail, SubscriptionPlan newPlan, User admin) {
        if (!(admin instanceof Admin)) {
        throw new AccessDeniedException("Apenas administradores podem alterar planos.");
        }
        User user = userRepository.findByEmail(studentEmail)
        
            .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o e-mail: " + studentEmail));
        if (!(user instanceof Student)) {
            throw new IllegalArgumentException("O usuário encontrado não é um aluno e não pode ter um plano.");
        }
        Student student = (Student) user;
        student.setSubscriptionPlan(newPlan);
        userRepository.save(student);
    }

    public Optional<UserSummaryDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toDTO);
    }

    
    public Collection<UserSummaryDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<User> findFullUserByEmail(String email) {
    return userRepository.findByEmail(email);
}

    private UserSummaryDTO toDTO(User user) {
    String role = user instanceof Student ? "STUDENT" : "ADMIN";

    return new UserSummaryDTO(user.getName(), user.getEmail(), role);
}

}
