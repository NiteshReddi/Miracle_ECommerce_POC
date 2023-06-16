
import org.example.controller.ClientController;
import org.example.model.OrderDetails;
import org.example.service.GetOrderDetailsService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {


    @Autowired
    private GetOrderDetailsService getOrderDetailsService;

    @MockBean
    private OrderDetails orderDetails;


    @Test
    public void getAllOrdersTest(){

     //   when(orderRepository.findAll()).thenReturn(buildOrders());
      //  assertEquals(buildOrders().size(),orderController.getAllOrders().size());

    }


}