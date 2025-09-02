package service;

import dtos.UserExportDTO;
import dtos.UserSummaryDTO;
import repository.UserRepository;
import model.Admin;
import model.BasicPlan;
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
    private final EnrollmentService enrollmentService;

    public UserService(UserRepository userRepository, EnrollmentService enrollmentService) {
        this.userRepository = userRepository;
        this.enrollmentService = enrollmentService;
    }

    public UserSummaryDTO register(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyExistException("Usuário com o email " + user.getEmail() + " ja cadastrado.");
        }
        userRepository.save(user);
        return toDTO(user);
    }

    public UserSummaryDTO login(String email) {
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
        if (newPlan instanceof BasicPlan) {
            int matriculas = enrollmentService.getEnrollmentsByStudent(student.getEmail()).size();
            if (matriculas > 3) {
                throw new IllegalArgumentException("Aluno tem mais de 3 matrículas. Não pode mudar para plano Basic.");
            }
        }

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

    public Collection<UserExportDTO> getAllUsersForExport() {
        return userRepository.findAll().stream()
                .map(this::toExportDTO)
                .collect(Collectors.toList());
    }

    private UserSummaryDTO toDTO(User user) {
        String role = user instanceof Student ? "STUDENT" : "ADMIN";

        return new UserSummaryDTO(user.getName(), user.getEmail(), role);

    }

    private UserExportDTO toExportDTO(User user) {
        UserExportDTO dto = new UserExportDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

}
