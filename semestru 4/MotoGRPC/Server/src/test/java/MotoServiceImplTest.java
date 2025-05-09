import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Services.MotoException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;
import services.grpc.MotoServiceProto;

import java.util.Optional;

class MotoServiceImplTest {
    private MotoServiceImpl motoService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        motoService = new MotoServiceImpl(null, userRepository, null, null);
    }

    @Test
    void testLogin_Success() {
        MotoServiceProto.LoginRequest request = MotoServiceProto.LoginRequest.newBuilder()
                .setUser(MotoServiceProto.User.newBuilder()
                        .setUsername("horatiu")
                        .setPassword("parola123"))
                .build();

        when(userRepository.findByUsername("horatiu", "parola123"))
                .thenReturn(Optional.of(new model.User("horatiu", "parola123")));

        StreamObserver<MotoServiceProto.LoginResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(MotoServiceProto.LoginResponse response) {
                assertEquals("Login successful", response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                fail("Should not have failed");
            }

            @Override
            public void onCompleted() {
                // Test completed successfully
            }
        };

        motoService.login(request, responseObserver);
    }

    @Test
    void testLogin_Failure() {
        MotoServiceProto.LoginRequest request = MotoServiceProto.LoginRequest.newBuilder()
                .setUser(MotoServiceProto.User.newBuilder()
                        .setUsername("testUser")
                        .setPassword("wrongPass"))
                .build();

        when(userRepository.findByUsername("testUser", "wrongPass"))
                .thenReturn(Optional.empty());

        StreamObserver<MotoServiceProto.LoginResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(MotoServiceProto.LoginResponse response) {
                fail("Should not have succeeded");
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(t instanceof MotoException);
                assertEquals("Username or Password invalid", t.getMessage());
            }

            @Override
            public void onCompleted() {
                // Should not reach here
            }
        };

        motoService.login(request, responseObserver);
    }
}