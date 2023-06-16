
import org.example.controller.VaultController;
import org.example.entity.AuthRequest;
import org.example.repository.DaemonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class VaultControllerTest{

        @Mock
        private AuthRequest authRequest;

        @InjectMocks
        private VaultController vaultController;


        @Autowired
        private DaemonRepository daemonRepository;


        public void userData(){
            List<AuthRequest> userDetails = Stream.of(
                    new AuthRequest("Nitesh","password")
            ).collect(Collectors.toList());

//            daemonRepository.setUserName("TestUserName");
//            daemonRepository.setPassword("TestPassword");
        }


        @Test
        public void storeSecretTest(){
       // when(authRequest.saveAll(anyList())).thenReturn(buildOrders());
       // assertEquals("\nOrder Placed with order ID :111", orderController.saveOrder(buildOrders().get(0)));
        }

        @Test
        public void validateUserTest(){
       // when(LogResponse.builder()).thenReturn()

        }

}
