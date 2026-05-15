package com.example.baglanulyabatfinalproject.service;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserRequest;
import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatUserDto.BaglanulyAbatUserResponse;
import com.example.baglanulyabatfinalproject.entity.BaglanulyAbatUser;
import com.example.baglanulyabatfinalproject.entity.enums.BaglanulyAbatUserRole;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatDuplicateResourceException;
import com.example.baglanulyabatfinalproject.exception.BaglanulyAbatResourceNotFoundException;
import com.example.baglanulyabatfinalproject.repository.BaglanulyAbatUserRepository;
import com.example.baglanulyabatfinalproject.service.impl.BaglanulyAbatUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BaglanulyAbatUserService Unit Tests")
class BaglanulyAbatUserServiceTest {

    @Mock
    private BaglanulyAbatUserRepository userRepository;

    @InjectMocks
    private BaglanulyAbatUserServiceImpl userService;

    private BaglanulyAbatUser testUser;
    private BaglanulyAbatUserRequest testRequest;

    @BeforeEach
    void setUp() {
        testUser = BaglanulyAbatUser.builder()
                .id(1L).username("testuser").email("test@example.com")
                .password("encoded_password").firstName("Test").lastName("User")
                .role(BaglanulyAbatUserRole.USER).isActive(true)
                .createdAt(LocalDateTime.now()).build();

        testRequest = BaglanulyAbatUserRequest.builder()
                .username("testuser").email("test@example.com")
                .password("password123").firstName("Test").lastName("User").build();
    }

    @Test
    @DisplayName("createUser — success")
    void createUser_WhenValidRequest_ShouldReturnResponse() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(BaglanulyAbatUser.class))).thenReturn(testUser);

        BaglanulyAbatUserResponse response = userService.createUser(testRequest);

        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("testuser");
        verify(userRepository).save(any(BaglanulyAbatUser.class));
    }

    @Test
    @DisplayName("createUser — duplicate username throws")
    void createUser_WhenDuplicateUsername_ShouldThrow() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(testRequest))
                .isInstanceOf(BaglanulyAbatDuplicateResourceException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("getUserById — found")
    void getUserById_WhenExists_ShouldReturn() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        BaglanulyAbatUserResponse response = userService.getUserById(1L);

        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getUserById — not found throws")
    void getUserById_WhenMissing_ShouldThrow() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(BaglanulyAbatResourceNotFoundException.class);
    }

    @Test
    @DisplayName("getAllActiveUsers — returns active list")
    void getAllActiveUsers_ShouldReturnList() {
        when(userRepository.findAllByIsActiveTrue()).thenReturn(List.of(testUser));

        List<BaglanulyAbatUserResponse> users = userService.getAllActiveUsers();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getIsActive()).isTrue();
    }

    @Test
    @DisplayName("deactivateUser — sets isActive=false")
    void deactivateUser_ShouldSetInactive() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any())).thenReturn(testUser);

        userService.deactivateUser(1L);

        verify(userRepository).save(argThat(u -> !u.getIsActive()));
    }

    @Test
    @DisplayName("deleteUser — calls repository delete")
    void deleteUser_ShouldCallDelete() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        userService.deleteUser(1L);

        verify(userRepository).delete(testUser);
    }
}